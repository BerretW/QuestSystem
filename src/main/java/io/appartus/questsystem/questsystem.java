package io.appartus.questsystem;

import com.google.common.eventbus.Subscribe;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import io.appartus.questsystem.Events.*;
import io.appartus.questsystem.data.iscommandsign.*;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;

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


    @Inject
    @DefaultConfig(sharedRoot = true)
    public File config = null;

    @Inject
    @DefaultConfig(sharedRoot = true)
    public ConfigurationLoader<CommentedConfigurationNode> configLoader = null;

    public CommentedConfigurationNode configNode = null;

    @Listener
    public void onInit(GameInitializationEvent event){
        try {
            if(!config.exists()){
                config.createNewFile();
                configNode = configLoader.load();
                configNode.setValue("TestNode");
                configNode.getNode("test","bool").setValue(false);
                configNode.getNode("test","String").setValue("Hello");
                configNode.getNode("test").setComment("comment");
                configNode.getNode("Welcome").setValue("Ahoooooj");

                configLoader.save(configNode);
                logger.info("Config created");
            }
            configNode = configLoader.load();
            logger.info(configNode.getNode("Welcome").getString());
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        game.getEventManager().registerListeners(this, new onInteractBlock() );
        // IsCommandSign
        Sponge.getDataManager().register(IsCommandSignData.class, ImmutableIsCommandSignData.class, new IsCommandSignDataBuilder());
        Sponge.getDataManager().register(SpongeIsCommandSignData.class, ImmutableSpongeIsCommandSignData.class, new IsCommandSignDataBuilder());
    }

    public void LoadConfig(CommentedConfigurationNode config){
        try{
            config = configLoader.load();
        } catch (IOException e){
            logger.info("Failed to load config");
        }
    }

    public CommentedConfigurationNode rootNode(){
        return configNode;
    }

}
