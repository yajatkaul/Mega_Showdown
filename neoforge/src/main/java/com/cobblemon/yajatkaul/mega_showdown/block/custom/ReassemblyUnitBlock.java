package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ReassemblyUnitBlock extends Block {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public ReassemblyUnitBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any()
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

    @Override
    protected BlockState updateShape(BlockState arg, Direction arg2, BlockState arg3, LevelAccessor arg4, BlockPos arg5, BlockPos arg6) {
        DoubleBlockHalf doubleblockhalf = arg.getValue(HALF);
        if (arg2.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (arg2 == Direction.UP) || arg3.is(this) && arg3.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && arg2 == Direction.DOWN && !arg.canSurvive(arg4, arg5) ? Blocks.AIR.defaultBlockState() : super.updateShape(arg, arg2, arg3, arg4, arg5, arg6);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    public BlockState playerWillDestroy(Level arg, BlockPos arg2, BlockState arg3, Player arg4) {
        if (!arg.isClientSide) {
            if (arg4.isCreative()) {
                preventDropFromBottomPart(arg, arg2, arg3, arg4);
            }
        }

        return super.playerWillDestroy(arg, arg2, arg3, arg4);
    }

    protected static void preventDropFromBottomPart(Level arg, BlockPos arg2, BlockState arg3, Player arg4) {
        DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)arg3.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = arg2.below();
            BlockState blockstate = arg.getBlockState(blockpos);
            if (blockstate.is(arg3.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockstate1 = blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                arg.setBlock(blockpos, blockstate1, 35);
                arg.levelEvent(arg4, 2001, blockpos, Block.getId(blockstate));
            }
        }

    }


    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Direction playerFacing = context.getHorizontalDirection();

        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)) {
            return this.defaultBlockState()
                    .setValue(HALF, DoubleBlockHalf.LOWER)
                    .setValue(FACING, playerFacing.getOpposite());
        } else {
            return null;
        }
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }

    @Override
    public @org.jetbrains.annotations.Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.PUSH_ONLY;
    }
}