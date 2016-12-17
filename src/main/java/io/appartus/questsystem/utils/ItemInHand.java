package io.appartus.questsystem.utils;

import org.spongepowered.api.entity.living.player.Player;

import static org.spongepowered.api.data.type.HandTypes.MAIN_HAND;

/**
 * Created by Alois on 13.12.2016.
 */
public class ItemInHand {

    public String getItemName(Player player){
        String[] inHand = player.getItemInHand(MAIN_HAND).get().toString().split("@");
        inHand = inHand[0].split("x");
        return inHand[1];
    }

    public int getItemCount(Player player){
        String[] inHand = player.getItemInHand(MAIN_HAND).get().toString().split("@");
        inHand = inHand[0].split("x");
        return Integer.parseInt(inHand[0]);
    }


}
