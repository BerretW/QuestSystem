package net.appartus;

import Events.onInteractBlock;
import com.google.inject.Inject;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import java.util.logging.Logger;

/**
 * Created by Alois on 11.12.2016.
 */
@Plugin(id="appartusquestsystem",name = "AppartusQuestSystem", version = "0.0.1")
public class questsystem {

    public int QuestType;

    @Inject
    Game game;

    @Inject
    Logger logger;

    @Listener
    public void onInit(GameInitializationEvent event){
        game.getEventManager().registerListeners(this, new onInteractBlock());
    }



}
