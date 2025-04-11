package com.cobblemon.yajatkaul.mega_showdown.event.dynamax;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;

import java.util.*;


public class DynamaxEventLogic {
    private static final Map<UUID, ScalingData> activeScalingAnimations = new HashMap<>();
    private static final WeakHashMap<UUID, LivingEntity> entityCache = new WeakHashMap<>();
    private static MinecraftServer server;

    public static void register() {
        DynamaxEvent.EVENT.register((battle, pokemon) -> {
            pokemon.getEntity().addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));
            LivingEntity pokemonEntity = pokemon.getEntity();

            if (server == null && pokemonEntity.getWorld() instanceof ServerWorld serverWorld) {
                server = serverWorld.getServer();
            }

            startGradualScaling(pokemonEntity, 3.0f);

            if (pokemon.getEntity().getWorld() instanceof ServerWorld serverLevel) {
                ServerScoreboard scoreboard = serverLevel.getScoreboard();
                String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);
                Team team = scoreboard.getTeam(teamName);
                if (team == null) {
                    team = scoreboard.addTeam(teamName);
                    if(pokemon.getEffectedPokemon().getSpecies().getName().equals("Calyrex")){
                        team.setColor(Formatting.BLUE);
                    }else{
                        team.setColor(Formatting.RED);
                    }
                }
                scoreboard.addScoreHolderToTeam(pokemon.getEntity().getUuid().toString(), team);
            }
        });

        DynamaxEventEnd.EVENT.register((battle, pokemon) -> {
            pokemon.getEntity().removeStatusEffect(StatusEffects.GLOWING);
            LivingEntity pokemonEntity = pokemon.getEntity();

            if (server == null && pokemonEntity.getWorld() instanceof ServerWorld serverWorld) {
                server = serverWorld.getServer();
            }

            startGradualScaling(pokemonEntity, 1.0f);
        });

        ServerTickEvents.END_SERVER_TICK.register(serverInstance -> {
            server = serverInstance;
            updateScalingAnimations();
        });
    }

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

    private static void updateScalingAnimations() {
        if (server == null) return;

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

            // If entity exists, update its scale
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