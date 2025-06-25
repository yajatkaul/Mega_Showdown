package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PowerSpotBlock extends Block {
    public PowerSpotBlock(Properties properties) {
        super(properties);
        properties.strength(3f)
                .lightLevel((state) -> 15)
                .mapColor(MapColor.COLOR_PURPLE)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE);
    }

    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 4, 14);

    @Override
    protected VoxelShape getShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        return SHAPE;
    }
}
