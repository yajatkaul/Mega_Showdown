package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

import java.util.*;

public class DynamaxUtils {
    private static final Map<UUID, ScalingData> activeScalingAnimations = new HashMap<>();
    private static final WeakHashMap<UUID, LivingEntity> entityCache = new WeakHashMap<>();
    public static MinecraftServer server;

    public static void startGradualScaling(LivingEntity entity, float targetScale) {
        UUID entityId = entity.getUuid();
        EntityAttributeInstance scaleAttribute = entity.getAttributeInstance(EntityAttributes.GENERIC_SCALE);

        if (scaleAttribute != null) {
            entityCache.put(entityId, entity);

            float startScale = (float) scaleAttribute.getBaseValue();

            int durationTicks = 60;

            ScalingData scalingData = new ScalingData(
                    entity.getWorld().getRegistryKey().toString(),
                    entityId,
                    startScale,
                    targetScale,
                    durationTicks,
                    0
            );

            activeScalingAnimations.put(entityId, scalingData);
        }
    }

    public static void updateScalingAnimations(MinecraftServer server) {
        Iterator<Map.Entry<UUID, ScalingData>> iterator = activeScalingAnimations.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, ScalingData> entry = iterator.next();
            UUID entityId = entry.getKey();
            ScalingData data = entry.getValue();

            data.currentTick++;

            LivingEntity entity = entityCache.get(entityId);

            if (entity == null || entity.isRemoved()) {
                for (ServerWorld world : server.getWorlds()) {
                    entity = (LivingEntity) world.getEntity(entityId);
                    if (entity != null) {
                        entityCache.put(entityId, entity);
                        break;
                    }
                }
            }

            if (entity != null && !entity.isRemoved()) {
                EntityAttributeInstance scaleAttribute = entity.getAttributeInstance(EntityAttributes.GENERIC_SCALE);
                if (scaleAttribute != null) {
                    float progress = Math.min(1.0f, (float) data.currentTick / data.durationTicks);
                    float newScale = data.startScale + (data.targetScale - data.startScale) * progress;

                    scaleAttribute.setBaseValue(newScale);
                }

                if (data.currentTick >= data.durationTicks) {
                    iterator.remove();
                    entityCache.remove(entityId);
                }
            } else {
                iterator.remove();
                entityCache.remove(entityId);
            }
        }
    }

    private static class ScalingData {
        final String worldId;
        final UUID entityId;
        final float startScale;
        final float targetScale;
        final int durationTicks;
        int currentTick;

        public ScalingData(String worldId, UUID entityId, float startScale, float targetScale, int durationTicks, int currentTick) {
            this.worldId = worldId;
            this.entityId = entityId;
            this.startScale = startScale;
            this.targetScale = targetScale;
            this.durationTicks = durationTicks;
            this.currentTick = currentTick;
        }
    }
}
