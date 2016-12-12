package Events;

import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import utils.TileUtils;

/**
 * Created by Alois on 12.12.2016.
 */
public class onInteractBlock {
    TileUtils utils = new TileUtils();

    @Listener
    public void onInteract(InteractBlockEvent event, @First Player player){
        if (event.getTargetBlock().getLocation().isPresent()) {
            Location<World> location = event.getTargetBlock().getLocation().get();

            if (location.getTileEntity().isPresent()) {
                TileEntity tile = location.getTileEntity().get();
                player.sendMessage(Text.of(utils.getTileLine(tile,0).get()));
                player.sendMessage(Text.of(utils.getTileLine(tile,1).get()));
                player.sendMessage(Text.of(utils.getTileLine(tile,2).get()));
                player.sendMessage(Text.of(utils.getTileLine(tile,3).get()));
            }
        }
    }

}
