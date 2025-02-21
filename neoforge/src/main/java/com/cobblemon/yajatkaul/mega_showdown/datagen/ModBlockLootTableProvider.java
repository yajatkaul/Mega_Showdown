package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(ModBlocks.ABOMASITE_ORE.get(),
                block -> createOreDrop(ModBlocks.ABOMASITE_ORE.get(), ModItems.ABOMASITE.get()));
        add(ModBlocks.ABSOLITE_ORE.get(),
                block -> createOreDrop(ModBlocks.ABSOLITE_ORE.get(), ModItems.ABSOLITE.get()));
        add(ModBlocks.AERODACTYLITE_ORE.get(),
                block -> createOreDrop(ModBlocks.AERODACTYLITE_ORE.get(), ModItems.AERODACTYLITE.get()));
        add(ModBlocks.AGGRONITE_ORE.get(),
                block -> createOreDrop(ModBlocks.AGGRONITE_ORE.get(), ModItems.AGGRONITE.get()));
        add(ModBlocks.ALAKAZITE_ORE.get(),
                block -> createOreDrop(ModBlocks.ALAKAZITE_ORE.get(), ModItems.ALAKAZITE.get()));
        add(ModBlocks.ALTARIANITE_ORE.get(),
                block -> createOreDrop(ModBlocks.ALTARIANITE_ORE.get(), ModItems.ALTARIANITE.get()));
        add(ModBlocks.AMPHAROSITE_ORE.get(),
                block -> createOreDrop(ModBlocks.AMPHAROSITE_ORE.get(), ModItems.AMPHAROSITE.get()));
        add(ModBlocks.AUDINITE_ORE.get(),
                block -> createOreDrop(ModBlocks.AUDINITE_ORE.get(), ModItems.AUDINITE.get()));
        add(ModBlocks.BANETTITE_ORE.get(),
                block -> createOreDrop(ModBlocks.BANETTITE_ORE.get(), ModItems.BANETTITE.get()));
        add(ModBlocks.BEEDRILLITE_ORE.get(),
                block -> createOreDrop(ModBlocks.BEEDRILLITE_ORE.get(), ModItems.BEEDRILLITE.get()));
        add(ModBlocks.BLASTOISINITE_ORE.get(),
                block -> createOreDrop(ModBlocks.BLASTOISINITE_ORE.get(), ModItems.BLASTOISINITE.get()));
        add(ModBlocks.BLAZIKENITE_ORE.get(),
                block -> createOreDrop(ModBlocks.BLAZIKENITE_ORE.get(), ModItems.BLAZIKENITE.get()));
        add(ModBlocks.CAMERUPTITE_ORE.get(),
                block -> createOreDrop(ModBlocks.CAMERUPTITE_ORE.get(), ModItems.CAMERUPTITE.get()));
        add(ModBlocks.CHARIZARDITE_X_ORE.get(),
                block -> createOreDrop(ModBlocks.CHARIZARDITE_X_ORE.get(), ModItems.CHARIZARDITE_X.get()));
        add(ModBlocks.CHARIZARDITE_Y_ORE.get(),
                block -> createOreDrop(ModBlocks.CHARIZARDITE_Y_ORE.get(), ModItems.CHARIZARDITE_Y.get()));
        add(ModBlocks.DIANCITE_ORE.get(),
                block -> createOreDrop(ModBlocks.DIANCITE_ORE.get(), ModItems.DIANCITE.get()));
        add(ModBlocks.GALLADITE_ORE.get(),
                block -> createOreDrop(ModBlocks.GALLADITE_ORE.get(), ModItems.GALLADITE.get()));
        add(ModBlocks.GARCHOMPITE_ORE.get(),
                block -> createOreDrop(ModBlocks.GARCHOMPITE_ORE.get(), ModItems.GARCHOMPITE.get()));
        add(ModBlocks.GARDEVOIRITE_ORE.get(),
                block -> createOreDrop(ModBlocks.GARDEVOIRITE_ORE.get(), ModItems.GARDEVOIRITE.get()));
        add(ModBlocks.GENGARITE_ORE.get(),
                block -> createOreDrop(ModBlocks.GENGARITE_ORE.get(), ModItems.GENGARITE.get()));
        add(ModBlocks.GLALITITE_ORE.get(),
                block -> createOreDrop(ModBlocks.GLALITITE_ORE.get(), ModItems.GLALITITE.get()));
        add(ModBlocks.GYARADOSITE_ORE.get(),
                block -> createOreDrop(ModBlocks.GYARADOSITE_ORE.get(), ModItems.GYARADOSITE.get()));
        add(ModBlocks.HERACRONITE_ORE.get(),
                block -> createOreDrop(ModBlocks.HERACRONITE_ORE.get(), ModItems.HERACRONITE.get()));
        add(ModBlocks.HOUNDOOMINITE_ORE.get(),
                block -> createOreDrop(ModBlocks.HOUNDOOMINITE_ORE.get(), ModItems.HOUNDOOMINITE.get()));
        add(ModBlocks.KANGASKHANITE_ORE.get(),
                block -> createOreDrop(ModBlocks.KANGASKHANITE_ORE.get(), ModItems.KANGASKHANITE.get()));
        add(ModBlocks.LATIASITE_ORE.get(),
                block -> createOreDrop(ModBlocks.LATIASITE_ORE.get(), ModItems.LATIASITE.get()));
        add(ModBlocks.LATIOSITE_ORE.get(),
                block -> createOreDrop(ModBlocks.LATIOSITE_ORE.get(), ModItems.LATIOSITE.get()));
        add(ModBlocks.LOPUNNITE_ORE.get(),
                block -> createOreDrop(ModBlocks.LOPUNNITE_ORE.get(), ModItems.LOPUNNITE.get()));
        add(ModBlocks.LUCARIONITE_ORE.get(),
                block -> createOreDrop(ModBlocks.LUCARIONITE_ORE.get(), ModItems.LUCARIONITE.get()));
        add(ModBlocks.MANECTITE_ORE.get(),
                block -> createOreDrop(ModBlocks.MANECTITE_ORE.get(), ModItems.MANECTITE.get()));
        add(ModBlocks.MAWILITE_ORE.get(),
                block -> createOreDrop(ModBlocks.MAWILITE_ORE.get(), ModItems.MAWILITE.get()));
        add(ModBlocks.MEDICHAMITE_ORE.get(),
                block -> createOreDrop(ModBlocks.MEDICHAMITE_ORE.get(), ModItems.MEDICHAMITE.get()));
        add(ModBlocks.METAGROSSITE_ORE.get(),
                block -> createOreDrop(ModBlocks.METAGROSSITE_ORE.get(), ModItems.METAGROSSITE.get()));
        add(ModBlocks.MEWTWONITE_X_ORE.get(),
                block -> createOreDrop(ModBlocks.MEWTWONITE_X_ORE.get(), ModItems.MEWTWONITE_X.get()));
        add(ModBlocks.MEWTWONITE_Y_ORE.get(),
                block -> createOreDrop(ModBlocks.MEWTWONITE_Y_ORE.get(), ModItems.MEWTWONITE_Y.get()));
        add(ModBlocks.PIDGEOTITE_ORE.get(),
                block -> createOreDrop(ModBlocks.PIDGEOTITE_ORE.get(), ModItems.PIDGEOTITE.get()));
        add(ModBlocks.PINSIRITE_ORE.get(),
                block -> createOreDrop(ModBlocks.PINSIRITE_ORE.get(), ModItems.PINSIRITE.get()));
        add(ModBlocks.SABLENITE_ORE.get(),
                block -> createOreDrop(ModBlocks.SABLENITE_ORE.get(), ModItems.SABLENITE.get()));
        add(ModBlocks.SALAMENCITE_ORE.get(),
                block -> createOreDrop(ModBlocks.SALAMENCITE_ORE.get(), ModItems.SALAMENCITE.get()));
        add(ModBlocks.SCEPTILITE_ORE.get(),
                block -> createOreDrop(ModBlocks.SCEPTILITE_ORE.get(), ModItems.SCEPTILITE.get()));
        add(ModBlocks.SCIZORITE_ORE.get(),
                block -> createOreDrop(ModBlocks.SCIZORITE_ORE.get(), ModItems.SCIZORITE.get()));
        add(ModBlocks.SHARPEDONITE_ORE.get(),
                block -> createOreDrop(ModBlocks.SHARPEDONITE_ORE.get(), ModItems.SHARPEDONITE.get()));
        add(ModBlocks.SLOWBRONITE_ORE.get(),
                block -> createOreDrop(ModBlocks.SLOWBRONITE_ORE.get(), ModItems.SLOWBRONITE.get()));
        add(ModBlocks.STEELIXITE_ORE.get(),
                block -> createOreDrop(ModBlocks.STEELIXITE_ORE.get(), ModItems.STEELIXITE.get()));
        add(ModBlocks.SWAMPERTITE_ORE.get(),
                block -> createOreDrop(ModBlocks.SWAMPERTITE_ORE.get(), ModItems.SWAMPERTITE.get()));
        add(ModBlocks.TYRANITARITE_ORE.get(),
                block -> createOreDrop(ModBlocks.TYRANITARITE_ORE.get(), ModItems.TYRANITARITE.get()));
        add(ModBlocks.VENUSAURITE_ORE.get(),
                block -> createOreDrop(ModBlocks.VENUSAURITE_ORE.get(), ModItems.VENUSAURITE.get()));

        add(ModBlocks.KEYSTONE_ORE.get(), block ->
                createSingleItemTableWithSilkTouch(ModBlocks.KEYSTONE_ORE.get(), ModItems.KEYSTONE.get()));

        add(ModBlocks.MEGA_STONE_CRYSTAL.get(), block ->
                createSingleItemTableWithSilkTouch(ModBlocks.MEGA_STONE_CRYSTAL.get(), ModItems.MEGA_STONE.get()));

        dropSelf(ModBlocks.MEGA_METEOROID_BLOCK.get());
        dropSelf(ModBlocks.MEGA_EVO_BLOCK.get());
        dropSelf(ModBlocks.KEYSTONE_BLOCK.get());
        dropSelf(ModBlocks.CHISELED_MEGA_EVO_BRICK.get());
        dropSelf(ModBlocks.CHISELED_MEGA_EVO_BLOCK.get());
        dropSelf(ModBlocks.MEGA_EVO_BRICK.get());

        dropSelf(ModBlocks.POLISHED_MEGA_EVO_BLOCK.get());

        add(ModBlocks.MEGA_METEORID_DAWN_ORE.get(),
                block -> createOreDrop(ModBlocks.MEGA_METEORID_DAWN_ORE.get(), CobblemonItems.DAWN_STONE));

        add(ModBlocks.MEGA_METEORID_DUSK_ORE.get(),
                block -> createOreDrop(ModBlocks.MEGA_METEORID_DUSK_ORE.get(), CobblemonItems.DUSK_STONE));

        add(ModBlocks.MEGA_METEORID_FIRE_ORE.get(),
                block -> createOreDrop(ModBlocks.MEGA_METEORID_FIRE_ORE.get(), CobblemonItems.FIRE_STONE));

        add(ModBlocks.MEGA_METEORID_ICE_ORE.get(),
                block -> createOreDrop(ModBlocks.MEGA_METEORID_ICE_ORE.get(), CobblemonItems.ICE_STONE));

        add(ModBlocks.MEGA_METEORID_LEAF_ORE.get(),
                block -> createOreDrop(ModBlocks.MEGA_METEORID_LEAF_ORE.get(), CobblemonItems.LEAF_STONE));

        add(ModBlocks.MEGA_METEORID_MOON_ORE.get(),
                block -> createOreDrop(ModBlocks.MEGA_METEORID_MOON_ORE.get(), CobblemonItems.MOON_STONE));

        add(ModBlocks.MEGA_METEORID_SHINY_ORE.get(),
                block -> createOreDrop(ModBlocks.MEGA_METEORID_SHINY_ORE.get(), CobblemonItems.SHINY_STONE));

        add(ModBlocks.MEGA_METEORID_SUN_ORE.get(),
                block -> createOreDrop(ModBlocks.MEGA_METEORID_SUN_ORE.get(), CobblemonItems.SUN_STONE));

        add(ModBlocks.MEGA_METEORID_THUNDER_ORE.get(),
                block -> createOreDrop(ModBlocks.MEGA_METEORID_THUNDER_ORE.get(), CobblemonItems.THUNDER_STONE));

        add(ModBlocks.MEGA_METEORID_WATER_ORE.get(),
                block -> createOreDrop(ModBlocks.MEGA_METEORID_WATER_ORE.get(), CobblemonItems.WATER_STONE));

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

}
