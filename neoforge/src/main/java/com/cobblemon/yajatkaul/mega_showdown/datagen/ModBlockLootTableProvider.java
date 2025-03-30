package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
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
                block -> createOreDrop(block, MegaStones.ABOMASITE.get()));
        add(MegaOres.ABSOLITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.ABSOLITE.get()));
        add(MegaOres.AERODACTYLITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.AERODACTYLITE.get()));
        add(MegaOres.AGGRONITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.AGGRONITE.get()));
        add(MegaOres.ALAKAZITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.ALAKAZITE.get()));
        add(MegaOres.ALTARIANITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.ALTARIANITE.get()));
        add(MegaOres.AMPHAROSITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.AMPHAROSITE.get()));
        add(MegaOres.AUDINITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.AUDINITE.get()));
        add(MegaOres.BANETTITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.BANETTITE.get()));
        add(MegaOres.BEEDRILLITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.BEEDRILLITE.get()));
        add(MegaOres.BLASTOISINITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.BLASTOISINITE.get()));
        add(MegaOres.BLAZIKENITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.BLAZIKENITE.get()));
        add(MegaOres.CAMERUPTITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.CAMERUPTITE.get()));
        add(MegaOres.CHARIZARDITE_X_ORE.get(),
                block -> createOreDrop(block, MegaStones.CHARIZARDITE_X.get()));
        add(MegaOres.CHARIZARDITE_Y_ORE.get(),
                block -> createOreDrop(block, MegaStones.CHARIZARDITE_Y.get()));
        add(MegaOres.DIANCITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.DIANCITE.get()));
        add(MegaOres.GALLADITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.GALLADITE.get()));
        add(MegaOres.GARCHOMPITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.GARCHOMPITE.get()));
        add(MegaOres.GARDEVOIRITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.GARDEVOIRITE.get()));
        add(MegaOres.GENGARITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.GENGARITE.get()));
        add(MegaOres.GLALITITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.GLALITITE.get()));
        add(MegaOres.GYARADOSITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.GYARADOSITE.get()));
        add(MegaOres.HERACRONITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.HERACRONITE.get()));
        add(MegaOres.HOUNDOOMINITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.HOUNDOOMINITE.get()));
        add(MegaOres.KANGASKHANITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.KANGASKHANITE.get()));
        add(MegaOres.LATIASITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.LATIASITE.get()));
        add(MegaOres.LATIOSITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.LATIOSITE.get()));
        add(MegaOres.LOPUNNITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.LOPUNNITE.get()));
        add(MegaOres.LUCARIONITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.LUCARIONITE.get()));
        add(MegaOres.MANECTITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.MANECTITE.get()));
        add(MegaOres.MAWILITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.MAWILITE.get()));
        add(MegaOres.MEDICHAMITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.MEDICHAMITE.get()));
        add(MegaOres.METAGROSSITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.METAGROSSITE.get()));
        add(MegaOres.MEWTWONITE_X_ORE.get(),
                block -> createOreDrop(block, MegaStones.MEWTWONITE_X.get()));
        add(MegaOres.MEWTWONITE_Y_ORE.get(),
                block -> createOreDrop(block, MegaStones.MEWTWONITE_Y.get()));
        add(MegaOres.PIDGEOTITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.PIDGEOTITE.get()));
        add(MegaOres.PINSIRITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.PINSIRITE.get()));
        add(MegaOres.SABLENITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.SABLENITE.get()));
        add(MegaOres.SALAMENCITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.SALAMENCITE.get()));
        add(MegaOres.SCEPTILITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.SCEPTILITE.get()));
        add(MegaOres.SCIZORITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.SCIZORITE.get()));
        add(MegaOres.SHARPEDONITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.SHARPEDONITE.get()));
        add(MegaOres.SLOWBRONITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.SLOWBRONITE.get()));
        add(MegaOres.STEELIXITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.STEELIXITE.get()));
        add(MegaOres.SWAMPERTITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.SWAMPERTITE.get()));
        add(MegaOres.TYRANITARITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.TYRANITARITE.get()));
        add(MegaOres.VENUSAURITE_ORE.get(),
                block -> createOreDrop(block, MegaStones.VENUSAURITE.get()));

        add(MegaOres.KEYSTONE_ORE.get(), block ->
                createSingleItemTableWithSilkTouch(block, MegaStones.KEYSTONE.get()));

        add(MegaOres.MEGA_STONE_CRYSTAL.get(), block ->
                createSingleItemTableWithSilkTouch(block, MegaStones.MEGA_STONE.get()));

        dropSelf(ModBlocks.MEGA_METEOROID_BLOCK.get());
        dropSelf(ModBlocks.MEGA_EVO_BLOCK.get());
        dropSelf(ModBlocks.KEYSTONE_BLOCK.get());
        dropSelf(ModBlocks.CHISELED_MEGA_EVO_BRICK.get());
        dropSelf(ModBlocks.CHISELED_MEGA_EVO_BLOCK.get());
        dropSelf(ModBlocks.MEGA_EVO_BRICK.get());

        dropSelf(ModBlocks.POLISHED_MEGA_EVO_BLOCK.get());

        add(MegaOres.MEGA_METEORID_DAWN_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.DAWN_STONE));

        add(MegaOres.MEGA_METEORID_DUSK_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.DUSK_STONE));

        add(MegaOres.MEGA_METEORID_FIRE_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.FIRE_STONE));

        add(MegaOres.MEGA_METEORID_ICE_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.ICE_STONE));

        add(MegaOres.MEGA_METEORID_LEAF_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.LEAF_STONE));

        add(MegaOres.MEGA_METEORID_MOON_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.MOON_STONE));

        add(MegaOres.MEGA_METEORID_SHINY_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.SHINY_STONE));

        add(MegaOres.MEGA_METEORID_SUN_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.SUN_STONE));

        add(MegaOres.MEGA_METEORID_THUNDER_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.THUNDER_STONE));

        add(MegaOres.MEGA_METEORID_WATER_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.WATER_STONE));

        dropSelf(ModBlocks.DEOXYS_METEORITE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

}
