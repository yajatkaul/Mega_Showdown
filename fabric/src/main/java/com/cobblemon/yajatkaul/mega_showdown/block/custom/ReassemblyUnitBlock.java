package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.item.PokeBallItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.properties.ReassembleStage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ZygardeCube;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
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
    public static final EnumProperty<ReassembleStage> REASSEMBLE_STAGE
            = EnumProperty.of("reassemble_stage", ReassembleStage.class);

    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);

    public ReassemblyUnitBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(HALF, DoubleBlockHalf.LOWER)
                .with(REASSEMBLE_STAGE, ReassembleStage.IDLE));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF, REASSEMBLE_STAGE);
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

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            pos = pos.down();
            state = world.getBlockState(pos); // Ensure we're working with lower half
        }

        if (state.get(REASSEMBLE_STAGE) == ReassembleStage.IDLE && stack.isOf(FormeChangeItems.ZYGARDE_CUBE)) {
            if (stack.getItem() instanceof ZygardeCube cube) {
                SimpleInventory inv = cube.getInventory(stack, player);

                ItemStack slot0 = inv.getStack(0);
                ItemStack slot1 = inv.getStack(1);

                if (slot0.getCount() == 95 && slot1.getCount() == 5) {
                    inv.setStack(0, ItemStack.EMPTY);
                    inv.setStack(1, ItemStack.EMPTY);
                    world.setBlockState(pos, state.with(REASSEMBLE_STAGE, ReassembleStage.COOKING_100), 3);
                    world.scheduleBlockTick(pos, this, 20 * 60 * 2);
                } else if (slot0.getCount() >= 49 && slot1.getCount() >= 1) {
                    slot0.decrement(49);
                    slot1.decrement(1);
                    inv.setStack(0, slot0);
                    inv.setStack(1, slot1);
                    world.setBlockState(pos, state.with(REASSEMBLE_STAGE, ReassembleStage.COOKING_50), 3);
                    world.scheduleBlockTick(pos, this, 20 * 60 * 5);
                } else if (slot0.getCount() >= 9 && slot1.getCount() >= 1) {
                    slot0.decrement(9);
                    slot1.decrement(1);
                    inv.setStack(0, slot0);
                    inv.setStack(1, slot1);
                    world.setBlockState(pos, state.with(REASSEMBLE_STAGE, ReassembleStage.COOKING_10), 3);
                    world.scheduleBlockTick(pos, this, 20 * 60 * 10);
                } else {
                    player.sendMessage(Text.literal("You don't have enough cells/core").styled(s -> s.withColor(Formatting.RED)), true);
                }

                if(!world.isClient){
                    stack.set(DataManage.ZYGARDE_CUBE_INV, ZygardeCube.serializeInventory(inv,
                            player.getWorld().getRegistryManager()));
                }
            }

            return ItemActionResult.success(world.isClient());
        }

        if (stack.getItem() instanceof PokeBallItem) {
            if (state.get(REASSEMBLE_STAGE) == ReassembleStage.FINISHED_10) {
                if (!world.isClient()) {
                    Pokemon zygarde = PokemonProperties.Companion.parse("zygarde percent_cells=10-percent").create();
                    Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player).add(zygarde);
                }
                world.setBlockState(pos, state.with(REASSEMBLE_STAGE, ReassembleStage.IDLE), 3);
                return ItemActionResult.success(world.isClient());
            } else if (state.get(REASSEMBLE_STAGE) == ReassembleStage.FINISHED_50) {
                if (!world.isClient()) {
                    Pokemon zygarde = PokemonProperties.Companion.parse("zygarde percent_cells=50-percent").create();
                    Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player).add(zygarde);
                }
                world.setBlockState(pos, state.with(REASSEMBLE_STAGE, ReassembleStage.IDLE), 3);
                return ItemActionResult.success(world.isClient());
            } else if (state.get(REASSEMBLE_STAGE) == ReassembleStage.FINISHED_100) {
                if (!world.isClient()) {
                    Pokemon zygarde = PokemonProperties.Companion.parse("zygarde percent_cells=50-percent power-construct").create();
                    Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player).add(zygarde);
                }
                world.setBlockState(pos, state.with(REASSEMBLE_STAGE, ReassembleStage.IDLE), 3);
                return ItemActionResult.success(world.isClient());
            }
        }

        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        switch (state.get(REASSEMBLE_STAGE)) {
            case COOKING_10 -> world.setBlockState(pos, state.with(REASSEMBLE_STAGE, ReassembleStage.FINISHED_10), 3);
            case COOKING_50 -> world.setBlockState(pos, state.with(REASSEMBLE_STAGE, ReassembleStage.FINISHED_50), 3);
            case COOKING_100 -> world.setBlockState(pos, state.with(REASSEMBLE_STAGE, ReassembleStage.FINISHED_100), 3);
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return switch (state.get(REASSEMBLE_STAGE)) {
            case COOKING_10, COOKING_50, COOKING_100 -> true;
            default -> false;
        };
    }
}