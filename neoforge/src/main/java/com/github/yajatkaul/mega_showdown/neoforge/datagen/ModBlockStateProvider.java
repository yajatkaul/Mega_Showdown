package com.github.yajatkaul.mega_showdown.neoforge.datagen;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MegaShowdown.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(MegaShowdownBlocks.MEGA_METEOROID_BLOCK.get());
        blockWithItem(MegaShowdownBlocks.MEGA_METEOROID_RADIATED_BLOCK.get());
        blockWithItem(MegaShowdownBlocks.MEGA_METEORID_WATER_ORE.get());
        blockWithItem(MegaShowdownBlocks.MEGA_METEORID_DAWN_ORE.get());
        blockWithItem(MegaShowdownBlocks.MEGA_METEORID_DUSK_ORE.get());
        blockWithItem(MegaShowdownBlocks.MEGA_METEORID_FIRE_ORE.get());
        blockWithItem(MegaShowdownBlocks.MEGA_METEORID_ICE_ORE.get());
        blockWithItem(MegaShowdownBlocks.MEGA_METEORID_LEAF_ORE.get());
        blockWithItem(MegaShowdownBlocks.MEGA_METEORID_MOON_ORE.get());
        blockWithItem(MegaShowdownBlocks.MEGA_METEORID_SHINY_ORE.get());
        blockWithItem(MegaShowdownBlocks.MEGA_METEORID_SUN_ORE.get());
        blockWithItem(MegaShowdownBlocks.MEGA_METEORID_THUNDER_ORE.get());
        blockWithItem(MegaShowdownBlocks.KEYSTONE_ORE.get());

        blockWithItem(MegaShowdownBlocks.DEOXYS_METEORITE.get());

        blockWithItem(MegaShowdownBlocks.MEGA_METEOROID_BRICK.get());
        topSideBlock(MegaShowdownBlocks.CHISELED_MEGA_METEOROID_BLOCK.get());
        blockWithItem(MegaShowdownBlocks.CHISELED_MEGA_METEOROID_BRICK.get());
        blockWithItem(MegaShowdownBlocks.POLISHED_MEGA_METEOROID_BLOCK.get());
        blockWithItem(MegaShowdownBlocks.KEYSTONE_BLOCK.get());
    }

    private void blockWithItem(Block deferredBlock) {
        simpleBlockWithItem(deferredBlock, cubeAll(deferredBlock));
    }

    private void topSideBlock(Block block) {
        ResourceLocation side = blockTexture(block);
        ResourceLocation top = ResourceLocation.fromNamespaceAndPath(
                MegaShowdown.MOD_ID,
                "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_top"
        );

        simpleBlockWithItem(block, models().cubeColumn(
                BuiltInRegistries.BLOCK.getKey(block).getPath(),
                side,
                top
        ));
    }
}