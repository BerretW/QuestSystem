package io.appartus.questsystem.Events;

import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import io.appartus.questsystem.utils.*;

import javax.smartcardio.TerminalFactory;


/**
 * Created by Alois on 12.12.2016.
 */
public class onInteractBlock {
    TileUtils utils = new TileUtils();
    QuestUtils questUtils = new QuestUtils();
    //ItemInHand itemInHand = new ItemInHand();




    @Listener
    public void onInteract(InteractBlockEvent.Primary event, @First Player player){
        if (event.getTargetBlock().getLocation().isPresent()) {
            Location<World> location = event.getTargetBlock().getLocation().get();

            if (location.getTileEntity().isPresent()) {
                TileEntity tile = location.getTileEntity().get();
                if(utils.haveSignData(tile)){
                    player.sendMessage(Text.of("Je tam ",utils.getSignData(tile)));
                }
                if(utils.getSignData(tile)) {
                    questUtils.giveItem(player, questUtils.StringToItemStack(utils.getSignLine(tile, 0), 1));
                } else{
                    //player.sendMessage(Text.of(questUtils.LoadQuests()));

                    if(questUtils.HasItemInHand(player, questUtils.StringToItemStack(utils.getSignLine(tile, 0),1))){
                        questUtils.giveItem(player,questUtils.StringToItemStack("minecraft:diamond",1));
                        player.setItemInHand(HandTypes.MAIN_HAND, null);

                    }
                }
            }
        }
    }
    @Listener
    public void onInteract2(InteractBlockEvent.Secondary event, @First Player player){
        if (event.getTargetBlock().getLocation().isPresent()) {
            Location<World> location = event.getTargetBlock().getLocation().get();
            if (location.getTileEntity().isPresent()) {
                TileEntity tile = location.getTileEntity().get();
                if(utils.getSignData(tile)){
                    utils.setSignData(tile,false);
                    player.sendMessage(Text.of("Nastaveno na ", utils.getSignData(tile)));
                } else {
                    utils.setSignData(tile,true);
                    player.sendMessage(Text.of("Nastaveno na ", utils.getSignData(tile)));
                }
            }
        }
    }


}
