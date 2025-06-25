package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;

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
            if (type.equals(ElementalTypes.INSTANCE.getBUG())) shard =  TeraMoves.BUG_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getDARK())) shard =  TeraMoves.DARK_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getDRAGON())) shard =  TeraMoves.DRAGON_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getELECTRIC())) shard =  TeraMoves.ELECTRIC_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFAIRY())) shard =  TeraMoves.FAIRY_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFIGHTING())) shard =  TeraMoves.FIGHTING_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFIRE())) shard =  TeraMoves.FIRE_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFLYING())) shard =  TeraMoves.FLYING_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGHOST())) shard =  TeraMoves.GHOST_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGRASS())) shard =  TeraMoves.GRASS_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGROUND())) shard =  TeraMoves.GROUND_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getICE())) shard =  TeraMoves.ICE_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getNORMAL())) shard =  TeraMoves.NORMAL_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getPOISON())) shard =  TeraMoves.POISON_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getPSYCHIC())) shard =  TeraMoves.PSYCHIC_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getROCK())) shard =  TeraMoves.ROCK_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getSTEEL())) shard =  TeraMoves.STEEL_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getWATER())) shard =  TeraMoves.WATER_TERA_SHARD;
        }
        return shard.get();
    }

    public static ChatFormatting getGlowColorForType(ItemStack heldItem) {
        if (heldItem.is(ZCrystals.ALORAICHIUM_Z)) return ChatFormatting.YELLOW;
        if (heldItem.is(ZCrystals.BUGINIUM_Z)) return ChatFormatting.DARK_GREEN;
        if (heldItem.is(ZCrystals.DARKINIUM_Z)) return ChatFormatting.BLACK;
        if (heldItem.is(ZCrystals.DRAGONIUM_Z) || heldItem.is(ZCrystals.KOMMONIUM_Z)) return ChatFormatting.DARK_BLUE;
        if (heldItem.is(ZCrystals.EEVIUM_Z) || heldItem.is(ZCrystals.SNORLIUM_Z) || heldItem.is(ZCrystals.NORMALIUM_Z))
            return ChatFormatting.WHITE;
        if (heldItem.is(ZCrystals.ELECTRIUM_Z) || heldItem.is(ZCrystals.PIKANIUM_Z) || heldItem.is(ZCrystals.PIKASHUNIUM_Z))
            return ChatFormatting.YELLOW;
        if (heldItem.is(ZCrystals.FAIRIUM_Z) || heldItem.is(ZCrystals.TAPUNIUM_Z)) return ChatFormatting.LIGHT_PURPLE;
        if (heldItem.is(ZCrystals.FIGHTINIUM_Z)) return ChatFormatting.DARK_RED;
        if (heldItem.is(ZCrystals.FIRIUM_Z) || heldItem.is(ZCrystals.INCINIUM_Z)) return ChatFormatting.RED;
        if (heldItem.is(ZCrystals.FLYINIUM_Z)) return ChatFormatting.GRAY;
        if (heldItem.is(ZCrystals.GHOSTIUM_Z) || heldItem.is(ZCrystals.MARSHADIUM_Z) || heldItem.is(ZCrystals.MIMIKIUM_Z))
            return ChatFormatting.DARK_PURPLE;
        if (heldItem.is(ZCrystals.GRASSIUM_Z) || heldItem.is(ZCrystals.DECIDIUM_Z)) return ChatFormatting.GREEN;
        if (heldItem.is(ZCrystals.GROUNDIUM_Z) || heldItem.is(ZCrystals.LYCANIUM_Z)) return ChatFormatting.DARK_RED;
        if (heldItem.is(ZCrystals.ICIUM_Z)) return ChatFormatting.BLUE;
        if (heldItem.is(ZCrystals.POISONIUM_Z)) return ChatFormatting.DARK_PURPLE;
        if (heldItem.is(ZCrystals.PSYCHIUM_Z) || heldItem.is(ZCrystals.MEWNIUM_Z)) return ChatFormatting.LIGHT_PURPLE;
        if (heldItem.is(ZCrystals.ROCKIUM_Z)) return ChatFormatting.DARK_GRAY;
        if (heldItem.is(ZCrystals.STEELIUM_Z)) return ChatFormatting.GRAY;
        if (heldItem.is(ZCrystals.WATERIUM_Z) || heldItem.is(ZCrystals.PRIMARIUM_Z)) return ChatFormatting.BLUE;
        if (heldItem.is(ZCrystals.SOLGANIUM_Z) || heldItem.is(ZCrystals.LUNALIUM_Z) || heldItem.is(ZCrystals.ULTRANECROZIUM_Z))
            return ChatFormatting.GOLD;

        return ChatFormatting.WHITE;
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
        if (teraType.equals(TeraTypes.getSTELLAR())) return ChatFormatting.WHITE;

        return ChatFormatting.WHITE;
    }
}
