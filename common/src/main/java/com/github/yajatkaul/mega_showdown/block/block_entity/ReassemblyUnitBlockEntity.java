package com.github.yajatkaul.mega_showdown.block.block_entity;

import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlockEntities;
import com.github.yajatkaul.mega_showdown.block.custom.ReassemblyUnitBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ReassemblyUnitBlockEntity extends BlockEntity {
    public ReassemblyUnitBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MegaShowdownBlockEntities.REASSEMBLY_UNIT_ENTITY.get(), blockPos, blockState);
    }

    public enum ReassembleStage implements StringRepresentable {
        IDLE, COOKING_10, COOKING_50, COOKING_100, FINISHED_10, FINISHED_50, FINISHED_100;

        @Override
        public @NotNull String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    private int cookTime = 0;
    private int maxCookTime = -1;
    private ReassembleStage stage = ReassembleStage.IDLE;

    public void startProcess(ReassembleStage stage, int durationTicks) {
        this.stage = stage;
        this.cookTime = 0;
        this.maxCookTime = durationTicks;

        if (level != null) {
            level.setBlock(worldPosition, getBlockState().setValue(ReassemblyUnitBlock.REASSEMBLE_STAGE, stage), 3);
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ReassemblyUnitBlockEntity be) {
        if (level.isClientSide) return;

        if (be.maxCookTime != -1) {
            be.cookTime++;

            if (be.cookTime >= be.maxCookTime) {
                ReassembleStage finishedStage = switch (be.stage) {
                    case ReassembleStage.COOKING_10 -> ReassembleStage.FINISHED_10;
                    case ReassembleStage.COOKING_50 -> ReassembleStage.FINISHED_50;
                    case ReassembleStage.COOKING_100 -> ReassembleStage.FINISHED_100;
                    default -> ReassembleStage.IDLE;
                };

                be.stage = finishedStage;
                be.maxCookTime = -1;
                be.cookTime = 0;

                level.setBlock(pos, state.setValue(ReassemblyUnitBlock.REASSEMBLE_STAGE, finishedStage), 3);
                setChanged(level, pos, state);
            }
        }
    }

    public boolean isIdle() {
        return stage == ReassembleStage.IDLE;
    }

    public boolean isFinished() {
        return stage == ReassembleStage.FINISHED_10 || stage == ReassembleStage.FINISHED_50 || stage == ReassembleStage.FINISHED_100;
    }

    public ReassembleStage getStage() {
        return stage;
    }

    public void setStage(ReassembleStage stage) {
        this.stage = stage;
        if (this.level instanceof ServerLevel serverLevel) {
            setChanged(serverLevel, this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("CookTime", cookTime);
        tag.putInt("MaxCookTime", maxCookTime);
        tag.putString("Stage", stage.name());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        this.cookTime = tag.getInt("CookTime");
        this.maxCookTime = tag.getInt("MaxCookTime");
        this.stage = ReassembleStage.valueOf(tag.getString("Stage"));
    }
}
