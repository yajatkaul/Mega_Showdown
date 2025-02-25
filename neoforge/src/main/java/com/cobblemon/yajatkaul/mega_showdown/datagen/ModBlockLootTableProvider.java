package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
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
        add(MegaOres.ABOMASITE_ORE.get(),
                block -> createOreDrop(MegaOres.ABOMASITE_ORE.get(), MegaStones.ABOMASITE.get()));
        add(MegaOres.ABSOLITE_ORE.get(),
                block -> createOreDrop(MegaOres.ABSOLITE_ORE.get(), MegaStones.ABSOLITE.get()));
        add(MegaOres.AERODACTYLITE_ORE.get(),
                block -> createOreDrop(MegaOres.AERODACTYLITE_ORE.get(), MegaStones.AERODACTYLITE.get()));
        add(MegaOres.AGGRONITE_ORE.get(),
                block -> createOreDrop(MegaOres.AGGRONITE_ORE.get(), MegaStones.AGGRONITE.get()));
        add(MegaOres.ALAKAZITE_ORE.get(),
                block -> createOreDrop(MegaOres.ALAKAZITE_ORE.get(), MegaStones.ALAKAZITE.get()));
        add(MegaOres.ALTARIANITE_ORE.get(),
                block -> createOreDrop(MegaOres.ALTARIANITE_ORE.get(), MegaStones.ALTARIANITE.get()));
        add(MegaOres.AMPHAROSITE_ORE.get(),
                block -> createOreDrop(MegaOres.AMPHAROSITE_ORE.get(), MegaStones.AMPHAROSITE.get()));
        add(MegaOres.AUDINITE_ORE.get(),
                block -> createOreDrop(MegaOres.AUDINITE_ORE.get(), MegaStones.AUDINITE.get()));
        add(MegaOres.BANETTITE_ORE.get(),
                block -> createOreDrop(MegaOres.BANETTITE_ORE.get(), MegaStones.BANETTITE.get()));
        add(MegaOres.BEEDRILLITE_ORE.get(),
                block -> createOreDrop(MegaOres.BEEDRILLITE_ORE.get(), MegaStones.BEEDRILLITE.get()));
        add(MegaOres.BLASTOISINITE_ORE.get(),
                block -> createOreDrop(MegaOres.BLASTOISINITE_ORE.get(), MegaStones.BLASTOISINITE.get()));
        add(MegaOres.BLAZIKENITE_ORE.get(),
                block -> createOreDrop(MegaOres.BLAZIKENITE_ORE.get(), MegaStones.BLAZIKENITE.get()));
        add(MegaOres.CAMERUPTITE_ORE.get(),
                block -> createOreDrop(MegaOres.CAMERUPTITE_ORE.get(), MegaStones.CAMERUPTITE.get()));
        add(MegaOres.CHARIZARDITE_X_ORE.get(),
                block -> createOreDrop(MegaOres.CHARIZARDITE_X_ORE.get(), MegaStones.CHARIZARDITE_X.get()));
        add(MegaOres.CHARIZARDITE_Y_ORE.get(),
                block -> createOreDrop(MegaOres.CHARIZARDITE_Y_ORE.get(), MegaStones.CHARIZARDITE_Y.get()));
        add(MegaOres.DIANCITE_ORE.get(),
                block -> createOreDrop(MegaOres.DIANCITE_ORE.get(), MegaStones.DIANCITE.get()));
        add(MegaOres.GALLADITE_ORE.get(),
                block -> createOreDrop(MegaOres.GALLADITE_ORE.get(), MegaStones.GALLADITE.get()));
        add(MegaOres.GARCHOMPITE_ORE.get(),
                block -> createOreDrop(MegaOres.GARCHOMPITE_ORE.get(), MegaStones.GARCHOMPITE.get()));
        add(MegaOres.GARDEVOIRITE_ORE.get(),
                block -> createOreDrop(MegaOres.GARDEVOIRITE_ORE.get(), MegaStones.GARDEVOIRITE.get()));
        add(MegaOres.GENGARITE_ORE.get(),
                block -> createOreDrop(MegaOres.GENGARITE_ORE.get(), MegaStones.GENGARITE.get()));
        add(MegaOres.GLALITITE_ORE.get(),
                block -> createOreDrop(MegaOres.GLALITITE_ORE.get(), MegaStones.GLALITITE.get()));
        add(MegaOres.GYARADOSITE_ORE.get(),
                block -> createOreDrop(MegaOres.GYARADOSITE_ORE.get(), MegaStones.GYARADOSITE.get()));
        add(MegaOres.HERACRONITE_ORE.get(),
                block -> createOreDrop(MegaOres.HERACRONITE_ORE.get(), MegaStones.HERACRONITE.get()));
        add(MegaOres.HOUNDOOMINITE_ORE.get(),
                block -> createOreDrop(MegaOres.HOUNDOOMINITE_ORE.get(), MegaStones.HOUNDOOMINITE.get()));
        add(MegaOres.KANGASKHANITE_ORE.get(),
                block -> createOreDrop(MegaOres.KANGASKHANITE_ORE.get(), MegaStones.KANGASKHANITE.get()));
        add(MegaOres.LATIASITE_ORE.get(),
                block -> createOreDrop(MegaOres.LATIASITE_ORE.get(), MegaStones.LATIASITE.get()));
        add(MegaOres.LATIOSITE_ORE.get(),
                block -> createOreDrop(MegaOres.LATIOSITE_ORE.get(), MegaStones.LATIOSITE.get()));
        add(MegaOres.LOPUNNITE_ORE.get(),
                block -> createOreDrop(MegaOres.LOPUNNITE_ORE.get(), MegaStones.LOPUNNITE.get()));
        add(MegaOres.LUCARIONITE_ORE.get(),
                block -> createOreDrop(MegaOres.LUCARIONITE_ORE.get(), MegaStones.LUCARIONITE.get()));
        add(MegaOres.MANECTITE_ORE.get(),
                block -> createOreDrop(MegaOres.MANECTITE_ORE.get(), MegaStones.MANECTITE.get()));
        add(MegaOres.MAWILITE_ORE.get(),
                block -> createOreDrop(MegaOres.MAWILITE_ORE.get(), MegaStones.MAWILITE.get()));
        add(MegaOres.MEDICHAMITE_ORE.get(),
                block -> createOreDrop(MegaOres.MEDICHAMITE_ORE.get(), MegaStones.MEDICHAMITE.get()));
        add(MegaOres.METAGROSSITE_ORE.get(),
                block -> createOreDrop(MegaOres.METAGROSSITE_ORE.get(), MegaStones.METAGROSSITE.get()));
        add(MegaOres.MEWTWONITE_X_ORE.get(),
                block -> createOreDrop(MegaOres.MEWTWONITE_X_ORE.get(), MegaStones.MEWTWONITE_X.get()));
        add(MegaOres.MEWTWONITE_Y_ORE.get(),
                block -> createOreDrop(MegaOres.MEWTWONITE_Y_ORE.get(), MegaStones.MEWTWONITE_Y.get()));
        add(MegaOres.PIDGEOTITE_ORE.get(),
                block -> createOreDrop(MegaOres.PIDGEOTITE_ORE.get(), MegaStones.PIDGEOTITE.get()));
        add(MegaOres.PINSIRITE_ORE.get(),
                block -> createOreDrop(MegaOres.PINSIRITE_ORE.get(), MegaStones.PINSIRITE.get()));
        add(MegaOres.SABLENITE_ORE.get(),
                block -> createOreDrop(MegaOres.SABLENITE_ORE.get(), MegaStones.SABLENITE.get()));
        add(MegaOres.SALAMENCITE_ORE.get(),
                block -> createOreDrop(MegaOres.SALAMENCITE_ORE.get(), MegaStones.SALAMENCITE.get()));
        add(MegaOres.SCEPTILITE_ORE.get(),
                block -> createOreDrop(MegaOres.SCEPTILITE_ORE.get(), MegaStones.SCEPTILITE.get()));
        add(MegaOres.SCIZORITE_ORE.get(),
                block -> createOreDrop(MegaOres.SCIZORITE_ORE.get(), MegaStones.SCIZORITE.get()));
        add(MegaOres.SHARPEDONITE_ORE.get(),
                block -> createOreDrop(MegaOres.SHARPEDONITE_ORE.get(), MegaStones.SHARPEDONITE.get()));
        add(MegaOres.SLOWBRONITE_ORE.get(),
                block -> createOreDrop(MegaOres.SLOWBRONITE_ORE.get(), MegaStones.SLOWBRONITE.get()));
        add(MegaOres.STEELIXITE_ORE.get(),
                block -> createOreDrop(MegaOres.STEELIXITE_ORE.get(), MegaStones.STEELIXITE.get()));
        add(MegaOres.SWAMPERTITE_ORE.get(),
                block -> createOreDrop(MegaOres.SWAMPERTITE_ORE.get(), MegaStones.SWAMPERTITE.get()));
        add(MegaOres.TYRANITARITE_ORE.get(),
                block -> createOreDrop(MegaOres.TYRANITARITE_ORE.get(), MegaStones.TYRANITARITE.get()));
        add(MegaOres.VENUSAURITE_ORE.get(),
                block -> createOreDrop(MegaOres.VENUSAURITE_ORE.get(), MegaStones.VENUSAURITE.get()));

        add(MegaOres.KEYSTONE_ORE.get(), block ->
                createSingleItemTableWithSilkTouch(MegaOres.KEYSTONE_ORE.get(), MegaStones.KEYSTONE.get()));

        add(MegaOres.MEGA_STONE_CRYSTAL.get(), block ->
                createSingleItemTableWithSilkTouch(MegaOres.MEGA_STONE_CRYSTAL.get(), MegaStones.MEGA_STONE.get()));

        dropSelf(ModBlocks.MEGA_METEOROID_BLOCK.get());
        dropSelf(ModBlocks.MEGA_EVO_BLOCK.get());
        dropSelf(ModBlocks.KEYSTONE_BLOCK.get());
        dropSelf(ModBlocks.CHISELED_MEGA_EVO_BRICK.get());
        dropSelf(ModBlocks.CHISELED_MEGA_EVO_BLOCK.get());
        dropSelf(ModBlocks.MEGA_EVO_BRICK.get());

        dropSelf(ModBlocks.POLISHED_MEGA_EVO_BLOCK.get());

        add(MegaOres.MEGA_METEORID_DAWN_ORE.get(),
                block -> createOreDrop(MegaOres.MEGA_METEORID_DAWN_ORE.get(), CobblemonItems.DAWN_STONE));

        add(MegaOres.MEGA_METEORID_DUSK_ORE.get(),
                block -> createOreDrop(MegaOres.MEGA_METEORID_DUSK_ORE.get(), CobblemonItems.DUSK_STONE));

        add(MegaOres.MEGA_METEORID_FIRE_ORE.get(),
                block -> createOreDrop(MegaOres.MEGA_METEORID_FIRE_ORE.get(), CobblemonItems.FIRE_STONE));

        add(MegaOres.MEGA_METEORID_ICE_ORE.get(),
                block -> createOreDrop(MegaOres.MEGA_METEORID_ICE_ORE.get(), CobblemonItems.ICE_STONE));

        add(MegaOres.MEGA_METEORID_LEAF_ORE.get(),
                block -> createOreDrop(MegaOres.MEGA_METEORID_LEAF_ORE.get(), CobblemonItems.LEAF_STONE));

        add(MegaOres.MEGA_METEORID_MOON_ORE.get(),
                block -> createOreDrop(MegaOres.MEGA_METEORID_MOON_ORE.get(), CobblemonItems.MOON_STONE));

        add(MegaOres.MEGA_METEORID_SHINY_ORE.get(),
                block -> createOreDrop(MegaOres.MEGA_METEORID_SHINY_ORE.get(), CobblemonItems.SHINY_STONE));

        add(MegaOres.MEGA_METEORID_SUN_ORE.get(),
                block -> createOreDrop(MegaOres.MEGA_METEORID_SUN_ORE.get(), CobblemonItems.SUN_STONE));

        add(MegaOres.MEGA_METEORID_THUNDER_ORE.get(),
                block -> createOreDrop(MegaOres.MEGA_METEORID_THUNDER_ORE.get(), CobblemonItems.THUNDER_STONE));

        add(MegaOres.MEGA_METEORID_WATER_ORE.get(),
                block -> createOreDrop(MegaOres.MEGA_METEORID_WATER_ORE.get(), CobblemonItems.WATER_STONE));

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

}
