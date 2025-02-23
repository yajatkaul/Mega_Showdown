package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class MegaCrystalBlock extends AmethystClusterBlock {

    public MegaCrystalBlock(float f, float g, Properties arg) {
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

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        // Get block center coordinates
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.5D;
        double z = pos.getZ() + 0.5D;

        // Spawn particles around the block
        for (int i = 0; i < 3; i++) { // Spawn 3 particles per tick
            // Add some random offset to particle position
            double offsetX = (random.nextDouble() - 0.5D) * 0.5D;
            double offsetY = (random.nextDouble() - 0.5D) * 0.5D;
            double offsetZ = (random.nextDouble() - 0.5D) * 0.5D;

            level.addParticle(
                    ParticleTypes.END_ROD, // Particle type
                    x + offsetX, // X position
                    y + offsetY, // Y position
                    z + offsetZ, // Z position
                    0.0D, // X velocity
                    0.0D, // Y velocity
                    0.0D  // Z velocity
            );
        }
    }
}
