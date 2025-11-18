package com.github.yajatkaul.mega_showdown.utils.particles;

import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import com.github.yajatkaul.mega_showdown.utils.PokemonBehaviourHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Unit;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public record SnowStormParticle(
        Optional<List<String>> source_apply,
        Optional<List<String>> target_apply,
        Optional<List<String>> source_revert,
        Optional<List<String>> target_revert,
        Optional<String> particle_apply,
        Optional<Float> apply_after,
        Optional<String> particle_revert,
        Optional<Float> revert_after,
        Optional<String> sound_apply,
        Optional<String> sound_revert,
        Optional<AnimationData> animations
) {
    public static final Codec<SnowStormParticle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Codec.STRING).optionalFieldOf("source_apply").forGetter(SnowStormParticle::source_apply),
            Codec.list(Codec.STRING).optionalFieldOf("target_apply").forGetter(SnowStormParticle::target_apply),
            Codec.list(Codec.STRING).optionalFieldOf("source_revert").forGetter(SnowStormParticle::source_revert),
            Codec.list(Codec.STRING).optionalFieldOf("target_revert").forGetter(SnowStormParticle::target_revert),
            Codec.STRING.optionalFieldOf("particle_apply").forGetter(SnowStormParticle::particle_apply),
            Codec.FLOAT.optionalFieldOf("apply_after").forGetter(SnowStormParticle::apply_after),
            Codec.STRING.optionalFieldOf("particle_revert").forGetter(SnowStormParticle::particle_revert),
            Codec.FLOAT.optionalFieldOf("revert_after").forGetter(SnowStormParticle::revert_after),
            Codec.STRING.optionalFieldOf("sound_apply").forGetter(SnowStormParticle::sound_apply),
            Codec.STRING.optionalFieldOf("sound_revert").forGetter(SnowStormParticle::sound_revert),
            AnimationData.CODEC.optionalFieldOf("animations").forGetter(SnowStormParticle::animations)
    ).apply(instance, SnowStormParticle::new));

    public void apply(PokemonEntity context, List<String> aspects, PokemonEntity other) {
        processTransformation(context, aspects, other, null, true);
    }

    public void revert(PokemonEntity context, List<String> aspects, PokemonEntity other) {
        processTransformation(context, aspects, other, null, false);
    }

    public void applyBattle(PokemonEntity context, List<String> aspects, PokemonEntity other, BattlePokemon battlePokemon) {
        processTransformation(context, aspects, other, battlePokemon, true);
    }

    public void revertBattle(PokemonEntity context, List<String> aspects, PokemonEntity other, BattlePokemon battlePokemon) {
        processTransformation(context, aspects, other, battlePokemon, false);
    }

    private void processTransformation(PokemonEntity context, List<String> aspects, PokemonEntity other,
                                       BattlePokemon battlePokemon, boolean isApply) {
        context.setNoAi(true);

        CompoundTag pokemonPersistentData = context.getPokemon().getPersistentData();
        pokemonPersistentData.putBoolean("form_changing", true);
        pokemonPersistentData.put("apply_aspects", AspectUtils.makeNbt(aspects));

        Optional<String> particle = isApply ? particle_apply : particle_revert;
        Optional<List<String>> sourceParticles = isApply ? source_apply : source_revert;
        Optional<List<String>> targetParticles = isApply ? target_apply : target_revert;
        Optional<Float> delay = isApply ? apply_after : revert_after;

        if (particle.isEmpty()) {
            applyAspectsAndCleanup(context, aspects, pokemonPersistentData, battlePokemon, isApply);
            return;
        }

        ResourceLocation particleId = ResourceLocation.tryParse(particle.get());
        if (particleId == null) {
            MegaShowdown.LOGGER.error("Invalid snowstorm {} particle{}",
                    isApply ? "apply" : "revert",
                    battlePokemon != null ? " during battle" : "");
            return;
        }

        PokemonBehaviourHelper.Companion.snowStormPartileSpawner(
                context, particleId, sourceParticles.orElse(null), other, targetParticles.orElse(null)
        );
        playSound(context, (ServerLevel) context.level(), isApply);

        delay.ifPresentOrElse(
                delayValue -> context.after(delayValue, () -> {
                    applyAspectsAndCleanup(context, aspects, pokemonPersistentData, battlePokemon, isApply);
                    return Unit.INSTANCE;
                }),
                () -> applyAspectsAndCleanup(context, aspects, pokemonPersistentData, battlePokemon, isApply)
        );
    }

    private void applyAspectsAndCleanup(PokemonEntity context, List<String> aspects,
                                        CompoundTag pokemonPersistentData, BattlePokemon battlePokemon,
                                        boolean isApply) {
        AspectUtils.applyAspects(context.getPokemon(), aspects);

        if (battlePokemon != null) {
            AspectUtils.updatePackets(battlePokemon);
        }

        resetFormChangingState(context, pokemonPersistentData);

        animations.ifPresent(animation -> {
            if (isApply) {
                animation.applyAnimations(context);
            } else {
                animation.revertAnimations(context);
            }
        });
    }

    private void resetFormChangingState(PokemonEntity context, CompoundTag pokemonPersistentData) {
        context.setNoAi(false);
        pokemonPersistentData.remove("form_changing");
        pokemonPersistentData.remove("apply_aspects");
    }

    private void playSound(PokemonEntity context, ServerLevel level, boolean isApply) {
        Optional<String> sound = isApply ? sound_apply : sound_revert;
        String soundType = isApply ? "Apply" : "Revert";

        sound.ifPresent(soundId -> {
            String[] parts = soundId.split(":");
            if (parts.length != 2) {
                MegaShowdown.LOGGER.error("Invalid Sound {} format for pokemon: {}, sound id: {}",
                        soundType, context.getPokemon().getSpecies().getName(), soundId);
                return;
            }

            ResourceLocation soundResourceId = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
            SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(soundResourceId);
            Vec3 entityPos = context.position();

            if (soundEvent == null) {
                MegaShowdown.LOGGER.error("Invalid Sound {} used for pokemon: {}, sound id: {}",
                        soundType, context.getPokemon().getSpecies().getName(), soundId);
            } else {
                level.playSound(
                        null, entityPos.x, entityPos.y, entityPos.z,
                        soundEvent,
                        SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
                );
            }
        });
    }
}