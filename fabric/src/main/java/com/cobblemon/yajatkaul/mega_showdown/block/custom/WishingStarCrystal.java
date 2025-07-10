package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class WishingStarCrystal extends CrystalBlock {
    public WishingStarCrystal(float height, float xzOffset, Settings settings) {
        super(height, xzOffset, settings.strength(1.5f)
                .sounds(BlockSoundGroup.STONE)
                .nonOpaque()
                .requiresTool()
                .pistonBehavior(PistonBehavior.IGNORE)
                .luminance((state) -> 15));
    }

    private static final VoxelShape SHAPE =
            Block.createCuboidShape(2, 0, 2, 14, 9, 14);

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // Get block center coordinates
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.5D;
        double z = pos.getZ() + 0.5D;

        // Spawn particles around the block
        for (int i = 0; i < 3; i++) { // Spawn 3 particles per tick
            double offsetX = (random.nextDouble() - 0.5D) * 0.5D;
            double offsetY = (random.nextDouble() - 0.5D) * 0.5D;
            double offsetZ = (random.nextDouble() - 0.5D) * 0.5D;

            world.addParticle(
                    new DustParticleEffect(new Vector3f(1.0f, 0.0f, 0.0f), 0.5f),
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
