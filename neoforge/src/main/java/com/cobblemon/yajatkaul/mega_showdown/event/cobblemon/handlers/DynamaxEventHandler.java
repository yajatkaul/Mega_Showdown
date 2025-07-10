package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.DynamaxEventEnd;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.DynamaxEventStart;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.DynamaxUtils;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;
import net.minecraft.ChatFormatting;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.*;

public class DynamaxEventHandler {

    @SubscribeEvent
    public void onDynamax(DynamaxEventStart event) {
        PokemonEntity entity = event.getPokemon().getEntity();
        Vec3 entityPos = entity.position(); // Get entity position

        entity.level().playSound(
                null, entityPos.x, entityPos.y, entityPos.z,
                ModSounds.DYNAMAX.get(),
                SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
        );

        if (entity == null) return;

        if (event.getGmax()) {
            new StringSpeciesFeature("dynamax_form", "gmax").apply(event.getPokemon().getEffectedPokemon());
            BattlePokemon pokemon = event.getPokemon();
            for (ActiveBattlePokemon activeBattlePokemon : event.getBattle().getActivePokemon()) {
                if (activeBattlePokemon.getBattlePokemon() != null &&
                        activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pokemon.getEffectedPokemon().getOwnerPlayer()
                        && activeBattlePokemon.getBattlePokemon() == pokemon) {
                    event.getBattle().sendSidedUpdate(activeBattlePokemon.getActor(),
                            new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, true),
                            new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, false),
                            false);

                }
            }
            AdvancementHelper.grantAdvancement(entity.getPokemon().getOwnerPlayer(), "dynamax/gigantamax");
        } else {
            AdvancementHelper.grantAdvancement(entity.getPokemon().getOwnerPlayer(), "dynamax/dynamax");
        }

        entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));

        if (DynamaxUtils.server == null && entity.level() instanceof ServerLevel serverLevel) {
            DynamaxUtils.server = serverLevel.getServer();
        }

        DynamaxUtils.startGradualScaling(entity, MegaShowdownConfig.dynamaxScaleFactor);

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

        event.getBattle().dispatchWaitingToFront(3F, () -> {
            SnowStormHandler.Companion.playAnimation(event.getPokemon().getEffectedPokemon().getEntity(), Set.of("cry"), List.of());
            return Unit.INSTANCE;
        });
    }

    @SubscribeEvent
    public void onDynamaxEnd(DynamaxEventEnd event) {
        new StringSpeciesFeature("dynamax_form", "none").apply(event.getPokemon().getEffectedPokemon());
        BattlePokemon pokemon = event.getPokemon();
        for (ActiveBattlePokemon activeBattlePokemon : event.getBattle().getActivePokemon()) {
            if (activeBattlePokemon.getBattlePokemon() != null &&
                    activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pokemon.getEffectedPokemon().getOwnerPlayer()
                    && activeBattlePokemon.getBattlePokemon() == pokemon) {
                event.getBattle().sendSidedUpdate(activeBattlePokemon.getActor(),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, true),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, false),
                        false);

            }
        }

        LivingEntity entity = event.getPokemon().getEntity();
        if (entity == null) return;

        entity.removeEffect(MobEffects.GLOWING);

        if (DynamaxUtils.server == null && entity.level() instanceof ServerLevel serverLevel) {
            DynamaxUtils.server = serverLevel.getServer();
        }

        DynamaxUtils.startGradualScaling(entity, 1.0f);
    }


}