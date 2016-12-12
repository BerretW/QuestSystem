package utils;

import org.spongepowered.api.block.tileentity.TileEntity;

/**
 * Created by Alois on 12.12.2016.
 */
public class QuestUtils {
    TileUtils tileutils = new TileUtils();

    public void getQuestType(TileEntity entity){
        tileutils.getTileLine(entity,0);
    }
}
