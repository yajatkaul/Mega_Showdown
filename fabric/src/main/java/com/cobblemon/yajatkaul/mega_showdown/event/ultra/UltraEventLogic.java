package com.cobblemon.yajatkaul.mega_showdown.event.ultra;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.yajatkaul.mega_showdown.event.dynamax.DynamaxEvent;
import com.cobblemon.yajatkaul.mega_showdown.event.dynamax.DynamaxEventEnd;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.UltraLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.LazyLib;
import kotlin.Unit;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

import java.util.*;


public class UltraEventLogic {
    public static void register() {
        UltraEvent.EVENT.register((battle, pokemon) -> {
            new FlagSpeciesFeature("ultra", true).apply(pokemon.getEffectedPokemon());
            UltraLogic.ultraAnimation(pokemon.getEntity());

            for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()){
                if(activeBattlePokemon.getBattlePokemon() != null &&
                        activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pokemon.getEffectedPokemon().getOwnerPlayer()
                        && activeBattlePokemon.getBattlePokemon() == pokemon){
                    battle.sendSidedUpdate(activeBattlePokemon.getActor(),
                            new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, true),
                            new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, false),
                            false);

                }
            }

            battle.sendUpdate(new AbilityUpdatePacket(pokemon::getEffectedPokemon, pokemon.getEffectedPokemon().getAbility().getTemplate()));
            battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon.getEffectedPokemon()));

            battle.dispatchWaitingToFront(3F, () -> {
                LazyLib.Companion.cryAnimation(pokemon.getEntity());
                return Unit.INSTANCE;
            });
        });
    }
}