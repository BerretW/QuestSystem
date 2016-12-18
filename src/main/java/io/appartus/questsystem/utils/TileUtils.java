package io.appartus.questsystem.utils;

import io.appartus.questsystem.Quests.Type1;
import io.appartus.questsystem.data.iscommandsign.IsCommandSignData;
import io.appartus.questsystem.data.iscommandsign.SpongeIsCommandSignData;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.text.Text;

import java.util.Optional;

/**
 * Created by Alois on 12.12.2016.
 */
public class TileUtils {



    public String getSignLine(TileEntity entity, int line) {
        if (!isSign(entity)) return null;

        String lineString;

        Optional<SignData> data = entity.getOrCreate(SignData.class);
        if (data.isPresent()) {
            lineString = data.get().lines().get(line).toString();
            lineString = lineString.substring(5,lineString.length());
            lineString = lineString.replace("}", "");
            return lineString;
        }
        return null;
    }

    public boolean setSignLine(TileEntity entity, int line, Text text) {
        if (!isSign(entity)) return false;
        if(line > 3 ) return false;
        if(line < 0 ) return false;

        if (entity.supports(SignData.class)) {
            SignData sign = entity.getOrCreate(SignData.class).get();
            sign.set(sign.lines().set(line, text));
            entity.offer(sign);
            return true;
        }
        return false;
    }

    public boolean haveSignData(TileEntity tile){
        if (!isSign(tile)) return false;

        Optional<IsCommandSignData> isCommandSignData = tile.get(IsCommandSignData.class);
        if(isCommandSignData.isPresent())return true;
        return false;
    }
    public boolean setSignData(TileEntity tile,boolean value){
        if (!isSign(tile)) return false;
        tile.offer(new SpongeIsCommandSignData(value));
        return true;
    }

    public boolean getSignData(TileEntity tile){

        Optional<IsCommandSignData> isCommandSignData;

        if (haveSignData(tile)){
            isCommandSignData = tile.get(IsCommandSignData.class);
            return isCommandSignData.get().isCommandSign().get();
        } else {
            return setSignData(tile,true);
        }
    }

    public boolean isSign(TileEntity tile){
        return tile.supports(SignData.class);
    }

}
