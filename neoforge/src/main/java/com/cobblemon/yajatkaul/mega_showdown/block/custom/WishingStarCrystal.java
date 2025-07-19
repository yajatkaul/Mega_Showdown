package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

public class WishingStarCrystal extends CrystalBlock {
    public WishingStarCrystal(float f, float g, Properties properties, boolean dropExp) {
        super(f, g, properties.strength(1.5f)
                .sound(SoundType.MEDIUM_AMETHYST_BUD)
                .noOcclusion()
                .requiresCorrectToolForDrops()
                .pushReaction(PushReaction.IGNORE)
                .lightLevel((state) -> 15)
                , dropExp);
    }

    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 9, 14);

    @Override
    protected VoxelShape getShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        return SHAPE;
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
                    new DustParticleOptions(
                            new Vector3f(1.0f, 0.0f, 0.0f), // From color
                            0.5f
                    ),
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
