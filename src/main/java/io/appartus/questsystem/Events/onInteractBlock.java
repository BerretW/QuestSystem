package io.appartus.questsystem.Events;

import io.appartus.questsystem.questsystem;
import org.slf4j.Logger;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import io.appartus.questsystem.utils.*;


/**
 * onIteractBlock event functions
 * Created by Alois on 12.12.2016.
 */
public class onInteractBlock {
    private TileUtils tileUtils = new TileUtils();
    private QuestUtils questUtils = new QuestUtils();

    private boolean RunQuest(Player player, int QuestID){
        return questUtils.runQuest(player,QuestID);
    }
    private Logger getLogger() {
        return questsystem.getLogger();
    }

    @Listener
    public void Click_to_runQuest(InteractBlockEvent.Primary event, @First Player player) {
        TileEntity tile = null;
        if (event.getTargetBlock().getLocation().isPresent()) {
            Location<World> location = event.getTargetBlock().getLocation().get();
            if (location.getTileEntity().isPresent()) {
                if (location.getTileEntity().get().getType().equals(TileEntityTypes.SIGN))
                    tile = location.getTileEntity().get();
                else return;
            }
        }
        if (tile != null) {
            if (tileUtils.haveSignData(tile)) {
                int i;
                if (tileUtils.getSignData(tile)) {
                    event.setCancelled(true);
                    try {
                        i = Integer.parseInt(tileUtils.getSignLine(tile, 3));
                        getLogger().info(Integer.toString(i));
                        if (i > 0) RunQuest(player, i);
                    } catch (NumberFormatException e) {

                    }
                }
            }
        }

    }


    @Listener
    public void Creator_onInteract(InteractBlockEvent.Secondary event, @First Player player){
        if (event.getTargetBlock().getLocation().isPresent()) {
            Location<World> location = event.getTargetBlock().getLocation().get();
            if (location.getTileEntity().isPresent() && player.hasPermission(questsystem.Quest_Creator_Permission) && questUtils.HaveItemInHand(player,questsystem.Creator_Tool)) {
                TileEntity tile = location.getTileEntity().get();

                if(!tileUtils.isSign(tile)) return;
                if(tileUtils.getSignData(tile)){
                    tileUtils.setSignData(tile,false);
                    player.sendMessage(Text.of("Quest restricted"));
                } else {
                    tileUtils.setSignData(tile,true);
                    player.sendMessage(Text.of("Quest enabled"));
                }
            }
        }
    }
}
