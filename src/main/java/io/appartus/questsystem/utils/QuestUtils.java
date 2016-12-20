package io.appartus.questsystem.utils;


import io.appartus.questsystem.questsystem;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


/**
 * aa
 * Created by Alois on 12.12.2016.
 */
public class QuestUtils {
    private TileUtils tileutils = new TileUtils();


    private Logger getLogger() {
        return questsystem.getLogger();
    }
    private CommentedConfigurationNode configNode(){
        return questsystem.getConfigNode();
    }



    public String getQuestType(TileEntity entity){
        return tileutils.getSignLine(entity,3);
    }

    public boolean runQuest(Player player, int QuestID){
        getLogger().info(player.getName() + " run a quest with id " + QuestID);
        Function<Object,String> stringTransformer = new Function<Object,String>() {
            public String apply(Object input) {
                if (input instanceof String) {
                    return (String) input;
                } else {
                    return null;
                }
            }
        };

        ConfigurationNode pickNode;
        List<String> rewlist;
        List<String> reqlist;
        int Remove_Level = 0;
        String Quest_Lore;
        pickNode = configNode().getNode("Quests",Integer.toString(QuestID),"Quest Reward");
        rewlist = pickNode.getList(stringTransformer);
        pickNode = configNode().getNode("Quests",Integer.toString(QuestID),"Quest Requirements");
        reqlist = pickNode.getList(stringTransformer);
        Quest_Lore = "Quest"+QuestID+" Lore: " + configNode().getNode("Quests",Integer.toString(QuestID),"Quest Lore").getString();


        Boolean Succes = false;
        List<Boolean> Req_S = new ArrayList<>();
        List<ItemStack> itemsToRemove = new ArrayList<>();


        if(!Succes){

        for (String Req : reqlist ) {
            String Type = Req.split("\\|\\|")[0];
            String Arg = Req.split("\\|\\|")[1];

            switch (Type) {
                case "Level": {
                    boolean ok;
                    ok = GetPlayerLevel(player) >= Integer.parseInt(Arg);
                    if (ok) {
                        Remove_Level = Integer.parseInt(Arg);

                    } else {
                        player.sendMessage(Text.of(TextColors.RED, "Nemas dostatecnou uroven pro tento quest"));
                        getLogger().info("Quest " + QuestID + ": Low level");
                    }
                    Req_S.add(ok);
                }
                break;
                case "Item": {
                    String item;
                    int Count;
                    boolean ok;
                    ItemStack Item;
                    item = Arg.split("\\|")[0];
                    Count = Integer.parseInt(Arg.split("\\|")[1]);
                    Item = StringToItemStack(item, Count);
                    ok = HaveItemInHand(player, Item);
                    if (ok) itemsToRemove.add(Item);
                    else {
                        player.sendMessage(Text.of(TextColors.RED, "Nemáš " + item + " pro splneni tohoto questu"));
                        //player.sendMessage(Text.of(Quest_Lore));
                        getLogger().info("Quest " + QuestID + ": Wrong or No Item");
                    }
                    Req_S.add(ok);
                }
                break;
                case "Perm":{
                    if(player.hasPermission(Arg)) Req_S.add(true);
                    else {
                        player.sendMessage(Text.of(TextColors.RED, "Nemas potrebna opraveneni k tomuto questu"));
                        getLogger().info("Quest " + QuestID + ": Not have permission for this quest");
                        Req_S.add(false);
                    }
                }
                break;
                case "PermNot":{
                    if(!player.hasPermission(Arg)) Req_S.add(true);
                    else {
                        player.sendMessage(Text.of(TextColors.RED, "Mas opravneni ktere bys mit nemel!"));
                        getLogger().info("Quest " + QuestID + ": Have not allowed permission for this quest");
                        Req_S.add(false);
                    }
                }
                break;
            }
        }



        if (Check_succes(Req_S)){

            if (Remove_Level > 0) {
                if(RemovePlayerLevel(player,Remove_Level)){
                    getLogger().info("Quest" + QuestID + ", remove " + Remove_Level + " from player " + player.getName());
                }
                else {
                    player.sendMessage(Text.of("Nekde nastala chyba a uroven takovou jako pred chvilkou"));
                    getLogger().info("Error in quest " + QuestID + ", player dont have Level");
                    rewlist.clear();
                    return false;
                }
            } else getLogger().info("Error in quest " + QuestID + ", with level take " + Remove_Level);

            for (ItemStack item : itemsToRemove){
                if(HaveItemInHand(player,item)) {
                    DeleteItemFromHand(player);
                    getLogger().info("Hraci byl odebran predmet " + item.getItem().getName());
                }
                else {
                    player.sendMessage(Text.of("Nekde nastala chyba a v ruce nedrziz to co pred chvilkou"));
                    getLogger().info("Error in quest " + QuestID + ", player dont have item");
                    rewlist.clear();
                    return false;
                }
            }
            for (String Rew : rewlist ) {
                String Type = Rew.split("\\|\\|")[0];
                String Arg = Rew.split("\\|\\|")[1];
               // player.sendMessage(Text.of("Typ odmeny je ", Type));
                switch (Type){
                    case "Command":{
                        String Command = Arg;
                        if (Command.contains("@p")){
                            Command = Command.replace("@p", player.getName());
                        }
                        RunCommand(Command);
                        getLogger().info("Quest"+ QuestID+": Run Command " + Command);
                    }
                    break;

                    case "Item":{
                        String Item = Arg.split("\\|")[0];
                        int Count = Integer.parseInt(Arg.split("\\|")[1]);
                        GiveItem(player,StringToItemStack(Item,Count));
                        getLogger().info("Player obtain "+ Count +"x Item:" + Item);
                    }
                    break;
                }


            }

            Succes = true;
        } else{
            player.sendMessage(Text.of(TextColors.RED,"Nesplnil jsi nekterou podminku Questu"));
            player.sendMessage(Text.of(Quest_Lore));
        }

        }
        return true;
    }

