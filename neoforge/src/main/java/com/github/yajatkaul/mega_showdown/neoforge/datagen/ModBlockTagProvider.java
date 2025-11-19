package com.github.yajatkaul.mega_showdown.neoforge.datagen;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlocks;
import com.github.yajatkaul.mega_showdown.tag.MegaShowdownTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider
            , @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MegaShowdown.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        MegaShowdownBlocks.PEDESTAL.get(),
                        MegaShowdownBlocks.WISHING_STAR_CRYSTAL.get(),
                        MegaShowdownBlocks.KEYSTONE_ORE.get(),
                        MegaShowdownBlocks.MEGA_STONE_CRYSTAL.get(),
                        MegaShowdownBlocks.MEGA_METEOROID_BLOCK.get(),
                        MegaShowdownBlocks.MEGA_METEOROID_RADIATED_BLOCK.get(),
                        MegaShowdownBlocks.MEGA_METEORID_DAWN_ORE.get(),
                        MegaShowdownBlocks.MEGA_METEORID_DUSK_ORE.get(),
                        MegaShowdownBlocks.MEGA_METEORID_FIRE_ORE.get(),
                        MegaShowdownBlocks.MEGA_METEORID_ICE_ORE.get(),
                        MegaShowdownBlocks.MEGA_METEORID_LEAF_ORE.get(),
                        MegaShowdownBlocks.MEGA_METEORID_MOON_ORE.get(),
                        MegaShowdownBlocks.MEGA_METEORID_SHINY_ORE.get(),
                        MegaShowdownBlocks.MEGA_METEORID_SUN_ORE.get(),
                        MegaShowdownBlocks.MEGA_METEORID_THUNDER_ORE.get(),
                        MegaShowdownBlocks.MEGA_METEORID_WATER_ORE.get(),
                        MegaShowdownBlocks.DEOXYS_METEORITE.get(),
                        MegaShowdownBlocks.POWER_SPOT.get(),
                        MegaShowdownBlocks.REASSEMBLY_UNIT.get(),
                        MegaShowdownBlocks.DORMANT_CRYSTAL.get(),
                        MegaShowdownBlocks.ROTOM_MOW.get(),
                        MegaShowdownBlocks.ROTOM_OVEN.get(),
                        MegaShowdownBlocks.ROTOM_FAN.get(),
                        MegaShowdownBlocks.ROTOM_FRIDGE.get(),
                        MegaShowdownBlocks.ROTOM_WASHING_MACHINE.get()
                );

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(
                        MegaShowdownBlocks.WISHING_STAR_CRYSTAL.get(),
                        MegaShowdownBlocks.MEGA_STONE_CRYSTAL.get(),
                        MegaShowdownBlocks.KEYSTONE_ORE.get(),
                        MegaShowdownBlocks.DEOXYS_METEORITE.get(),
                        MegaShowdownBlocks.POWER_SPOT.get(),
                        MegaShowdownBlocks.DORMANT_CRYSTAL.get()
                );

        tag(MegaShowdownTags.Blocks.POWER_SPOT)
                .add(
                        MegaShowdownBlocks.POWER_SPOT.get()
                );
    }
}