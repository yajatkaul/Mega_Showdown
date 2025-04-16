package com.cobblemon.yajatkaul.mega_showdown.event.dynamax;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.scores.PlayerTeam;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.*;

public class DynamaxEventListener {
    private static final Map<UUID, ScalingData> activeScalingAnimations = new HashMap<>();
    private static final WeakHashMap<UUID, LivingEntity> entityCache = new WeakHashMap<>();
    private static MinecraftServer server;

    @SubscribeEvent
    public void onDynamax(DynamaxEvent event) {
        LivingEntity entity = event.getPokemon().getEntity();
        if (entity == null) return;

        if(event.getGmax()){
            new StringSpeciesFeature("dynamax_form", "gmax").apply(event.getPokemon().getEffectedPokemon());
            BattlePokemon pokemon = event.getPokemon();
            for (ActiveBattlePokemon activeBattlePokemon : event.getBattle().getActivePokemon()){
                if(activeBattlePokemon.getBattlePokemon() != null &&
                        activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pokemon.getEffectedPokemon().getOwnerPlayer()
                        && activeBattlePokemon.getBattlePokemon() == pokemon){
                    event.getBattle().sendSidedUpdate(activeBattlePokemon.getActor(),
                            new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, true),
                            new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, false),
                            false);

                }
            }
        }

        entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));

        if (server == null && entity.level() instanceof ServerLevel serverLevel) {
            server = serverLevel.getServer();
        }

        startGradualScaling(entity, 4.0f);

        if (entity.level() instanceof ServerLevel serverLevel) {
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);
            PlayerTeam team = scoreboard.getPlayerTeam(teamName);
            if (team == null) {
                team = scoreboard.addPlayerTeam(teamName);
                if (event.getPokemon().getEffectedPokemon().getSpecies().getName().equals("Calyrex")) {
                    team.setColor(ChatFormatting.BLUE);
                } else {
                    team.setColor(ChatFormatting.RED);
                }
            }
            scoreboard.addPlayerToTeam(entity.getUUID().toString(), team);
        }
    }

    @SubscribeEvent
    public void onDynamaxEnd(DynamaxEventEnd event) {
        new StringSpeciesFeature("dynamax_form", "none").apply(event.getPokemon().getEffectedPokemon());
        BattlePokemon pokemon = event.getPokemon();
        for (ActiveBattlePokemon activeBattlePokemon : event.getBattle().getActivePokemon()){
            if(activeBattlePokemon.getBattlePokemon() != null &&
                    activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pokemon.getEffectedPokemon().getOwnerPlayer()
                    && activeBattlePokemon.getBattlePokemon() == pokemon){
                event.getBattle().sendSidedUpdate(activeBattlePokemon.getActor(),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, true),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, false),
                        false);

            }
        }

        LivingEntity entity = event.getPokemon().getEntity();
        if (entity == null) return;

        entity.removeEffect(MobEffects.GLOWING);

        if (server == null && entity.level() instanceof ServerLevel serverLevel) {
            server = serverLevel.getServer();
        }

        startGradualScaling(entity, 1.0f);
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        updateScalingAnimations();
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

    private static void updateScalingAnimations() {
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