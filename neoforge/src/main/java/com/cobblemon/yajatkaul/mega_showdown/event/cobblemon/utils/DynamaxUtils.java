package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.*;

public class DynamaxUtils {
    private static final Map<UUID, DynamaxUtils.ScalingData> activeScalingAnimations = new HashMap<>();
    private static final WeakHashMap<UUID, LivingEntity> entityCache = new WeakHashMap<>();
    public static MinecraftServer server;

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

    public static void startGradualScaling(LivingEntity entity, float targetScale) {
        UUID entityId = entity.getUUID();
        AttributeInstance scaleAttr = entity.getAttribute(Attributes.SCALE);

        if (scaleAttr != null) {
            entityCache.put(entityId, entity);
            float startScale = (float) scaleAttr.getBaseValue();

            ScalingData scalingData = new ScalingData(
                    entity.level().dimension().location().toString(),
                    entityId,
                    startScale,
                    targetScale,
                    60,
                    0
            );

            activeScalingAnimations.put(entityId, scalingData);
        }
    }

    public static void updateScalingAnimations() {
        if (server == null) return;

        Iterator<Map.Entry<UUID, ScalingData>> iterator = activeScalingAnimations.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, ScalingData> entry = iterator.next();
            UUID entityId = entry.getKey();
            ScalingData data = entry.getValue();
            data.currentTick++;

            LivingEntity entity = entityCache.get(entityId);
            if (entity == null || !entity.isAlive()) {
                for (ServerLevel world : server.getAllLevels()) {
                    entity = (LivingEntity) world.getEntity(entityId);
                    if (entity != null) {
                        entityCache.put(entityId, entity);
                        break;
                    }
                }
            }

            if (entity != null && entity.isAlive()) {
                AttributeInstance scaleAttr = entity.getAttribute(Attributes.SCALE);
                if (scaleAttr != null) {
                    float progress = Math.min(1.0f, (float) data.currentTick / data.durationTicks);
                    float newScale = data.startScale + (data.targetScale - data.startScale) * progress;

                    scaleAttr.setBaseValue(newScale);
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
}
