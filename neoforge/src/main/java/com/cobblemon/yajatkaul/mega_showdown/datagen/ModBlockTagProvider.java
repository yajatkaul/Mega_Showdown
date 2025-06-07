package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider
            , @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MegaShowdown.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        ModBlocks.WISHING_STAR_CRYSTAL.get(),

                        MegaOres.KEYSTONE_ORE.get(),

                        MegaOres.MEGA_STONE_CRYSTAL.get(),

                        ModBlocks.MEGA_METEOROID_BLOCK.get(),
                        ModBlocks.MEGA_EVO_BLOCK.get(),
                        ModBlocks.KEYSTONE_BLOCK.get(),

                        MegaOres.MEGA_METEORID_DAWN_ORE.get(),
                        MegaOres.MEGA_METEORID_DUSK_ORE.get(),
                        MegaOres.MEGA_METEORID_FIRE_ORE.get(),
                        MegaOres.MEGA_METEORID_ICE_ORE.get(),
                        MegaOres.MEGA_METEORID_LEAF_ORE.get(),
                        MegaOres.MEGA_METEORID_MOON_ORE.get(),
                        MegaOres.MEGA_METEORID_SHINY_ORE.get(),
                        MegaOres.MEGA_METEORID_SUN_ORE.get(),
                        MegaOres.MEGA_METEORID_THUNDER_ORE.get(),
                        MegaOres.MEGA_METEORID_WATER_ORE.get(),

                        ModBlocks.DEOXYS_METEORITE.get(),

                        //Decor
                        ModBlocks.CHISELED_MEGA_EVO_BRICK.get(),
                        ModBlocks.CHISELED_MEGA_EVO_BLOCK.get(),
                        ModBlocks.POLISHED_MEGA_EVO_BLOCK.get(),
                        ModBlocks.MEGA_EVO_BRICK.get(),

                        ModBlocks.POWER_SPOT.get(),

                        ModBlocks.PEDESTAL.get(),

                        ModBlocks.REASSEMBLY_UNIT.get(),

                        ModBlocks.DORMANT_CRYSTAL.get(),

                        ModBlocks.ROTOM_MOW.get(),
                        ModBlocks.ROTOM_OVEN.get(),
                        ModBlocks.ROTOM_FAN.get(),
                        ModBlocks.ROTOM_FRIDGE.get(),
                        ModBlocks.ROTOM_WASHING_MACHINE.get()
                );

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(
                        ModBlocks.WISHING_STAR_CRYSTAL.get(),

                        MegaOres.MEGA_STONE_CRYSTAL.get(),
                        MegaOres.KEYSTONE_ORE.get(),
                        ModBlocks.KEYSTONE_BLOCK.get(),

                        ModBlocks.DEOXYS_METEORITE.get(),

                        ModBlocks.POWER_SPOT.get(),

                        ModBlocks.DORMANT_CRYSTAL.get()
                );

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(
                        ModBlocks.MEGA_METEOROID_BLOCK.get(),
                        ModBlocks.MEGA_EVO_BLOCK.get(),
                        MegaOres.MEGA_METEORID_DAWN_ORE.get(),
                        MegaOres.MEGA_METEORID_DUSK_ORE.get(),
                        MegaOres.MEGA_METEORID_FIRE_ORE.get(),
                        MegaOres.MEGA_METEORID_ICE_ORE.get(),
                        MegaOres.MEGA_METEORID_LEAF_ORE.get(),
                        MegaOres.MEGA_METEORID_MOON_ORE.get(),
                        MegaOres.MEGA_METEORID_SHINY_ORE.get(),
                        MegaOres.MEGA_METEORID_SUN_ORE.get(),
                        MegaOres.MEGA_METEORID_THUNDER_ORE.get(),
                        MegaOres.MEGA_METEORID_WATER_ORE.get(),

                        //Decor
                        ModBlocks.CHISELED_MEGA_EVO_BRICK.get(),
                        ModBlocks.CHISELED_MEGA_EVO_BLOCK.get(),
                        ModBlocks.POLISHED_MEGA_EVO_BLOCK.get(),
                        ModBlocks.MEGA_EVO_BRICK.get(),

                        ModBlocks.REASSEMBLY_UNIT.get(),

                        ModBlocks.ROTOM_MOW.get(),
                        ModBlocks.ROTOM_OVEN.get(),
                        ModBlocks.ROTOM_FAN.get(),
                        ModBlocks.ROTOM_FRIDGE.get(),
                        ModBlocks.ROTOM_WASHING_MACHINE.get()
                );
    }
}
