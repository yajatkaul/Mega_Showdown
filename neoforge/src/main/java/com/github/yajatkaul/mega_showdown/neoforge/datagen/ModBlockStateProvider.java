package com.github.yajatkaul.mega_showdown.neoforge.datagen;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MegaShowdown.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        blockWithItem(UltraPlantBlocks.BLUE_CRYSTAL.get());
    }

    private void blockWithItem(Block deferredBlock) {
        simpleBlockWithItem(deferredBlock, cubeAll(deferredBlock));
    }
}