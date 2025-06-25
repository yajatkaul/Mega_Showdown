package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.DynamaxEventEnd;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.DynamaxEventStart;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.GlowHandler;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

import java.util.*;


public class DynamaxEvent {
    private static final Map<UUID, ScalingData> activeScalingAnimations = new HashMap<>();
    private static final WeakHashMap<UUID, LivingEntity> entityCache = new WeakHashMap<>();
    private static MinecraftServer server;

    public static void register() {
        DynamaxEventStart.EVENT.register((battle, pokemon, gmax) -> {
            if (gmax) {
                new StringSpeciesFeature("dynamax_form", "gmax").apply(pokemon.getEffectedPokemon());
                for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()) {
                    if (activeBattlePokemon.getBattlePokemon() != null &&
                            activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pokemon.getEffectedPokemon().getOwnerPlayer()
                            && activeBattlePokemon.getBattlePokemon() == pokemon) {
                        battle.sendSidedUpdate(activeBattlePokemon.getActor(),
                                new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, true),
                                new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, false),
                                false);

                    }
                }
                AdvancementHelper.grantAdvancement(pokemon.getEffectedPokemon().getOwnerPlayer(), "dynamax/gigantamax");
            } else {
                AdvancementHelper.grantAdvancement(pokemon.getEffectedPokemon().getOwnerPlayer(), "dynamax/dynamax");
            }

            PokemonEntity pokemonEntity = pokemon.getEntity();
            Vec3d entityPos = pokemonEntity.getPos();

            pokemonEntity.getWorld().playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    ModSounds.DYNAMAX,
                    SoundCategory.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
            );

            if (server == null && pokemonEntity.getWorld() instanceof ServerWorld serverWorld) {
                server = serverWorld.getServer();
            }

            startGradualScaling(pokemonEntity, MegaShowdownConfig.dynamaxScaleFactor.get());

            GlowHandler.applyDynamaxGlow(pokemonEntity);

            battle.dispatchWaitingToFront(3F, () -> {
                SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                return Unit.INSTANCE;
            });
        });

        DynamaxEventEnd.EVENT.register((battle, pokemon) -> {
            new StringSpeciesFeature("dynamax_form", "none").apply(pokemon.getEffectedPokemon());
            for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()) {
                if (activeBattlePokemon.getBattlePokemon() != null &&
                        activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pokemon.getEffectedPokemon().getOwnerPlayer()
                        && activeBattlePokemon.getBattlePokemon() == pokemon) {
                    battle.sendSidedUpdate(activeBattlePokemon.getActor(),
                            new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, true),
                            new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, false),
                            false);

                }
            }
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