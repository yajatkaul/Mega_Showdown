package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.NotNull;

public class ReassemblyUnitBlock extends Block {
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);

    public ReassemblyUnitBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        Direction playerFacing = ctx.getHorizontalPlayerFacing();

        // Check if there is room to place the upper half
        if (pos.getY() < world.getTopY() - 1 && world.getBlockState(pos.up()).canReplace(ctx)) {
            return this.getDefaultState()
                    .with(HALF, DoubleBlockHalf.LOWER)
                    .with(FACING, playerFacing.getOpposite()); // Ensure block faces opposite of where the player is facing
        } else {
            return null;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf half = state.get(HALF);
        if (direction.getAxis() != Direction.Axis.Y || (half == DoubleBlockHalf.LOWER) != (direction == Direction.UP) || (neighborState.isOf(this) && neighborState.get(HALF) != half)) {
            if (half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
                return Blocks.AIR.getDefaultState();
            }
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

    @Override
    public BlockState onBreak(@NotNull World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (player.isCreative()) {
                preventDropFromBottomPart(world, pos, state, player);
            }
        }

        super.onBreak(world, pos, state, player);
        return state;
    }

    protected static void preventDropFromBottomPart(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleBlockHalf half = state.get(HALF);
        if (half == DoubleBlockHalf.UPPER) {
            BlockPos belowPos = pos.down();
            BlockState belowState = world.getBlockState(belowPos);
            if (belowState.isOf(state.getBlock()) && belowState.get(HALF) == DoubleBlockHalf.LOWER) {
                BlockState newState = belowState.getFluidState().isIn(FluidTags.WATER) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
                world.setBlockState(belowPos, newState, Block.NOTIFY_ALL);
                world.syncWorldEvent(player, 2001, belowPos, Block.getRawIdFromState(belowState));
            }
        }
    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return true;
    }
}