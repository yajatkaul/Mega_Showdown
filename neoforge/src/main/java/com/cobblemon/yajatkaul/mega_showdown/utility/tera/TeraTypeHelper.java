package com.cobblemon.yajatkaul.mega_showdown.utility.tera;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class TeraTypeHelper {
    private static final Map<Item, TeraType> ITEM_TO_TERA_MAP = new HashMap<>();

    public static void loadShardData() {
        ITEM_TO_TERA_MAP.put(TeraMoves.BUG_TERA_SHARD.get(), TeraTypes.getBUG());
        ITEM_TO_TERA_MAP.put(TeraMoves.DARK_TERA_SHARD.get(), TeraTypes.getDARK());
        ITEM_TO_TERA_MAP.put(TeraMoves.DRAGON_TERA_SHARD.get(), TeraTypes.getDRAGON());
        ITEM_TO_TERA_MAP.put(TeraMoves.ELECTRIC_TERA_SHARD.get(), TeraTypes.getELECTRIC());
        ITEM_TO_TERA_MAP.put(TeraMoves.FAIRY_TERA_SHARD.get(), TeraTypes.getFAIRY());
        ITEM_TO_TERA_MAP.put(TeraMoves.FIGHTING_TERA_SHARD.get(), TeraTypes.getFIGHTING());
        ITEM_TO_TERA_MAP.put(TeraMoves.FIRE_TERA_SHARD.get(), TeraTypes.getFIRE());
        ITEM_TO_TERA_MAP.put(TeraMoves.FLYING_TERA_SHARD.get(), TeraTypes.getFLYING());
        ITEM_TO_TERA_MAP.put(TeraMoves.GHOST_TERA_SHARD.get(), TeraTypes.getGHOST());
        ITEM_TO_TERA_MAP.put(TeraMoves.GRASS_TERA_SHARD.get(), TeraTypes.getGRASS());
        ITEM_TO_TERA_MAP.put(TeraMoves.GROUND_TERA_SHARD.get(), TeraTypes.getGROUND());
        ITEM_TO_TERA_MAP.put(TeraMoves.ICE_TERA_SHARD.get(), TeraTypes.getICE());
        ITEM_TO_TERA_MAP.put(TeraMoves.NORMAL_TERA_SHARD.get(), TeraTypes.getNORMAL());
        ITEM_TO_TERA_MAP.put(TeraMoves.POISON_TERA_SHARD.get(), TeraTypes.getPOISON());
        ITEM_TO_TERA_MAP.put(TeraMoves.PSYCHIC_TERA_SHARD.get(), TeraTypes.getPSYCHIC());
        ITEM_TO_TERA_MAP.put(TeraMoves.ROCK_TERA_SHARD.get(), TeraTypes.getROCK());
        ITEM_TO_TERA_MAP.put(TeraMoves.STEEL_TERA_SHARD.get(), TeraTypes.getSTEEL());
        ITEM_TO_TERA_MAP.put(TeraMoves.STELLAR_TERA_SHARD.get(), TeraTypes.getSTELLAR());
        ITEM_TO_TERA_MAP.put(TeraMoves.WATER_TERA_SHARD.get(), TeraTypes.getWATER());
    }

    public static TeraType getType(Item item) {
        return ITEM_TO_TERA_MAP.getOrDefault(item, TeraTypes.getNORMAL());
    }

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
