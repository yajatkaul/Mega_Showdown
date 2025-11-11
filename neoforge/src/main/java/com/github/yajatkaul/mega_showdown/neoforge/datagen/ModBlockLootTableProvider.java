package com.github.yajatkaul.mega_showdown.neoforge.datagen;

import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlocks;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(MegaShowdownBlocks.POWER_SPOT.get());
        dropSelf(MegaShowdownBlocks.MAX_MUSHROOM.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (RegistrySupplier<Block> supplier : MegaShowdownBlocks.BLOCKS) {
            blocks.add(supplier.get());
        }
        return blocks;
    }
}