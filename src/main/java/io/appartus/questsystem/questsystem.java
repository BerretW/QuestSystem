package io.appartus.questsystem;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import io.appartus.questsystem.Events.*;
import io.appartus.questsystem.data.iscommanddata.*;

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
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

/** Inital file
 * Created by Alois on 11.12.2016.
 */
@Plugin(id="appartusquestsystem",name = "AppartusQuestSystem", version = "1.0")
public class questsystem {
    public static final Key<Value<Boolean>> IS_COMMAND_SIGN = KeyFactory.makeSingleKey(new TypeToken<Boolean>() {} , new TypeToken<Value<Boolean>>() {}, DataQuery.of("IsCommandSign"), "commandsigns:is_command_sign", "Whether a sign is a CommandSign");
    public static final Key<Value<Boolean>> IS_COMMAND_DATA = KeyFactory.makeSingleKey(new TypeToken<Boolean>() {} , new TypeToken<Value<Boolean>>() {}, DataQuery.of("IsCommandData"), "commandsigns:is_command_data", "Whether a sign has a Command Data");

    private List<String> Quest1_Req = new ArrayList<>();
    private List<String> Quest2_Req = new ArrayList<>();
    private List<String> Quest1_Rew = new ArrayList<>();
    private List<String> Quest2_Rew = new ArrayList<>();
    public static String Quest_Creator_Permission = "AQS.creator";
    public static ItemStack Creator_Tool = ItemStack.of(ItemTypes.CARROT_ON_A_STICK,1) ;




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
    private static CommentedConfigurationNode configNode;

    public static CommentedConfigurationNode getConfigNode(){
        return configNode;
    }

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File config = null;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configLoader = null;

    @Listener
    public void onReload(GameReloadEvent event){
        LoadConfig();
        getLogger().info("Config ReLoaded");
    }

    @Listener
    public void onInit(GameInitializationEvent event){
        try {
            if(!config.exists()){
                if (config.createNewFile()) getLogger().info("Config File created");
                else getLogger().info("Error with creating config file");
                configNode = configLoader.load();
                Quest1_Req.add("Level||30");
                Quest1_Req.add("PermNot||appartus.MAT1");

                Quest1_Rew.add("Command||say Hrac si prave udelal prvni Tier");
                Quest1_Rew.add("Command||pm users @p add group MAT1");
                Quest1_Rew.add("Command||pm users @p set permission appartus.MAT1");
                configNode.getNode("1","Quest Name").setValue("Learn Mystical Agriculture T1");
                configNode.getNode("1","Quest Requirements").setValue(Quest1_Req);
                configNode.getNode("1","Quest Reward").setValue(Quest1_Rew);
                configNode.getNode("1","Quest Lore").setValue("Pro nauceni techto vedomosti je vzadovani mit level 30");

                Quest2_Req.add("Level||30");
                Quest2_Req.add("Item||minecraft:stone|1");
                Quest2_Req.add("PermNot||appartus.MAT2");
                Quest2_Req.add("Perm||appartus.MAT1");

                Quest2_Rew.add("Command||say Hrac si prave udelal druhy Tier");
                Quest2_Rew.add("Command||pm users @p add group MAT2");
                Quest2_Rew.add("Command||pm users @p set permission appartus.MAT2");
                Quest2_Rew.add("Item||minecraft:diamond|1");
                configNode.getNode("2","Quest Name").setValue("Learn Mystical Agriculture T2");
                configNode.getNode("2","Quest Requirements").setValue(Quest2_Req);
                configNode.getNode("2","Quest Reward").setValue(Quest2_Rew);
                configNode.getNode("2","Quest Lore").setValue("Pro nauceni techto vedomosti je vzadovani mit level 30");
                configLoader.save(configNode);
                getLogger().info("Config created");
            }
            LoadConfig();
            getLogger().info("Config Loaded");

        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        game.getEventManager().registerListeners(this, new onInteractBlock() );
        // IsCommandSign
        //Sponge.getDataManager().register();
        Sponge.getDataManager().register(IsCommandSignData.class, ImmutableIsCommandSignData.class, new IsCommandSignDataBuilder());
        Sponge.getDataManager().register(SpongeIsCommandSignData.class, ImmutableSpongeIsCommandSignData.class, new IsCommandSignDataBuilder());
    }

    public void LoadConfig(){
        try{
            configNode = configLoader.load();
        } catch (IOException e){
            getLogger().info("Failed to load config");
        }
    }
    public ItemStack StringToItemStack(String item, int count){
        if (item == null) return null;
        if (item.equals("")) return null;

        if(Sponge.getRegistry().getType(ItemType.class, item).isPresent()){
            return ItemStack.of(Sponge.getRegistry().getType(ItemType.class, item).get(), count);
        }
        return null;
    }





}
