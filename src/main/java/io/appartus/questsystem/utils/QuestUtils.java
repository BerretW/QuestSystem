package io.appartus.questsystem.utils;

import io.appartus.questsystem.Quests.Type1;
import io.appartus.questsystem.questsystem;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Optional;

import static org.spongepowered.api.data.type.HandTypes.MAIN_HAND;

/**
 * Created by Alois on 12.12.2016.
 */
public class QuestUtils {
    TileUtils tileutils = new TileUtils();
    questsystem plugin = new questsystem();
    CommentedConfigurationNode config = plugin.configNode;

    public String getQuestType(TileEntity entity){
        return tileutils.getSignLine(entity,3);
    }

    public void runQuest(Player player, TileEntity entity){
        String Type = tileutils.getSignLine(entity,0);
        Type1 Quest1 = new Type1();

        //player.sendMessage(Text.of(Type));

        switch (tileutils.getSignLine(entity,0)) {
            case "1":
                Quest1.runQuest(player,entity);
                break;
        }
    }

    private boolean notNull(TileEntity entity,int lines){
        int Line = 0;
        while (Line <= lines){
            if(tileutils.getSignLine(entity, Line) == "") return false;
            Line ++;
        }
        return true;
    }

    public void giveItem(Player player, ItemStack item){
        //ItemStack itemStack;
        //Optional<ItemType> optionalItemType;
        //optionalItemType = Sponge.getRegistry().getType(ItemType.class, Item);

            //itemStack = ItemStack.of(Sponge.getRegistry().getType(ItemType.class, Item).get(), count);
            player.getInventory().offer(item);

    }

    public Boolean HasItemInHand(Player player,ItemStack item){
        //Optional<ItemStack> inHand;
        if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
            //return player.getItemInHand(HandTypes.MAIN_HAND).get();
            player.sendMessage(Text.of("Have in Hand ", player.getItemInHand(HandTypes.MAIN_HAND).get().toString()));
            player.sendMessage(Text.of("Need ", item.toString()));

            return item.equalTo(player.getItemInHand(HandTypes.MAIN_HAND).get());
        }
        return false;
    }

    public ItemStack StringToItemStack(String item,int count){
        return ItemStack.of(Sponge.getRegistry().getType(ItemType.class, item).get(), count);
    }

    public String LoadQuests(){

       return config.getNode("Welcome").getString();
    }
}
