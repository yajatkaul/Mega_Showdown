package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.item.PokeBallItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.properties.ReassembleStage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ZygardeCube;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ReassemblyUnitBlock extends Block {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<ReassembleStage> REASSEMBLE_STAGE
            = EnumProperty.create("reassemble_stage", ReassembleStage.class);

    private static final VoxelShape UPPER_SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    private static final VoxelShape LOWER_SHAPE = Block.box(1, 0, 1, 15, 16, 15);

    public ReassemblyUnitBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any()
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(FACING, Direction.NORTH)
                .setValue(REASSEMBLE_STAGE, ReassembleStage.IDLE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPE : LOWER_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF, REASSEMBLE_STAGE);
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

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult arg7) {
        if(level.isClientSide){
            return ItemInteractionResult.SUCCESS;
        }

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            pos = pos.below();
            state = level.getBlockState(pos);// Ensure we're always working with the lower half
        }

        BlockState upper = level.getBlockState(pos.above());

        if(hand == InteractionHand.MAIN_HAND && player.getOffhandItem().getItem() instanceof ZygardeCube){
            if(state.getValue(REASSEMBLE_STAGE) == ReassembleStage.IDLE){
                stack = player.getOffhandItem();
                if(stack.get(DataManage.POKEMON_STORAGE) == null){
                    player.displayClientMessage(Component.translatable("message.mega_showdown.zygarde_missing")
                            .withColor(0xFF0000), true);
                    return ItemInteractionResult.SUCCESS;
                }
                Pokemon pokemon = stack.get(DataManage.POKEMON_STORAGE).getPokemon();
                ItemStack cells = new ItemStack(FormeChangeItems.ZYGARDE_CELL.get());
                ItemStack cores = new ItemStack(FormeChangeItems.ZYGARDE_CORE.get());
                if(pokemon.getAspects().contains("10-percent")){
                    cells.setCount(9);
                    cores.setCount(1);
                } else if (pokemon.getAspects().contains("50-percent")) {
                    cells.setCount(49);
                    cores.setCount(1);
                }

                ItemEntity cellDrop = new ItemEntity(level,
                        pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, // slightly above the block
                        cells.copy());
                level.addFreshEntity(cellDrop);

                ItemEntity coreDrop = new ItemEntity(level,
                        pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                        cores.copy());
                level.addFreshEntity(coreDrop);
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.zygarde_cube"));
                stack.set(DataManage.POKEMON_STORAGE, null);
                return ItemInteractionResult.SUCCESS;
            }
        }

        if (state.getValue(REASSEMBLE_STAGE) == ReassembleStage.IDLE && stack.is(FormeChangeItems.ZYGARDE_CUBE)) {
            if(stack.getItem() instanceof ZygardeCube cube){
                ItemStackHandler inv = cube.getInventory(stack, level, player);

                ItemStack slot0 = inv.getStackInSlot(0);
                ItemStack slot1 = inv.getStackInSlot(1);

                if(slot0.getCount() == 95 && slot1.getCount() == 5){
                    inv.setStackInSlot(0, ItemStack.EMPTY);
                    inv.setStackInSlot(1, ItemStack.EMPTY);

                    level.setBlock(pos, state.setValue(REASSEMBLE_STAGE, ReassembleStage.COOKING_100), Block.UPDATE_ALL);
                    level.setBlock(pos.above(), upper.setValue(REASSEMBLE_STAGE, ReassembleStage.COOKING_100), Block.UPDATE_ALL);
                    level.scheduleTick(pos, this, 20 * 60 * 10); // 10 minutes in ticks
                    level.scheduleTick(pos.above(), this, 20 * 60 * 10); // 10 minutes in ticks
                } else if (slot0.getCount() >= 49 && slot1.getCount() >= 1) {
                    ItemStack newSlot0 = slot0.copy();
                    ItemStack newSlot1 = slot1.copy();

                    newSlot0.setCount(slot0.getCount() - 49);
                    newSlot1.setCount(slot1.getCount() - 1);

                    inv.setStackInSlot(0, newSlot0);
                    inv.setStackInSlot(1, newSlot1);

                    level.setBlock(pos, state.setValue(REASSEMBLE_STAGE, ReassembleStage.COOKING_50), Block.UPDATE_ALL);
                    level.setBlock(pos.above(), upper.setValue(REASSEMBLE_STAGE, ReassembleStage.COOKING_50), Block.UPDATE_ALL);
                    level.scheduleTick(pos, this, 20 * 60 * 5); // 5 minutes in ticks
                    level.scheduleTick(pos.above(), this, 20 * 60 * 5); // 5 minutes in ticks
                } else if (slot0.getCount() >= 9 && slot1.getCount() >= 1) {
                    ItemStack newSlot0 = slot0.copy();
                    ItemStack newSlot1 = slot1.copy();

                    newSlot0.setCount(slot0.getCount() - 9);
                    newSlot1.setCount(slot1.getCount() - 1);

                    inv.setStackInSlot(0, newSlot0);
                    inv.setStackInSlot(1, newSlot1);

                    level.setBlock(pos, state.setValue(REASSEMBLE_STAGE, ReassembleStage.COOKING_10), Block.UPDATE_ALL);
                    level.setBlock(pos.above(), upper.setValue(REASSEMBLE_STAGE, ReassembleStage.COOKING_10), Block.UPDATE_ALL);
                    level.scheduleTick(pos, this, 20 * 60 * 2); // 2 minutes in ticks
                    level.scheduleTick(pos.above(), this, 20 * 60 * 2); // 2 minutes in ticks
                } else{
                    player.displayClientMessage(Component.translatable("message.mega_showdown.not_enough_cells_core")
                            .withColor(0xFF0000), true);
                }
            }

            return ItemInteractionResult.SUCCESS;
        }

        if(stack.getItem() instanceof PokeBallItem ball){
            int shinyRoll = ThreadLocalRandom.current().nextInt(1, (int) (Cobblemon.config.getShinyRate() + 1)); // 8193 is exclusive

            if (state.getValue(REASSEMBLE_STAGE) == ReassembleStage.FINISHED_10) {
                Pokemon zygarde = PokemonProperties.Companion.parse("zygarde percent_cells=10").create();
                zygarde.setCaughtBall(ball.getPokeBall());

                if(shinyRoll == 1){
                    zygarde.setShiny(true);
                }

                Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player).add(zygarde);
                stack.shrink(1);
                level.setBlock(pos, state.setValue(REASSEMBLE_STAGE, ReassembleStage.IDLE), Block.UPDATE_ALL);
                level.setBlock(pos.above(), upper.setValue(REASSEMBLE_STAGE, ReassembleStage.IDLE), Block.UPDATE_ALL);
                return ItemInteractionResult.SUCCESS;
            } else if (state.getValue(REASSEMBLE_STAGE) == ReassembleStage.FINISHED_50) {
                Pokemon zygarde = PokemonProperties.Companion.parse("zygarde percent_cells=50").create();
                zygarde.setCaughtBall(ball.getPokeBall());

                if(shinyRoll == 1){
                    zygarde.setShiny(true);
                }

                Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player).add(zygarde);
                stack.shrink(1);
                level.setBlock(pos, state.setValue(REASSEMBLE_STAGE, ReassembleStage.IDLE), Block.UPDATE_ALL);
                level.setBlock(pos.above(), upper.setValue(REASSEMBLE_STAGE, ReassembleStage.IDLE), Block.UPDATE_ALL);
                return ItemInteractionResult.SUCCESS;
            } else if (state.getValue(REASSEMBLE_STAGE) == ReassembleStage.FINISHED_100) {
                Pokemon zygarde = PokemonProperties.Companion.parse("zygarde percent_cells=50 power-construct").create();
                zygarde.setCaughtBall(ball.getPokeBall());

                if(shinyRoll == 1){
                    zygarde.setShiny(true);
                }

                Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player).add(zygarde);
                stack.shrink(1);
                level.setBlock(pos, state.setValue(REASSEMBLE_STAGE, ReassembleStage.IDLE), Block.UPDATE_ALL);
                level.setBlock(pos.above(), upper.setValue(REASSEMBLE_STAGE, ReassembleStage.IDLE), Block.UPDATE_ALL);
                return ItemInteractionResult.SUCCESS;
            }
        }


        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        switch (state.getValue(REASSEMBLE_STAGE)) {
            case COOKING_10: {
                level.setBlock(pos, state.setValue(REASSEMBLE_STAGE, ReassembleStage.FINISHED_10), Block.UPDATE_ALL);
                break;
            }
            case COOKING_50: {
                level.setBlock(pos, state.setValue(REASSEMBLE_STAGE, ReassembleStage.FINISHED_50), Block.UPDATE_ALL);
                break;
            }
            case COOKING_100: {
                level.setBlock(pos, state.setValue(REASSEMBLE_STAGE, ReassembleStage.FINISHED_100), Block.UPDATE_ALL);
                break;
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return switch (state.getValue(REASSEMBLE_STAGE)) {
            case COOKING_10, COOKING_50, COOKING_100 -> true;
            default -> false;
        };
    }
}