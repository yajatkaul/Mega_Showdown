package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.block.custom.CrystalBlock;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks.registerBlock;

public class MegaOres {
    public static final Block KEYSTONE_ORE = registerBlock("keystone_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(6, 9), AbstractBlock.Settings.create()
                    .strength(3f)
                    .mapColor(MapColor.PURPLE)
                    .requiresTool()
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block MEGA_STONE_CRYSTAL = registerBlock("mega_stone_crystal",
            new CrystalBlock(4, 3,
                    AbstractBlock.Settings.create()
                            .strength(1.5f)
                            .sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)
                            .nonOpaque()
                            .requiresTool()
                            .pistonBehavior(PistonBehavior.PUSH_ONLY)
                            .luminance((state) -> 15)){
                private static final VoxelShape SHAPE =
                        Block.createCuboidShape(2, 0, 2, 14, 13, 14);

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
            });

    public static void registerBlocks(){

    }
}
