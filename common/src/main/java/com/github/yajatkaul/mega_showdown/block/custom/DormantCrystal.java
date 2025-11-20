package com.github.yajatkaul.mega_showdown.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DormantCrystal extends CrystalBlock {
    private static final VoxelShape SHAPE_UP = Block.box(5, 0, 4, 11, 4, 11);      // floor
    private static final VoxelShape SHAPE_DOWN = Block.box(5, 12, 5, 11, 16, 12);  // ceiling
    private static final VoxelShape SHAPE_NORTH = Block.box(5, 4, 12, 11, 11, 15); // north wall
    private static final VoxelShape SHAPE_SOUTH = Block.box(5, 5, 0, 11, 12, 4);   // south wall
    private static final VoxelShape SHAPE_WEST = Block.box(12, 4, 5, 16, 11, 11);  // west wall
    private static final VoxelShape SHAPE_EAST = Block.box(0, 4, 5, 4, 11, 11);    // east wall
    public DormantCrystal(float f, float g, Properties properties, boolean dropExp) {
        super(f, g, properties.strength(3f)
                        .sound(SoundType.MEDIUM_AMETHYST_BUD)
                        .noOcclusion()
                        .requiresCorrectToolForDrops()
                        .pushReaction(PushReaction.IGNORE)
                        .lightLevel((state) -> 15)
                , dropExp);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        return switch (state.getValue(AmethystClusterBlock.FACING)) {
            case DOWN -> SHAPE_DOWN;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
            default -> SHAPE_UP;
        };
    }
}
