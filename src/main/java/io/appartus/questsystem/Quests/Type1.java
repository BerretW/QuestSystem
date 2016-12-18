package io.appartus.questsystem.Quests;

import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import io.appartus.questsystem.utils.TileUtils;

/**
 * Created by Alois on 12.12.2016.
 */
public class Type1 {
    TileUtils tileutils = new TileUtils();


    public void runQuest(Player player, TileEntity entity) {

        String PrevTier = tileutils.getSignLine(entity, 1);
        int Level = Integer.parseInt(tileutils.getSignLine(entity, 2));
        String Revard = tileutils.getSignLine(entity, 3);

        player.sendMessage(Text.of("Potrebujes Level ", Level,"."));
        player.sendMessage(Text.of("Potrebujes umet predchodzi Tier ", PrevTier, "."));
        player.sendMessage(Text.of("Dostanes za to opravneni na ", Revard, "."));
    }

    private int HaveLevel(Player player){
        return player.get(Keys.EXPERIENCE_LEVEL).get();
    }

    private int NeedLevel(TileEntity entity){
        return Integer.parseInt(tileutils.getSignLine(entity, 2));
    }

    private boolean HasPermission(Player player, String permission){
        if(player.hasPermission(permission)) return true;
        return false;
    }
    private int HasLevel(Player player){
        if(HaveLevel(player)  == 10) return 10;
        return player.get(Keys.EXPERIENCE_LEVEL).get();
    }



}
