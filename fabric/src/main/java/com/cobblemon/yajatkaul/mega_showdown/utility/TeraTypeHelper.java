package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

public class TeraTypeHelper {
    private static final Map<Item, TeraType> ITEM_TO_TERA_MAP = new HashMap<>();

    public static void loadShardData() {
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
        return TeraMoves.NORMAL_TERA_SHARD;
    }

    public static Formatting getGlowColorForType(ItemStack heldItem) {
        if (heldItem.isOf(ZCrystals.ALORAICHIUM_Z)) return Formatting.YELLOW;
        if (heldItem.isOf(ZCrystals.BUGINIUM_Z)) return Formatting.DARK_GREEN;
        if (heldItem.isOf(ZCrystals.DARKINIUM_Z)) return Formatting.BLACK;
        if (heldItem.isOf(ZCrystals.DRAGONIUM_Z) || heldItem.isOf(ZCrystals.KOMMONIUM_Z)) return Formatting.DARK_BLUE;
        if (heldItem.isOf(ZCrystals.EEVIVIUM_Z) || heldItem.isOf(ZCrystals.SNORLIUM_Z) || heldItem.isOf(ZCrystals.NORMALIUM_Z))
            return Formatting.WHITE;
        if (heldItem.isOf(ZCrystals.ELECTRIUM_Z) || heldItem.isOf(ZCrystals.PIKANIUM_Z) || heldItem.isOf(ZCrystals.PIKASHUNIUM_Z))
            return Formatting.YELLOW;
        if (heldItem.isOf(ZCrystals.FAIRIUM_Z) || heldItem.isOf(ZCrystals.TAPUNIUM_Z)) return Formatting.LIGHT_PURPLE;
        if (heldItem.isOf(ZCrystals.FIGHTINIUM_Z)) return Formatting.DARK_RED;
        if (heldItem.isOf(ZCrystals.FIRIUM_Z) || heldItem.isOf(ZCrystals.INCINIUM_Z)) return Formatting.RED;
        if (heldItem.isOf(ZCrystals.FLYINIUM_Z)) return Formatting.GRAY;
        if (heldItem.isOf(ZCrystals.GHOSTIUM_Z) || heldItem.isOf(ZCrystals.MARSHADIUM_Z) || heldItem.isOf(ZCrystals.MIMIKIUM_Z))
            return Formatting.DARK_PURPLE;
        if (heldItem.isOf(ZCrystals.GRASSIUM_Z) || heldItem.isOf(ZCrystals.DECIDIUM_Z)) return Formatting.GREEN;
        if (heldItem.isOf(ZCrystals.GROUNDIUM_Z) || heldItem.isOf(ZCrystals.LYCANIUM_Z)) return Formatting.DARK_RED;
        if (heldItem.isOf(ZCrystals.ICIUM_Z)) return Formatting.BLUE;
        if (heldItem.isOf(ZCrystals.POISONIUM_Z)) return Formatting.DARK_PURPLE;
        if (heldItem.isOf(ZCrystals.PSYCHIUM_Z) || heldItem.isOf(ZCrystals.MEWNIUM_Z)) return Formatting.LIGHT_PURPLE;
        if (heldItem.isOf(ZCrystals.ROCKIUM_Z)) return Formatting.DARK_GRAY;
        if (heldItem.isOf(ZCrystals.STEELIUM_Z)) return Formatting.GRAY;
        if (heldItem.isOf(ZCrystals.WATERIUM_Z) || heldItem.isOf(ZCrystals.PRIMARIUM_Z)) return Formatting.BLUE;
        if (heldItem.isOf(ZCrystals.SOLGANIUM_Z) || heldItem.isOf(ZCrystals.LUNALIUM_Z) || heldItem.isOf(ZCrystals.ULTRANECROZIUM_Z))
            return Formatting.GOLD;

        return Formatting.WHITE;
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
        if (teraType.equals(TeraTypes.getSTELLAR())) return Formatting.WHITE;
        else {
            return Formatting.WHITE;
        }
    }
}
