package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.DynamaxUtils;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.GlowHandler;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Set;

public class DynamaxEventHandler {
    public static void dynamaxStartHandler(PokemonBattle battle, BattlePokemon pokemon, boolean gmax) {
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

        if (DynamaxUtils.server == null && pokemonEntity.getWorld() instanceof ServerWorld serverWorld) {
            DynamaxUtils.server = serverWorld.getServer();
        }

        DynamaxUtils.startGradualScaling(pokemonEntity, MegaShowdownConfig.dynamaxScaleFactor.get());

        GlowHandler.applyDynamaxGlow(pokemonEntity);

        battle.dispatchWaitingToFront(3F, () -> {
            SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
            return Unit.INSTANCE;
        });
    }

    public static void dynamaxEndHandler(PokemonBattle battle, BattlePokemon pokemon) {
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

        if (DynamaxUtils.server == null && pokemonEntity.getWorld() instanceof ServerWorld serverWorld) {
            DynamaxUtils.server = serverWorld.getServer();
        }

        DynamaxUtils.startGradualScaling(pokemonEntity, 1.0f);
    }
}
