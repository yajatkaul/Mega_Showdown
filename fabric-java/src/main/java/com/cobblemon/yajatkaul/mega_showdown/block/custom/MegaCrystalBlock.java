package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

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

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
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

            world.addParticle(
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