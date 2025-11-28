package com.github.yajatkaul.mega_showdown.entitiy.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class TeraCrystalEntity extends Entity {
    public final AnimationState idleAnimationState = new AnimationState();

    public TeraCrystalEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    private void setupAnimationStates() {
        this.idleAnimationState.start(this.tickCount);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }
    }
}
