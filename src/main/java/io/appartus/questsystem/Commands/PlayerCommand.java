package io.appartus.questsystem.Commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Alois on 13.12.2016.
 */
public class PlayerCommand {
    public void RunCommand(Player player, String Command){
        Sponge.getCommandManager().process(player, Command);
    }
}
