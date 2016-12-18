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
 * aa
 * Created by Alois on 12.12.2016.
 */
public class QuestUtils {
    private TileUtils tileutils = new TileUtils();
    private questsystem plugin = new questsystem();
    private CommentedConfigurationNode config = plugin.configNode;

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

    public String LoadQuests(){
       return config.getNode("Welcome").getString();
    }
}
