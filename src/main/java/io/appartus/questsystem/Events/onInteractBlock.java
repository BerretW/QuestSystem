package io.appartus.questsystem.Events;

import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import io.appartus.questsystem.utils.*;


/**
 * Created by Alois on 12.12.2016.
 */
public class onInteractBlock {
    TileUtils utils = new TileUtils();
    QuestUtils questUtils = new QuestUtils();




    @Listener
    public void onInteract(InteractBlockEvent.Primary event, @First Player player){
        if (event.getTargetBlock().getLocation().isPresent()) {
            Location<World> location = event.getTargetBlock().getLocation().get();

            if (location.getTileEntity().isPresent()) {
                TileEntity tile = location.getTileEntity().get();
                if(utils.haveSignData(tile)){
                    player.sendMessage(Text.of("Je tam ",utils.getSignData(tile)));
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
