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
        context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);

        CompoundTag pokemonPersistentData = context.getPokemon().getPersistentData();
        pokemonPersistentData.putBoolean("form_changing", true);
        pokemonPersistentData.put("apply_aspects", AspectUtils.makeNbt(aspects));

        if (this.particle_apply.isEmpty()) {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
            resetFormChangingState(context, pokemonPersistentData);
            return;
        }

        ResourceLocation particleId = ResourceLocation.tryParse(this.particle_apply.get());
        if (particleId == null) {
            MegaShowdown.LOGGER.error("Invalid snowstorm apply particle");
            return;
        }

        PokemonBehaviourHelper.Companion.snowStormPartileSpawner(context, particleId, this.source_apply.orElse(null), other, this.target_apply.orElse(null));
        applySounds(context, (ServerLevel) context.level());

        this.apply_after.ifPresentOrElse((apply_after) -> {
            context.after(apply_after, () -> {
                AspectUtils.applyAspects(context.getPokemon(), aspects);
                resetFormChangingState(context, pokemonPersistentData);

                this.animations.ifPresent((animation) -> {
                    animation.applyAnimations(context);
                });

                return Unit.INSTANCE;
            });
        }, () -> {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
            resetFormChangingState(context, pokemonPersistentData);

            this.animations.ifPresent((animation) -> {
                animation.applyAnimations(context);
            });
        });

    }

    public void revert(PokemonEntity context, List<String> aspects, PokemonEntity other) {
        context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);

        CompoundTag pokemonPersistentData = context.getPokemon().getPersistentData();
        pokemonPersistentData.putBoolean("form_changing", true);
        pokemonPersistentData.put("apply_aspects", AspectUtils.makeNbt(aspects));

        if (this.particle_revert.isEmpty()) {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
            resetFormChangingState(context, pokemonPersistentData);
            return;
        }

        ResourceLocation particleId = ResourceLocation.tryParse(this.particle_revert.get());
        if (particleId == null) {
            MegaShowdown.LOGGER.error("Invalid snowstorm revert particle");
            return;
        }
        PokemonBehaviourHelper.Companion.snowStormPartileSpawner(context, particleId, this.source_revert.orElse(null), other, this.target_apply.orElse(null));
        revertSounds(context, (ServerLevel) context.level());

        this.revert_after.ifPresentOrElse((revert_after) -> {
            context.after(revert_after, () -> {
                AspectUtils.applyAspects(context.getPokemon(), aspects);
                resetFormChangingState(context, pokemonPersistentData);


                this.animations.ifPresent((animation) -> {
                    animation.revertAnimations(context);
                });

                return Unit.INSTANCE;
            });
        }, () -> {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
            resetFormChangingState(context, pokemonPersistentData);

            this.animations.ifPresent((animation) -> {
                animation.revertAnimations(context);
            });
        });
    }

    public void applyBattle(PokemonEntity context, List<String> aspects, PokemonEntity other, BattlePokemon battlePokemon) {
        context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);

        CompoundTag pokemonPersistentData = context.getPokemon().getPersistentData();
        pokemonPersistentData.putBoolean("form_changing", true);
        pokemonPersistentData.put("apply_aspects", AspectUtils.makeNbt(aspects));

        if (this.particle_apply.isEmpty()) {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
            AspectUtils.updatePackets(battlePokemon);
            resetFormChangingState(context, pokemonPersistentData);
            return;
        }

        ResourceLocation particleId = ResourceLocation.tryParse(this.particle_apply.get());
        if (particleId == null) {
            MegaShowdown.LOGGER.error("Invalid snowstorm apply particle during battle");
            return;
        }

        PokemonBehaviourHelper.Companion.snowStormPartileSpawner(context, particleId, this.source_apply.orElse(null), other, this.target_apply.orElse(null));
        applySounds(context, (ServerLevel) context.level());

        this.apply_after.ifPresentOrElse((apply_after) -> {
            context.after(apply_after, () -> {
                AspectUtils.applyAspects(context.getPokemon(), aspects);
                resetFormChangingState(context, pokemonPersistentData);

                AspectUtils.updatePackets(battlePokemon);

                this.animations.ifPresent((animation) -> {
                    animation.applyAnimations(context);
                });

                return Unit.INSTANCE;
            });
        }, () -> {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
            resetFormChangingState(context, pokemonPersistentData);

            AspectUtils.updatePackets(battlePokemon);

            this.animations.ifPresent((animation) -> {
                animation.applyAnimations(context);
            });
        });

    }

    public void revertBattle(PokemonEntity context, List<String> aspects, PokemonEntity other, BattlePokemon battlePokemon) {
        context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);

        CompoundTag pokemonPersistentData = context.getPokemon().getPersistentData();
        pokemonPersistentData.putBoolean("form_changing", true);
        pokemonPersistentData.put("apply_aspects", AspectUtils.makeNbt(aspects));

        if (this.particle_revert.isEmpty()) {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
            AspectUtils.updatePackets(battlePokemon);
            resetFormChangingState(context, pokemonPersistentData);
            return;
        }

        ResourceLocation particleId = ResourceLocation.tryParse(this.particle_revert.get());
        if (particleId == null) {
            MegaShowdown.LOGGER.error("Invalid snowstorm revert particle during battle");
            return;
        }

        PokemonBehaviourHelper.Companion.snowStormPartileSpawner(context, particleId, this.source_revert.orElse(null), other, this.target_apply.orElse(null));
        revertSounds(context, (ServerLevel) context.level());

        this.revert_after.ifPresentOrElse((revert_after) -> {
            context.after(revert_after, () -> {
                AspectUtils.applyAspects(context.getPokemon(), aspects);
                resetFormChangingState(context, pokemonPersistentData);

                AspectUtils.updatePackets(battlePokemon);

                this.animations.ifPresent((animation) -> {
                    animation.revertAnimations(context);
                });

                return Unit.INSTANCE;
            });
        }, () -> {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
            resetFormChangingState(context, pokemonPersistentData);

            AspectUtils.updatePackets(battlePokemon);

            this.animations.ifPresent((animation) -> {
                animation.revertAnimations(context);
            });
        });
    }

    private void resetFormChangingState(PokemonEntity context, CompoundTag pokemonPersistentData) {
        context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
        pokemonPersistentData.remove("form_changing");
        pokemonPersistentData.remove("apply_aspects");
    }

    public void applySounds(PokemonEntity context, ServerLevel level) {
        this.sound_apply.ifPresent((sound_apply) -> {
            String[] partsSound;
            partsSound = sound_apply.split(":");
            ResourceLocation custom_sound_id = ResourceLocation.fromNamespaceAndPath(partsSound[0], partsSound[1]);
            SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(custom_sound_id);

            Vec3 entityPos = context.position();

            if (soundEvent == null) {
                MegaShowdown.LOGGER.error("Invalid Sound Apply used for pokemon: {}, sound id: {}",
                        context.getPokemon().getSpecies().getName(),
                        sound_apply);
            } else {
                level.playSound(
                        null, entityPos.x, entityPos.y, entityPos.z,
                        soundEvent,
                        SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
                );
            }
        });
    }

    public void revertSounds(PokemonEntity context, ServerLevel level) {
        this.sound_revert.ifPresent((sound_revert) -> {
            String[] partsSound;
            partsSound = sound_revert.split(":");
            ResourceLocation custom_sound_id = ResourceLocation.fromNamespaceAndPath(partsSound[0], partsSound[1]);
            SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(custom_sound_id);

            Vec3 entityPos = context.position();

            if (soundEvent == null) {
                MegaShowdown.LOGGER.error("Invalid Sound Revert used for pokemon: {}, sound id: {}",
                        context.getPokemon().getSpecies().getName(),
                        sound_apply);
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