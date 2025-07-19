package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class CrystalBlock extends AmethystClusterBlock {
    private final boolean dropExp;
    private static final IntProvider EXP_RANGE = UniformIntProvider.create(6, 9);

    public CrystalBlock(float height, float xzOffset, Settings settings, boolean dropExp) {
        super(height, xzOffset, settings);
        this.dropExp = dropExp;
    }

    @Override
    protected void dropExperienceWhenMined(ServerWorld world, BlockPos pos, ItemStack tool, IntProvider experience) {
        super.dropExperienceWhenMined(world, pos, tool, experience);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return true;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return state;
    }

    @Override
    protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        if (dropExperience && this.dropExp) {
            dropExperienceWhenMined(world, pos, tool, EXP_RANGE);
        }
        super.onStacksDropped(state, world, pos, tool, dropExperience);
    }
}