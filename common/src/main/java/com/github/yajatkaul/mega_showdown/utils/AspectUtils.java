package com.github.yajatkaul.mega_showdown.utils;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.battles.model.actor.ActorType;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.status.MegaShowdownStatusEffects;
import com.github.yajatkaul.mega_showdown.utils.particles.MinecraftParticle;
import com.github.yajatkaul.mega_showdown.utils.particles.SnowStormParticle;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class AspectUtils {
    public static void applyAspects(Pokemon pokemon, List<String> aspects) {
        for (String aspect : aspects) {
            String[] aspect_split = aspect.split("=");
            if (aspect_split[1].equals("true") || aspect_split[1].equals("false")) {
                new FlagSpeciesFeature(aspect_split[0], Boolean.parseBoolean(aspect_split[1])).apply(pokemon);
            } else {
                new StringSpeciesFeature(aspect_split[0], aspect_split[1]).apply(pokemon);
            }
        }
    }

    public record EffectPair(
            Effect effect,
            List<String> aspects
    ) {
        public static final Codec<EffectPair> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Effect.CODEC.fieldOf("effect").forGetter(EffectPair::effect),
                Codec.STRING.listOf().fieldOf("aspects").forGetter(EffectPair::aspects)
        ).apply(instance, EffectPair::new));
    }

    public static void appendRevertDataPokemon(Effect effect, List<String> string, Pokemon pokemon, String tagName) {
        EffectPair effectPair = new EffectPair(effect, string);

        List<EffectPair> existing = getRevertDataPokemon(pokemon, tagName);

        existing.add(effectPair);

        Tag encoded =
                EffectPair.CODEC.listOf()
                        .encodeStart(NbtOps.INSTANCE, existing)
                        .getOrThrow();

        pokemon.getPersistentData().put(tagName, encoded);
    }

    public static List<EffectPair> getRevertDataPokemon(Pokemon pokemon, String tagName) {
        Tag raw = pokemon.getPersistentData().get(tagName);

        if (raw == null) return new ArrayList<>();

        return EffectPair.CODEC.listOf()
                .parse(NbtOps.INSTANCE, raw)
                .result()
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
    }

    public static void revertPokemonsIfRequired(PlayerPartyStore playerPartyStore) {
        for (Pokemon pokemon : playerPartyStore) {
            AspectUtils.revertPokemonsIfRequired(pokemon);
        }
    }

    public static void revertPokemonsIfRequired(Pokemon pokemon) {
        if (pokemon.getPersistentData().contains("battle_end_revert")) {
            List<EffectPair> aspects = AspectUtils.getRevertDataPokemon(
                    pokemon,
                    "battle_end_revert"
            );

            for (EffectPair effectPair: aspects) {
                effectPair.effect.revertEffects(pokemon, effectPair.aspects, null);
            }
        }

        if (pokemon.getPersistentData().contains("apply_aspects")) {
            List<EffectPair> aspects = AspectUtils.getRevertDataPokemon(
                    pokemon,
                    "apply_aspects"
            );

            for (EffectPair effectPair: aspects) {
                effectPair.effect.revertEffects(pokemon, effectPair.aspects, null);
            }
        }

        if (pokemon.getPersistentData().getBoolean("is_tera")) {
            pokemon.getPersistentData().putBoolean("is_tera", false);
            if (pokemon.getEntity() != null) {
                pokemon.getEntity().removeEffect(MobEffects.GLOWING);
            }
        }

        if (pokemon.getPersistentData().getBoolean("is_max")) {
            pokemon.getPersistentData().putBoolean("is_max", false);
            if (pokemon.getEntity() != null) {
                AspectUtils.scaleDownDynamax(pokemon.getEntity());
            }
        }

        if (pokemon.getPersistentData().getBoolean("form_changing")) {
            pokemon.getPersistentData().putBoolean("form_changing", false);
        }
    }

    public static void updatePackets(BattlePokemon battlePokemon) {
        Pokemon pokemon = battlePokemon.getEntity().getPokemon();
        PokemonBattle battle = battlePokemon.getActor().getBattle();

        pokemon.updateAspects();

        if (battlePokemon.actor.getType().equals(ActorType.PLAYER)) {
            battle.sendUpdate(new AbilityUpdatePacket(battlePokemon::getEffectedPokemon, pokemon.getAbility().getTemplate()));
            battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon));
        }

        for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()) {
            if (!battlePokemon.actor.getType().equals(ActorType.PLAYER)) {
                continue;
            }
            if (activeBattlePokemon.getBattlePokemon() != null &&
                    activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == battlePokemon.getEffectedPokemon().getOwnerPlayer()
                    && activeBattlePokemon.getBattlePokemon() == battlePokemon) {
                battle.sendSidedUpdate(activeBattlePokemon.getActor(),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), battlePokemon, true),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), battlePokemon, false),
                        false);
            }
        }
    }

    public static void scaleDownDynamax(PokemonEntity pokemonEntity) {
        DelayedTicker.add(new DelayedTicker(MegaShowdownConfig.getDynamaxScaleDuration()) {
            @Override
            protected void function() {
                if (!pokemonEntity.isRemoved() && pokemonEntity.hasEffect(MegaShowdownStatusEffects.DYNAMAX)) {
                    pokemonEntity.removeEffect(MegaShowdownStatusEffects.DYNAMAX);
                    pokemonEntity.addEffect(
                            new MobEffectInstance(
                                    MegaShowdownStatusEffects.DYNAMAX,
                                    Integer.MAX_VALUE,
                                    (this.maxAge - this.age),
                                    true,
                                    true,
                                    true
                            ),
                            null
                    );
                } else {
                    this.age = this.maxAge;
                }
                if (this.age == this.maxAge) {
                    pokemonEntity.removeEffect(MobEffects.GLOWING);
                    pokemonEntity.removeEffect(MegaShowdownStatusEffects.DYNAMAX);
                }
            }
        });
    }

    public static void scaleUpDynamax(PokemonEntity pokemonEntity) {
        GlowHandler.applyDynamaxGlow(pokemonEntity);
        DelayedTicker.add(new DelayedTicker(MegaShowdownConfig.getDynamaxScaleDuration()) {
            @Override
            protected void function() {
                if (!pokemonEntity.isRemoved()) {
                    pokemonEntity.addEffect(
                            new MobEffectInstance(
                                    MegaShowdownStatusEffects.DYNAMAX,
                                    Integer.MAX_VALUE,
                                    this.age,
                                    true,
                                    true,
                                    true
                            )
                    );
                } else {
                    this.age = this.maxAge;
                }
            }
        });
    }
}
