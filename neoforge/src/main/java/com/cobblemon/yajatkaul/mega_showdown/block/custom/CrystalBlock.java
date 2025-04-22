package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CrystalBlock extends AmethystClusterBlock {

    public CrystalBlock(float f, float g, Properties arg) {
        super(f, g, arg);
    }

    @Override
    public int getExpDrop(BlockState state, LevelAccessor level, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity breaker, ItemStack tool) {
        if (level instanceof ServerLevel) {
            Random random = new Random();
            return random.nextInt(4) + 6; // Random value between 6 and 9
        }
        return 0;
    }
}
