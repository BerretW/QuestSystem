package io.appartus.questsystem;

import com.google.common.eventbus.Subscribe;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import io.appartus.questsystem.Events.*;
import io.appartus.questsystem.data.iscommandsign.*;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;

/**
 * Created by Alois on 11.12.2016.
 */
@Plugin(id="appartusquestsystem",name = "AppartusQuestSystem", version = "0.0.1")
public class questsystem {


    public static String[][][] questlist;


    public static final Key<Value<Boolean>> IS_COMMAND_SIGN = KeyFactory.makeSingleKey(new TypeToken<Boolean>() {} , new TypeToken<Value<Boolean>>() {}, DataQuery.of("IsCommandSign"), "commandsigns:is_command_sign", "Whether a sign is a CommandSign");

    public static String[]QuestIDs;

    public static int Quest_ID;
    public static String Quest_Name;
    public static String Quest_Req;
    public static String Quest_Rew1;
    public static String Quest_Rew2;
    public static String Quest_Rev3;

    private List<String> Quest1_Req = new ArrayList<String>();
    private List<String> Quest2_Req = new ArrayList<String>();
    private List<String> Quest1_Rew = new ArrayList<String>();
    private List<String> Quest2_Rew = new ArrayList<String>();
    @Inject
    Game game;


    private static Logger logger;
    @Inject
    public questsystem(Logger logger) {
    questsystem.logger = logger;
    }

    public static Logger getLogger(){
        return logger;
    }
    public static CommentedConfigurationNode configNode;

    @Inject
    @DefaultConfig(sharedRoot = true)
    public File config = null;

    @Inject
    @DefaultConfig(sharedRoot = true)
    public ConfigurationLoader<CommentedConfigurationNode> configLoader = null;

    public static CommentedConfigurationNode getConfigNode(){
        return configNode;
    }

    @Listener
    public void onInit(GameInitializationEvent event){
        try {
            if(!config.exists()){
                config.createNewFile();
                configNode = configLoader.load();
                Quest1_Req.add("Level|30");
                Quest1_Rew.add("Command|say Hrac si prave udelal prvni Tier");
                Quest1_Rew.add("Command|pm users @p group add MAT1");
                configNode.getNode("Quests","1","Quest Name").setValue("Learn Mystical Agriculture T2");
                configNode.getNode("Quests","1","Quest Type").setValue(1);
                configNode.getNode("Quests","1","Quest Requirements").setValue(Quest1_Req);
                configNode.getNode("Quests","1","Quest Reward").setValue(Quest1_Rew);
                configNode.getNode("Quests","1","Quest Lore").setValue("Pro nauceni techto vedomosti je vzadovani mit level 30");

                Quest2_Req.add("Level|30");
                Quest2_Req.add("Item|minecraft:stone");
                Quest2_Rew.add("Command|say Hrac si prave udelal druhy Tier");
                Quest2_Rew.add("Command|pm users @p group add MAT2");
                Quest2_Rew.add("Item|minecraft:diamond");
                configNode.getNode("Quests","2","Quest Name").setValue("Learn Mystical Agriculture T2");
                configNode.getNode("Quests","2","Quest Type").setValue(2);
                configNode.getNode("Quests","2","Quest Requirements").setValue(Quest2_Req);
                configNode.getNode("Quests","2","Quest Reward").setValue(Quest2_Rew);
                configNode.getNode("Quests","2","Quest Lore").setValue("Pro nauceni techto vedomosti je vzadovani mit level 30");
                configLoader.save(configNode);
                getLogger().info("Config created");
            }
            configNode = configLoader.load();
            getLogger().info("Config Loaded");

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
            getLogger().info("Failed to load config");
        }
    }





}
