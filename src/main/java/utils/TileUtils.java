package utils;

import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.text.Text;

import java.util.Optional;

/**
 * Created by Alois on 12.12.2016.
 */
public class TileUtils {



    public Optional<Text> getTileLine(TileEntity entity, int line) {
        Optional<SignData> data = entity.getOrCreate(SignData.class);
        if (data.isPresent()) {
            return Optional.of(data.get().lines().get(line));
        }
        return Optional.empty();
    }
}
