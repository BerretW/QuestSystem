package io.appartus.questsystem;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import io.appartus.questsystem.Events.*;
import io.appartus.questsystem.data.iscommandsign.*;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;
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

    public static final Key<Value<Boolean>> IS_COMMAND_SIGN = KeyFactory.makeSingleKey(new TypeToken<Boolean>() {} , new TypeToken<Value<Boolean>>() {}, DataQuery.of("IsCommandSign"), "commandsigns:is_command_sign", "Whether a sign is a CommandSign");

    @Inject
    Game game;

    @Inject
    Logger logger;

    @Listener
    public void onInit(GameInitializationEvent event){
        game.getEventManager().registerListeners(this, new onInteractBlock() );

        // IsCommandSign
        Sponge.getDataManager().register(IsCommandSignData.class, ImmutableIsCommandSignData.class, new IsCommandSignDataBuilder());
        Sponge.getDataManager().register(SpongeIsCommandSignData.class, ImmutableSpongeIsCommandSignData.class, new IsCommandSignDataBuilder());


    }


// One-Time



}
