package utils;

import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Alois on 12.12.2016.
 */
public class QuestUtils {
    TileUtils tileutils = new TileUtils();

    public String getQuestType(TileEntity entity){
        return tileutils.getTileLine(entity,0).get().toString();
    }

    public void runQuest(int Type, Player player, TileEntity entity){
        switch (getQuestType(entity)) {
            case "1":
                runQuest1(player, entity);
                break;
            case "2":
                runQuest2();
                break;
        }
    }

    public void runQuest1(Player player, TileEntity entity) {
        int Level = Integer.parseInt(tileutils.getTileLine(entity, 2).get().toString());
        
    }
    public void runQuest2(){

    }
}
