package io.appartus.questsystem.Commands;

import org.spongepowered.api.Sponge;

/**
 * Created by Alois on 13.12.2016.
 */
public class ConsoleCommand {

    public static void RunCommand(String Command){
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), Command);
    }
}
