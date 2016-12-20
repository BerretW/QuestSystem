package io.appartus.questsystem.utils;

import io.appartus.questsystem.data.iscommandsign.IsCommandSignData;
import io.appartus.questsystem.data.iscommandsign.SpongeIsCommandSignData;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityTypes;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.text.Text;

import java.util.Optional;

/**Tile utils
 * Created by Alois on 12.12.2016.
 */
public class TileUtils {



    public String getSignLine(TileEntity tile, int line) {
        if(tile == null) return null;
        if (!isSign(tile)) return null;

        String lineString;

        Optional<SignData> data = tile.getOrCreate(SignData.class);
        if (data.isPresent()) {
            lineString = data.get().lines().get(line).toString();
            lineString = lineString.substring(5,lineString.length());
            lineString = lineString.replace("}", "");
            return lineString;
        }
        return null;
    }

    public boolean setSignLine(TileEntity tile, int line, Text text) {
        if(tile == null) return false;
        if (!isSign(tile)) return false;
        if(line > 3 ) return false;
        if(line < 0 ) return false;

        if (tile.supports(SignData.class)) {
            if (tile.getOrCreate(SignData.class).isPresent()){
                SignData sign = tile.getOrCreate(SignData.class).get();
                sign.set(sign.lines().set(line, text));
                tile.offer(sign);
                return true;
            }

        }
        return false;
    }

    public boolean haveSignData(TileEntity tile){
        if(tile == null) return false;
        if (!isSign(tile)) return false;

        Optional<IsCommandSignData> isCommandSignData = tile.get(IsCommandSignData.class);
        return isCommandSignData.isPresent();
    }
    public boolean setSignData(TileEntity tile,boolean value){
        if(tile == null) return false;
        if (!isSign(tile)) return false;
        tile.offer(new SpongeIsCommandSignData(value));
        return true;
    }

    public boolean getSignData(TileEntity tile){
        if(tile == null) return false;
        if (!isSign(tile)) return false;

        Optional<IsCommandSignData> isCommandSignData;

        if (haveSignData(tile)){
            isCommandSignData = tile.get(IsCommandSignData.class);
            if (isCommandSignData.isPresent()){
                return isCommandSignData.get().isCommandSign().get();
            }
        }
            return setSignData(tile,true);

    }

    public boolean isSign(TileEntity tile){
        return tile != null && tile.getType().equals(TileEntityTypes.SIGN);
    }

}
