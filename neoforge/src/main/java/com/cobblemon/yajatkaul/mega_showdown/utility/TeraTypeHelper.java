package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashMap;
import java.util.Map;

public class TeraTypeHelper {
    private static final Map<Item, TeraType> ITEM_TO_TERA_MAP = new HashMap<>();

    public static void loadShardData (){
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

    public static DeferredItem<Item> getTeraShardForType(Iterable<ElementalType> types) {
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

    public static ChatFormatting getGlowColorForType(Pokemon pokemon) {
        Iterable<ElementalType> types = pokemon.getTypes();

        for (ElementalType type : types) {
            if (type.equals(ElementalTypes.INSTANCE.getBUG())) return ChatFormatting.DARK_GREEN;
            if (type.equals(ElementalTypes.INSTANCE.getDARK())) return ChatFormatting.BLACK;
            if (type.equals(ElementalTypes.INSTANCE.getDRAGON())) return ChatFormatting.DARK_BLUE;
            if (type.equals(ElementalTypes.INSTANCE.getELECTRIC())) return ChatFormatting.YELLOW;
            if (type.equals(ElementalTypes.INSTANCE.getFAIRY())) return ChatFormatting.LIGHT_PURPLE;
            if (type.equals(ElementalTypes.INSTANCE.getFIGHTING())) return ChatFormatting.DARK_RED;
            if (type.equals(ElementalTypes.INSTANCE.getFIRE())) return ChatFormatting.RED;
            if (type.equals(ElementalTypes.INSTANCE.getFLYING())) return ChatFormatting.GRAY;
            if (type.equals(ElementalTypes.INSTANCE.getGHOST())) return ChatFormatting.DARK_PURPLE;
            if (type.equals(ElementalTypes.INSTANCE.getGRASS())) return ChatFormatting.GREEN;
            if (type.equals(ElementalTypes.INSTANCE.getGROUND())) return ChatFormatting.DARK_RED;
            if (type.equals(ElementalTypes.INSTANCE.getICE())) return ChatFormatting.BLUE;
            if (type.equals(ElementalTypes.INSTANCE.getNORMAL())) return ChatFormatting.WHITE;
            if (type.equals(ElementalTypes.INSTANCE.getPOISON())) return ChatFormatting.DARK_PURPLE;
            if (type.equals(ElementalTypes.INSTANCE.getPSYCHIC())) return ChatFormatting.LIGHT_PURPLE;
            if (type.equals(ElementalTypes.INSTANCE.getROCK())) return ChatFormatting.DARK_GRAY;
            if (type.equals(ElementalTypes.INSTANCE.getSTEEL())) return ChatFormatting.GRAY;
            if (type.equals(ElementalTypes.INSTANCE.getWATER())) return ChatFormatting.BLUE;
        }
        throw new IllegalArgumentException("Unknown Pokémon types: " + types);
    }

    public static ChatFormatting getGlowColorForTeraType(TeraType teraType) {
        if (teraType.equals(TeraTypes.getBUG())) return ChatFormatting.DARK_GREEN;
        if (teraType.equals(TeraTypes.getDARK())) return ChatFormatting.BLACK;
        if (teraType.equals(TeraTypes.getDRAGON())) return ChatFormatting.DARK_BLUE;
        if (teraType.equals(TeraTypes.getELECTRIC())) return ChatFormatting.YELLOW;
        if (teraType.equals(TeraTypes.getFAIRY())) return ChatFormatting.LIGHT_PURPLE;
        if (teraType.equals(TeraTypes.getFIGHTING())) return ChatFormatting.DARK_RED;
        if (teraType.equals(TeraTypes.getFIRE())) return ChatFormatting.RED;
        if (teraType.equals(TeraTypes.getFLYING())) return ChatFormatting.GRAY;
        if (teraType.equals(TeraTypes.getGHOST())) return ChatFormatting.DARK_PURPLE;
        if (teraType.equals(TeraTypes.getGRASS())) return ChatFormatting.GREEN;
        if (teraType.equals(TeraTypes.getGROUND())) return ChatFormatting.DARK_RED;
        if (teraType.equals(TeraTypes.getICE())) return ChatFormatting.BLUE;
        if (teraType.equals(TeraTypes.getNORMAL())) return ChatFormatting.WHITE;
        if (teraType.equals(TeraTypes.getPOISON())) return ChatFormatting.DARK_PURPLE;
        if (teraType.equals(TeraTypes.getPSYCHIC())) return ChatFormatting.LIGHT_PURPLE;
        if (teraType.equals(TeraTypes.getROCK())) return ChatFormatting.DARK_GRAY;
        if (teraType.equals(TeraTypes.getSTEEL())) return ChatFormatting.GRAY;
        if (teraType.equals(TeraTypes.getWATER())) return ChatFormatting.BLUE;
        if(teraType.equals(TeraTypes.getSTELLAR())) return ChatFormatting.WHITE;

        throw new IllegalArgumentException("Unknown TeraType: " + teraType);
    }
}
