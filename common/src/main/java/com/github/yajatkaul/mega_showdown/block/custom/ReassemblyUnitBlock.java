package com.github.yajatkaul.mega_showdown.block.custom;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.item.PokeBallItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlockEntities;
import com.github.yajatkaul.mega_showdown.block.block_entity.ReassemblyUnitBlockEntity;
import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import com.github.yajatkaul.mega_showdown.item.custom.form_change.ZygardeCube;
import com.github.yajatkaul.mega_showdown.utils.NBTInventoryUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public class ReassemblyUnitBlock extends BaseEntityBlock {
    public static final EnumProperty<ReassemblyUnitBlockEntity.ReassembleStage> REASSEMBLE_STAGE
            = EnumProperty.create("reassemble_stage", ReassemblyUnitBlockEntity.ReassembleStage.class);
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<ReassemblyUnitBlock> CODEC = simpleCodec(ReassemblyUnitBlock::new);
    private static final VoxelShape UPPER_SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    private static final VoxelShape LOWER_SHAPE = Block.box(1, 0, 1, 15, 16, 15);

    public ReassemblyUnitBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(FACING, Direction.NORTH)
                .setValue(REASSEMBLE_STAGE, ReassemblyUnitBlockEntity.ReassembleStage.IDLE));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPE : LOWER_SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        DoubleBlockHalf doubleblockhalf = blockState.getValue(HALF);
        if (direction.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || blockState2.is(this) && blockState2.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !blockState.canSurvive(levelAccessor, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        DoubleBlockHalf doubleblockhalf = blockState.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = blockPos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(blockState.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockstate1 = blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(blockpos, blockstate1, 35);
                level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }
        return super.playerWillDestroy(level, blockPos, blockState, player);
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF, REASSEMBLE_STAGE);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (level.isClientSide) {
            return ItemInteractionResult.FAIL;
        }

        pos = blockState.getValue(HALF) == DoubleBlockHalf.UPPER
                ? pos.below()
                : pos;

        if (level.getBlockEntity(pos) instanceof ReassemblyUnitBlockEntity blockEntity) {
            if (itemStack.getItem() instanceof ZygardeCube) {
                CompoundTag tag = itemStack.getOrDefault(MegaShowdownDataComponents.NBT_POKEMON.get(),
                        new CompoundTag());
                CompoundTag storedTag = itemStack.get(MegaShowdownDataComponents.NBT_INV.get());
                Pokemon storedPokemon = null;
                if (storedTag != null) {
                    storedPokemon = new Pokemon().loadFromNBT(level.registryAccess(), storedTag);
                }

                if (blockEntity.isIdle()) {
                    if (storedPokemon == null) {
                        SimpleContainer simpleContainer = NBTInventoryUtils.deserializeInventory(tag, level.registryAccess());
                        ItemStack slot0 = simpleContainer.getItem(0);
                        ItemStack slot1 = simpleContainer.getItem(1);

                        if (slot0.getCount() == 95 && slot1.getCount() == 5) {
                            blockEntity.startProcess(ReassemblyUnitBlockEntity.ReassembleStage.COOKING_100, 20 * 60 * 10);
                            simpleContainer.setItem(0, ItemStack.EMPTY);
                            simpleContainer.setItem(1, ItemStack.EMPTY);
                        } else if (slot0.getCount() >= 49 && slot1.getCount() >= 1) {
                            blockEntity.startProcess(ReassemblyUnitBlockEntity.ReassembleStage.COOKING_50, 20 * 60 * 5);

                            slot0.setCount(slot0.getCount() - 49);
                            slot1.setCount(slot1.getCount() - 1);

                            simpleContainer.setItem(0, slot0);
                            simpleContainer.setItem(1, slot1);
                        } else if (slot0.getCount() >= 9 && slot1.getCount() >= 1) {
                            blockEntity.startProcess(ReassemblyUnitBlockEntity.ReassembleStage.COOKING_10, 20 * 60 * 2);

                            slot0.setCount(slot0.getCount() - 9);
                            slot1.setCount(slot1.getCount() - 1);

                            simpleContainer.setItem(0, slot0);
                            simpleContainer.setItem(1, slot1);
                        } else {
                            player.displayClientMessage(Component.translatable("message.mega_showdown.not_enough_cells_core")
                                    .withStyle(ChatFormatting.RED), true);
                            return ItemInteractionResult.FAIL;
                        }

                        itemStack.set(MegaShowdownDataComponents.NBT_POKEMON.get(), NBTInventoryUtils.serializeInventory(simpleContainer, level.registryAccess()));
                    } else {
                        ItemStack cells = new ItemStack(MegaShowdownItems.ZYGARDE_CELL.get());
                        ItemStack cores = new ItemStack(MegaShowdownItems.ZYGARDE_CORE.get());
                        if (storedPokemon.getAspects().contains("10-percent")) {
                            cells.setCount(9);
                            cores.setCount(1);
                        } else if (storedPokemon.getAspects().contains("50-percent")) {
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

                        itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.zygarde_cube.empty"));
                        itemStack.remove(MegaShowdownDataComponents.NBT_INV.get());
                    }
                    return ItemInteractionResult.SUCCESS;
                } else {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.machine_being_used")
                            .withStyle(ChatFormatting.RED), true);
                }
            } else if (itemStack.getItem() instanceof PokeBallItem pokeBall) {
                if (blockEntity.isFinished()) {
                    int shinyRoll = ThreadLocalRandom.current().nextInt(1, (int) (Cobblemon.config.getShinyRate() + 1)); // 8193 is exclusive
                    Pokemon zygarde = PokemonProperties.Companion.parse("zygarde").create();
                    zygarde.setCaughtBall(pokeBall.getPokeBall());

                    if (shinyRoll == 1) {
                        zygarde.setShiny(true);
                    }

                    if (blockEntity.getStage() == ReassemblyUnitBlockEntity.ReassembleStage.FINISHED_10) {
                        new StringSpeciesFeature("percent_cells", "10").apply(zygarde);
                    } else if (blockEntity.getStage() == ReassemblyUnitBlockEntity.ReassembleStage.FINISHED_50) {
                        new StringSpeciesFeature("percent_cells", "50").apply(zygarde);
                    } else if (blockEntity.getStage() == ReassemblyUnitBlockEntity.ReassembleStage.FINISHED_100) {
                        new StringSpeciesFeature("percent_cells", "50").apply(zygarde);
                        new FlagSpeciesFeature("power-construct", true).apply(zygarde);
                    }
                    Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player).add(zygarde);
                    itemStack.consume(1, player);
                    blockEntity.setStage(ReassemblyUnitBlockEntity.ReassembleStage.IDLE);
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }

        return ItemInteractionResult.FAIL;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, MegaShowdownBlockEntities.REASSEMBLY_UNIT_ENTITY.get(), ReassemblyUnitBlockEntity::tick);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER
                ? new ReassemblyUnitBlockEntity(pos, state)
                : null;
    }
}
