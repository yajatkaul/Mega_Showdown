package com.github.yajatkaul.mega_showdown.entitiy.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;

public class TeraHatEntity extends Entity {
    public TeraHatEntity(EntityType<? extends Entity> entityType, Level level) {
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
}