    private Boolean Check_succes(List<Boolean> list){
        for (boolean i: list){
            if (!i) return i;
        }
        return true;
    }

    private void GiveItem(Player player, ItemStack item){
        if (item == null) return;
        player.getInventory().offer(item);

    }

    private int GetPlayerLevel(Player player){
        if (player.get(Keys.EXPERIENCE_LEVEL).isPresent()) return player.get(Keys.EXPERIENCE_LEVEL).get();
        else return 0;
    }

    private boolean SetPlayerLevel(Player player, int Level){
        int currentLevel = GetPlayerLevel(player);
        int removeLevel = currentLevel - Level;
        if (removeLevel < 0) return false;

        String Command = "XP -" + removeLevel + "L "+ player.getName();
        return true;
    }

    private boolean RemovePlayerLevel(Player player, int RemoveLevel){
        int currentLevel = GetPlayerLevel(player);
        int NewLevel = currentLevel - RemoveLevel;
        if (NewLevel < 0) return false;

        String Command = "XP -" + RemoveLevel + "L "+ player.getName();
        RunCommand(Command);
        return true;
    }

    private void DeleteItemFromHand(Player player){
        player.setItemInHand(HandTypes.MAIN_HAND, null);
    }

    public Boolean HaveItemInHand(Player player,ItemStack item){
        if(item == null) return false;

        return player.getItemInHand(HandTypes.MAIN_HAND).isPresent() && item.equalTo(player.getItemInHand(HandTypes.MAIN_HAND).get());
    }


    private void RunCommand(String Command){
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Command);
    }
    public void RunPlayerCommand(Player player, String Command){
        Sponge.getCommandManager().process(player, Command);
    }


    private ItemStack StringToItemStack(String item, int count){
        if (item == null) return null;
        if (item.equals("")) return null;

        if(Sponge.getRegistry().getType(ItemType.class, item).isPresent()){
            return ItemStack.of(Sponge.getRegistry().getType(ItemType.class, item).get(), count);
        }
        return null;
    }
}
