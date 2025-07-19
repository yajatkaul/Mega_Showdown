package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class TeraTypeHelper {
    public static Item getTeraShardForType(Iterable<ElementalType> types) {
        for (ElementalType type : types) {
            if (type.equals(ElementalTypes.INSTANCE.getBUG())) return TeraMoves.BUG_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getDARK())) return TeraMoves.DARK_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getDRAGON())) return TeraMoves.DRAGON_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getELECTRIC())) return TeraMoves.ELECTRIC_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFAIRY())) return TeraMoves.FAIRY_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFIGHTING())) return TeraMoves.FIGHTING_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFIRE())) return TeraMoves.FIRE_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFLYING())) return TeraMoves.FLYING_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGHOST())) return TeraMoves.GHOST_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGRASS())) return TeraMoves.GRASS_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGROUND())) return TeraMoves.GROUND_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getICE())) return TeraMoves.ICE_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getNORMAL())) return TeraMoves.NORMAL_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getPOISON())) return TeraMoves.POISON_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getPSYCHIC())) return TeraMoves.PSYCHIC_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getROCK())) return TeraMoves.ROCK_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getSTEEL())) return TeraMoves.STEEL_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getWATER())) return TeraMoves.WATER_TERA_SHARD;
        }
        return TeraMoves.NORMAL_TERA_SHARD;
    }
}
