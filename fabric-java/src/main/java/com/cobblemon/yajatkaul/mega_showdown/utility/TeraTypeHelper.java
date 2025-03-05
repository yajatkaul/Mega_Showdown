package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import net.minecraft.item.Item;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

public class TeraTypeHelper {
    private static final Map<Item, TeraType> ITEM_TO_TERA_MAP = new HashMap<>();

    public static void loadShardData (){
        ITEM_TO_TERA_MAP.put(TeraMoves.BUG_TERA_SHARD, TeraTypes.getBUG());
        ITEM_TO_TERA_MAP.put(TeraMoves.DARK_TERA_SHARD, TeraTypes.getDARK());
        ITEM_TO_TERA_MAP.put(TeraMoves.DRAGON_TERA_SHARD, TeraTypes.getDRAGON());
        ITEM_TO_TERA_MAP.put(TeraMoves.ELECTRIC_TERA_SHARD, TeraTypes.getELECTRIC());
        ITEM_TO_TERA_MAP.put(TeraMoves.FAIRY_TERA_SHARD, TeraTypes.getFAIRY());
        ITEM_TO_TERA_MAP.put(TeraMoves.FIGHTING_TERA_SHARD, TeraTypes.getFIGHTING());
        ITEM_TO_TERA_MAP.put(TeraMoves.FIRE_TERA_SHARD, TeraTypes.getFIRE());
        ITEM_TO_TERA_MAP.put(TeraMoves.FLYING_TERA_SHARD, TeraTypes.getFLYING());
        ITEM_TO_TERA_MAP.put(TeraMoves.GHOST_TERA_SHARD, TeraTypes.getGHOST());
        ITEM_TO_TERA_MAP.put(TeraMoves.GRASS_TERA_SHARD, TeraTypes.getGRASS());
        ITEM_TO_TERA_MAP.put(TeraMoves.GROUND_TERA_SHARD, TeraTypes.getGROUND());
        ITEM_TO_TERA_MAP.put(TeraMoves.ICE_TERA_SHARD, TeraTypes.getICE());
        ITEM_TO_TERA_MAP.put(TeraMoves.NORMAL_TERA_SHARD, TeraTypes.getNORMAL());
        ITEM_TO_TERA_MAP.put(TeraMoves.POISON_TERA_SHARD, TeraTypes.getPOISON());
        ITEM_TO_TERA_MAP.put(TeraMoves.PSYCHIC_TERA_SHARD, TeraTypes.getPSYCHIC());
        ITEM_TO_TERA_MAP.put(TeraMoves.ROCK_TERA_SHARD, TeraTypes.getROCK());
        ITEM_TO_TERA_MAP.put(TeraMoves.STEEL_TERA_SHARD, TeraTypes.getSTEEL());
        ITEM_TO_TERA_MAP.put(TeraMoves.STELLAR_TERA_SHARD, TeraTypes.getSTELLAR());
        ITEM_TO_TERA_MAP.put(TeraMoves.WATER_TERA_SHARD, TeraTypes.getWATER());
    }

    public static TeraType getType(Item item) {
        return ITEM_TO_TERA_MAP.getOrDefault(item, TeraTypes.getNORMAL());
    }

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
        throw new IllegalArgumentException("Unknown Pokémon types: " + types);
    }

    public static Formatting getGlowColorForType(Pokemon pokemon) {
        Iterable<ElementalType> types = pokemon.getTypes();

        for (ElementalType type : types) {
            if (type.equals(ElementalTypes.INSTANCE.getBUG())) return Formatting.DARK_GREEN;
            if (type.equals(ElementalTypes.INSTANCE.getDARK())) return Formatting.BLACK;
            if (type.equals(ElementalTypes.INSTANCE.getDRAGON())) return Formatting.DARK_BLUE;
            if (type.equals(ElementalTypes.INSTANCE.getELECTRIC())) return Formatting.YELLOW;
            if (type.equals(ElementalTypes.INSTANCE.getFAIRY())) return Formatting.LIGHT_PURPLE;
            if (type.equals(ElementalTypes.INSTANCE.getFIGHTING())) return Formatting.DARK_RED;
            if (type.equals(ElementalTypes.INSTANCE.getFIRE())) return Formatting.RED;
            if (type.equals(ElementalTypes.INSTANCE.getFLYING())) return Formatting.GRAY;
            if (type.equals(ElementalTypes.INSTANCE.getGHOST())) return Formatting.DARK_PURPLE;
            if (type.equals(ElementalTypes.INSTANCE.getGRASS())) return Formatting.GREEN;
            if (type.equals(ElementalTypes.INSTANCE.getGROUND())) return Formatting.DARK_RED;
            if (type.equals(ElementalTypes.INSTANCE.getICE())) return Formatting.BLUE;
            if (type.equals(ElementalTypes.INSTANCE.getNORMAL())) return Formatting.WHITE;
            if (type.equals(ElementalTypes.INSTANCE.getPOISON())) return Formatting.DARK_PURPLE;
            if (type.equals(ElementalTypes.INSTANCE.getPSYCHIC())) return Formatting.LIGHT_PURPLE;
            if (type.equals(ElementalTypes.INSTANCE.getROCK())) return Formatting.DARK_GRAY;
            if (type.equals(ElementalTypes.INSTANCE.getSTEEL())) return Formatting.GRAY;
            if (type.equals(ElementalTypes.INSTANCE.getWATER())) return Formatting.BLUE;
        }
        throw new IllegalArgumentException("Unknown Pokémon types: " + types);
    }

    public static Formatting getGlowColorForTeraType(TeraType teraType) {
        if (teraType.equals(TeraTypes.getBUG())) return Formatting.DARK_GREEN;
        if (teraType.equals(TeraTypes.getDARK())) return Formatting.BLACK;
        if (teraType.equals(TeraTypes.getDRAGON())) return Formatting.DARK_BLUE;
        if (teraType.equals(TeraTypes.getELECTRIC())) return Formatting.YELLOW;
        if (teraType.equals(TeraTypes.getFAIRY())) return Formatting.LIGHT_PURPLE;
        if (teraType.equals(TeraTypes.getFIGHTING())) return Formatting.DARK_RED;
        if (teraType.equals(TeraTypes.getFIRE())) return Formatting.RED;
        if (teraType.equals(TeraTypes.getFLYING())) return Formatting.GRAY;
        if (teraType.equals(TeraTypes.getGHOST())) return Formatting.DARK_PURPLE;
        if (teraType.equals(TeraTypes.getGRASS())) return Formatting.GREEN;
        if (teraType.equals(TeraTypes.getGROUND())) return Formatting.DARK_RED;
        if (teraType.equals(TeraTypes.getICE())) return Formatting.BLUE;
        if (teraType.equals(TeraTypes.getNORMAL())) return Formatting.WHITE;
        if (teraType.equals(TeraTypes.getPOISON())) return Formatting.DARK_PURPLE;
        if (teraType.equals(TeraTypes.getPSYCHIC())) return Formatting.LIGHT_PURPLE;
        if (teraType.equals(TeraTypes.getROCK())) return Formatting.DARK_GRAY;
        if (teraType.equals(TeraTypes.getSTEEL())) return Formatting.GRAY;
        if (teraType.equals(TeraTypes.getWATER())) return Formatting.BLUE;

        throw new IllegalArgumentException("Unknown TeraType: " + teraType);
    }
}
