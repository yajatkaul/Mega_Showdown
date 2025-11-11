package com.github.yajatkaul.mega_showdown.utils;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;

public class TeraHelper {
    public static Item getTeraShardForType(Iterable<ElementalType> types) {
        RegistrySupplier<Item> shard = MegaShowdownItems.NORMAL_TERA_SHARD;
        for (ElementalType type : types) {
            if (type.equals(ElementalTypes.INSTANCE.getBUG())) shard = MegaShowdownItems.BUG_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getDARK())) shard = MegaShowdownItems.DARK_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getDRAGON())) shard = MegaShowdownItems.DRAGON_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getELECTRIC())) shard = MegaShowdownItems.ELECTRIC_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFAIRY())) shard = MegaShowdownItems.FAIRY_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFIGHTING())) shard = MegaShowdownItems.FIGHTING_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFIRE())) shard = MegaShowdownItems.FIRE_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getFLYING())) shard = MegaShowdownItems.FLYING_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGHOST())) shard = MegaShowdownItems.GHOST_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGRASS())) shard = MegaShowdownItems.GRASS_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getGROUND())) shard = MegaShowdownItems.GROUND_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getICE())) shard = MegaShowdownItems.ICE_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getNORMAL())) shard = MegaShowdownItems.NORMAL_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getPOISON())) shard = MegaShowdownItems.POISON_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getPSYCHIC())) shard = MegaShowdownItems.PSYCHIC_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getROCK())) shard = MegaShowdownItems.ROCK_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getSTEEL())) shard = MegaShowdownItems.STEEL_TERA_SHARD;
            if (type.equals(ElementalTypes.INSTANCE.getWATER())) shard = MegaShowdownItems.WATER_TERA_SHARD;
        }
        return shard.get();
    }

    public static TeraType getTeraFromElement(ElementalType type) {
        if (type.equals(ElementalTypes.INSTANCE.getBUG())) return TeraTypes.getBUG();
        if (type.equals(ElementalTypes.INSTANCE.getDARK())) return TeraTypes.getDARK();
        if (type.equals(ElementalTypes.INSTANCE.getDRAGON())) return TeraTypes.getDRAGON();
        if (type.equals(ElementalTypes.INSTANCE.getELECTRIC())) return TeraTypes.getELECTRIC();
        if (type.equals(ElementalTypes.INSTANCE.getFAIRY())) return TeraTypes.getFAIRY();
        if (type.equals(ElementalTypes.INSTANCE.getFIGHTING())) return TeraTypes.getFIGHTING();
        if (type.equals(ElementalTypes.INSTANCE.getFIRE())) return TeraTypes.getFIRE();
        if (type.equals(ElementalTypes.INSTANCE.getFLYING())) return TeraTypes.getFLYING();
        if (type.equals(ElementalTypes.INSTANCE.getGHOST())) return TeraTypes.getGHOST();
        if (type.equals(ElementalTypes.INSTANCE.getGRASS())) return TeraTypes.getGRASS();
        if (type.equals(ElementalTypes.INSTANCE.getGROUND())) return TeraTypes.getGROUND();
        if (type.equals(ElementalTypes.INSTANCE.getICE())) return TeraTypes.getICE();
        if (type.equals(ElementalTypes.INSTANCE.getNORMAL())) return TeraTypes.getNORMAL();
        if (type.equals(ElementalTypes.INSTANCE.getPOISON())) return TeraTypes.getPOISON();
        if (type.equals(ElementalTypes.INSTANCE.getPSYCHIC())) return TeraTypes.getPSYCHIC();
        if (type.equals(ElementalTypes.INSTANCE.getROCK())) return TeraTypes.getROCK();
        if (type.equals(ElementalTypes.INSTANCE.getSTEEL())) return TeraTypes.getSTEEL();
        if (type.equals(ElementalTypes.INSTANCE.getWATER())) return TeraTypes.getWATER();
        return TeraTypes.getNORMAL();
    }
}
