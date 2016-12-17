package io.appartus.questsystem.utils;

import io.appartus.questsystem.Quests.Type1;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import static org.spongepowered.api.data.type.HandTypes.MAIN_HAND;

/**
 * Created by Alois on 12.12.2016.
 */
public class QuestUtils {
    TileUtils tileutils = new TileUtils();

    public String getQuestType(TileEntity entity){
        return tileutils.getSignLine(entity,3);
    }

    public void runQuest(Player player, TileEntity entity){
        String Type = tileutils.getSignLine(entity,0);
        Type1 Quest1 = new Type1();

        //player.sendMessage(Text.of(Type));

        switch (tileutils.getSignLine(entity,0)) {
            case "1":
                Quest1.runQuest(player,entity);
                break;
        }
    }




    private boolean notNull(TileEntity entity,int lines){
        int Line = 0;
        while (Line <= lines){
            if(tileutils.getSignLine(entity, Line) == "") return false;
            Line ++;
        }
        return true;
    }
}
