package io.appartus.questsystem.utils;

/**
 * Created by Alois on 13.12.2016.
 */
public class Block {
    public String BlockNameFromEvent(String BlockInfo){

        String Block_Type = Parse_Block_Name(BlockInfo);
        String Block_Mod = Parse_Block_Mod(BlockInfo);
        String Block_Info = Parse_Block_Info(BlockInfo);


        if (Block_Info == null) return Block_Mod + ":" + Block_Type;
        return Block_Mod + ":" + Block_Type + ":" + Block_Info;


    }


    public static String Parse_Block_Name(String BlockName){
        String[] Parse_Block_Name = BlockName.split("\\[");
        String[] Parse_Block_Type = Parse_Block_Name[0].split(":");
        String Block_Type = Parse_Block_Type[1];
        return Block_Type;
    }
    public static String Parse_Block_Mod (String BlockName){
        String[] Parse_Block_Mod = BlockName.split(":");
        String Block_Mod = Parse_Block_Mod[0];
        return Block_Mod;
    }
    public static String Parse_Block_Info (String BlockName){
        String Block_Info = null;
        if(BlockName.contains("type=")) {
            String[] Parse_Block_Info = BlockName.split("type=");
            Block_Info = Parse_Block_Info[1].substring(0,(Parse_Block_Info[1].length()-1));
            return Block_Info;
        }
        if(BlockName.contains("variant=")) {
            String[] Parse_Block_Info = BlockName.split("variant=");
            Block_Info = Parse_Block_Info[1].substring(0,(Parse_Block_Info[1].length()-1));
            return Block_Info;
        }
        return Block_Info;
    }

}
