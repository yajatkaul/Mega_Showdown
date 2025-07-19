package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CrystalBlock extends AmethystClusterBlock {
    private final boolean dropExp;

    public CrystalBlock(float f, float g, Properties arg, boolean dropExp) {
        super(f, g, arg);
        this.dropExp = dropExp;
    }

    @Override
    public int getExpDrop(BlockState state, LevelAccessor level, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity breaker, ItemStack tool) {
        if (level instanceof ServerLevel && dropExp) {
            Random random = new Random();
            return random.nextInt(4) + 6; // Random value between 6 and 9
        }
        return 0;
    }

    @Override
    protected boolean canSurvive(BlockState arg, LevelReader arg2, BlockPos arg3) {
        return true;
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState arg, Direction arg2, BlockState arg3, LevelAccessor arg4, BlockPos arg5, BlockPos arg6) {
        return arg;
    }
}
