package io.appartus.questsystem.utils;

import io.appartus.questsystem.Quests.Type1;
import io.appartus.questsystem.questsystem;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.spongepowered.api.data.type.HandTypes.MAIN_HAND;

/**
 * aa
 * Created by Alois on 12.12.2016.
 */
public class QuestUtils {
    private TileUtils tileutils = new TileUtils();
    //private questsystem plugin = new questsystem();
    //private CommentedConfigurationNode config = plugin.configNode;
    //private CommentedConfigurationNode configNode = plugin.rootNode();

    public Logger getLogger() {
        return questsystem.getLogger();
    }
    public CommentedConfigurationNode configNode(){
        return questsystem.getConfigNode();
    }


    public String getQuestType(TileEntity entity){
        return tileutils.getSignLine(entity,3);
    }

    public void runQuest(Player player, int QuestID){
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

        ConfigurationNode pickNode = configNode().getNode("Quests",Integer.toString(QuestID),"Quest Reward");
        List<String> pickList;
        pickList = pickNode.getList(stringTransformer);

        getLogger().info(pickList.get(0));



    }


    //public void Quest1(Player player);
    public void GiveItem(Player player, ItemStack item){
        if (item == null) return;
        player.getInventory().offer(item);

    }

    public void DeleteItemFromHand(Player player){
        player.setItemInHand(HandTypes.MAIN_HAND, null);
    }

    public Boolean HaveItemInHand(Player player,ItemStack item){
        if(item == null) return false;

        if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
            return item.equalTo(player.getItemInHand(HandTypes.MAIN_HAND).get());
        }
        return false;
    }

    public ItemStack StringToItemStack(String item,int count){
        if (item == null) return null;
        if (item == "") return null;

        if(Sponge.getRegistry().getType(ItemType.class, item).isPresent()){
            return ItemStack.of(Sponge.getRegistry().getType(ItemType.class, item).get(), count);
        }
        return null;
    }
}
