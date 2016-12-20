package io.appartus.questsystem.Events;

import io.appartus.questsystem.questsystem;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import io.appartus.questsystem.utils.*;
import java.util.Optional;


/**
 * onIteractBlock event functions
 * Created by Alois on 12.12.2016.
 */
public class onInteractBlock {
    private TileUtils tileUtils = new TileUtils();
    private QuestUtils questUtils = new QuestUtils();
    //private questsystem plugin = new questsystem();


    @Listener
    public void onInteract(InteractBlockEvent.Primary event, @First Player player){
        questUtils.runQuest(player,2);




        //player.sendMessage(Text.of(plugin.QuestType));

        //player.sendMessage(Text.of(plugin.rootNode().getNode("Welcome").getString()));
    }

    @Listener
    public void onInteract2(InteractBlockEvent.Secondary event, @First Player player){
        if (event.getTargetBlock().getLocation().isPresent()) {
            Location<World> location = event.getTargetBlock().getLocation().get();
            if (location.getTileEntity().isPresent()) {
                TileEntity tile = location.getTileEntity().get();
                if(tileUtils.getSignData(tile)){
                    tileUtils.setSignData(tile,false);
                    player.sendMessage(Text.of("Nastaveno na ", tileUtils.getSignData(tile)));
                } else {
                    tileUtils.setSignData(tile,true);
                    player.sendMessage(Text.of("Nastaveno na ", tileUtils.getSignData(tile)));
                }
            }
        }
    }
}
