package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.MapColor;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class GracideaBlock extends FlowerBlock {
    public GracideaBlock() {
        super(
                StatusEffects.REGENERATION,
                8,
                Settings.create()
                        .mapColor(MapColor.PINK)
                        .noCollision()
                        .luminance((state) -> 15)
                        .strength(0.0f)
                        .sounds(BlockSoundGroup.GRASS)
        );
    }

    @Override
    protected boolean canPlantOnTop(BlockState state, BlockView world, BlockPos pos) {
        return state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.DIRT) || super.canPlantOnTop(state, world, pos);
    }
}