package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class MegaCrystalBlock extends AmethystClusterBlock {
    private static final IntProvider EXP_RANGE = UniformIntProvider.create(6, 9);

    public MegaCrystalBlock(float height, float xzOffset, Settings settings) {
        super(height, xzOffset, settings);
    }

    @Override
    protected void dropExperienceWhenMined(ServerWorld world, BlockPos pos, ItemStack tool, IntProvider experience) {
        super.dropExperienceWhenMined(world, pos, tool, experience);
    }

    @Override
    protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        if (dropExperience) {
            dropExperienceWhenMined(world, pos, tool, EXP_RANGE);
        }
        super.onStacksDropped(state, world, pos, tool, dropExperience);
    }
}