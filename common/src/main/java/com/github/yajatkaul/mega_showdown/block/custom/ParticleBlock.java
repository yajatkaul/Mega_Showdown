package com.github.yajatkaul.mega_showdown.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ParticleBlock extends CrystalBlock {
    public final VoxelShape SHAPE;
    public final ParticleOptions particleType;

    public ParticleBlock(float f, float g, Properties arg, boolean dropExp, VoxelShape shape, ParticleOptions particleType) {
        super(f, g, arg, dropExp);
        this.SHAPE = shape;
        this.particleType = particleType;
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        double x = blockPos.getX() + 0.5D;
        double y = blockPos.getY() + 0.5D;
        double z = blockPos.getZ() + 0.5D;

        for (int i = 0; i < 3; i++) {

            double offsetX = (randomSource.nextDouble() - 0.5D) * 0.5D;
            double offsetY = (randomSource.nextDouble() - 0.5D) * 0.5D;
            double offsetZ = (randomSource.nextDouble() - 0.5D) * 0.5D;

            level.addParticle(
                    particleType,
                    x + offsetX,
                    y + offsetY,
                    z + offsetZ,
                    0.0D,
                    0.0D,
                    0.0D
            );
        }
    }
}
