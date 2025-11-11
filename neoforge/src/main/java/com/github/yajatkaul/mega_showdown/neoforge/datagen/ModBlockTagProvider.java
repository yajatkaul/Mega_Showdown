package com.github.yajatkaul.mega_showdown.neoforge.datagen;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlocks;
import com.github.yajatkaul.mega_showdown.tag.ModTags;
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
                .add(MegaShowdownBlocks.POWER_SPOT.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(MegaShowdownBlocks.POWER_SPOT.get());

        tag(ModTags.Blocks.POWER_SPOT)
                .add(MegaShowdownBlocks.POWER_SPOT.get());
    }
}