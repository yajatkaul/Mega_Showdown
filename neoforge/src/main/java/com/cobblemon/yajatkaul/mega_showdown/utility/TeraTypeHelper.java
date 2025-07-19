package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class TeraTypeHelper {
    public static Item getTeraShardForType(Iterable<ElementalType> types) {
        var shard = TeraMoves.NORMAL_TERA_SHARD;
        for (ElementalType type : types) {
            if (type.equals(ElementalTypes.INSTANCE.getBUG())) shard = TeraMoves.BUG_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getDARK())) shard = TeraMoves.DARK_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getDRAGON())) shard = TeraMoves.DRAGON_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getELECTRIC())) shard = TeraMoves.ELECTRIC_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFAIRY())) shard = TeraMoves.FAIRY_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFIGHTING())) shard = TeraMoves.FIGHTING_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFIRE())) shard = TeraMoves.FIRE_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFLYING())) shard = TeraMoves.FLYING_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGHOST())) shard = TeraMoves.GHOST_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGRASS())) shard = TeraMoves.GRASS_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGROUND())) shard = TeraMoves.GROUND_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getICE())) shard = TeraMoves.ICE_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getNORMAL())) shard = TeraMoves.NORMAL_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getPOISON())) shard = TeraMoves.POISON_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getPSYCHIC())) shard = TeraMoves.PSYCHIC_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getROCK())) shard = TeraMoves.ROCK_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getSTEEL())) shard = TeraMoves.STEEL_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getWATER())) shard = TeraMoves.WATER_TERA_SHARD;
        }
        return shard.get();
    }
}
