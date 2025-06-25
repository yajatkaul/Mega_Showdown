package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class DormantCrystal extends CrystalBlock {
    public DormantCrystal(float height, float xzOffset, Settings settings) {
        super(height, xzOffset, settings);
        settings.strength(3f)
                .requiresTool()
                .mapColor(MapColor.DIAMOND_BLUE)
                .luminance((state) -> 15)
                .nonOpaque()
                .pistonBehavior(PistonBehavior.IGNORE)
                .sounds(BlockSoundGroup.AMETHYST_CLUSTER);
    }

    private static final VoxelShape SHAPE_UP = Block.createCuboidShape(5, 0, 4, 11, 4, 11);      // floor
    private static final VoxelShape SHAPE_DOWN = Block.createCuboidShape(5, 12, 5, 11, 16, 12);  // ceiling
    private static final VoxelShape SHAPE_NORTH = Block.createCuboidShape(5, 4, 12, 11, 11, 15); // north wall
    private static final VoxelShape SHAPE_SOUTH = Block.createCuboidShape(5, 5, 0, 11, 12, 4);   // south wall
    private static final VoxelShape SHAPE_WEST = Block.createCuboidShape(12, 4, 5, 16, 11, 11);  // west wall
    private static final VoxelShape SHAPE_EAST = Block.createCuboidShape(0, 4, 5, 4, 11, 11);    // east wall


    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case DOWN -> SHAPE_DOWN;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
            default -> SHAPE_UP; // case UP
        };
    }
}
