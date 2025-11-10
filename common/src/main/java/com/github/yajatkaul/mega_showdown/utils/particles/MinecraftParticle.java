package com.github.yajatkaul.mega_showdown.utils.particles;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public record MinecraftParticle(
        Optional<String> particle_apply,
        Optional<String> particle_revert,
        Optional<String> sound_apply,
        Optional<String> sound_revert,
        Optional<Integer> particle_apply_amplifier,
        Optional<Integer> particle_revert_amplifier,
        Optional<AnimationData> animations
) {
    public static final Codec<MinecraftParticle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("particle_apply").forGetter(MinecraftParticle::particle_apply),
            Codec.STRING.optionalFieldOf("particle_revert").forGetter(MinecraftParticle::particle_revert),
            Codec.STRING.optionalFieldOf("sound_apply").forGetter(MinecraftParticle::sound_apply),
            Codec.STRING.optionalFieldOf("sound_revert").forGetter(MinecraftParticle::sound_revert),
            Codec.INT.optionalFieldOf("particle_apply_amplifier").forGetter(MinecraftParticle::particle_apply_amplifier),
            Codec.INT.optionalFieldOf("particle_revert_amplifier").forGetter(MinecraftParticle::particle_revert_amplifier),
            AnimationData.CODEC.optionalFieldOf("animations").forGetter(MinecraftParticle::animations)
    ).apply(instance, MinecraftParticle::new));

    public void apply(PokemonEntity context) {
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position();

            this.animations.ifPresent((animation) -> {
                animation.applyAnimations(context);
            });

            this.sound_apply.ifPresent((sound_apply) -> {
                String[] partsSound;
                partsSound = sound_apply.split(":");
                ResourceLocation custom_sound_id = ResourceLocation.fromNamespaceAndPath(partsSound[0], partsSound[1]);
                SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(custom_sound_id);

                if (soundEvent == null) {
                    MegaShowdown.LOGGER.error("Invalid Sound Apply used for pokemon: {}, sound id: {}",
                            context.getPokemon().getSpecies().getName(),
                            sound_apply);
                } else {
                    serverLevel.playSound(
                            null, entityPos.x, entityPos.y, entityPos.z,
                            soundEvent,
                            SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
                    );
                }
            });

            this.particle_apply.ifPresent((particle_apply) -> {
                String[] partsParticle;
                partsParticle = particle_apply.split(":");

                ResourceLocation custom_particle_id = ResourceLocation.fromNamespaceAndPath(partsParticle[0], partsParticle[1]);
                ParticleType<?> particleType = BuiltInRegistries.PARTICLE_TYPE.get(custom_particle_id);

                double entityWidth = context.getBbWidth();
                double entityHeight = context.getBbHeight();

                int baseParticleCount = (int) (100 * entityWidth * entityHeight);
                int amplifier = 1;

                if (this.particle_apply_amplifier.isPresent()) {
                    amplifier = Math.max(1, this.particle_apply_amplifier.get());
                }

                int particleCount = baseParticleCount * amplifier; // Amplify particle count
                double radius = entityWidth * (0.8 + amplifier * 0.1); // Slightly increase radius with amplifier

                if (particleType instanceof ParticleOptions particle) {
                    for (int i = 0; i < particleCount; i++) {
                        double angle = Math.random() * 2 * Math.PI;
                        double xOffset = Math.cos(angle) * radius;
                        double zOffset = Math.sin(angle) * radius;
                        double yOffset = Math.random() * entityHeight;

                        serverLevel.sendParticles(
                                particle,
                                entityPos.x + xOffset,
                                entityPos.y + yOffset,
                                entityPos.z + zOffset,
                                1,
                                0, 0, 0,
                                0.1
                        );
                    }
                } else {
                    MegaShowdown.LOGGER.error("Invalid Particle Apply used for pokemon: {}, particle id: {}",
                            context.getPokemon().getSpecies().getName(),
                            particle_apply);
                }
            });
        }
    }

    public void revert(PokemonEntity context) {
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position();

            this.animations.ifPresent((animation) -> {
                animation.revertAnimations(context);
            });

            this.sound_revert.ifPresent((sound_revert) -> {
                String[] partsSound;
                partsSound = sound_revert.split(":");
                ResourceLocation custom_sound_id = ResourceLocation.fromNamespaceAndPath(partsSound[0], partsSound[1]);
                SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(custom_sound_id);

                if (soundEvent == null) {
                    MegaShowdown.LOGGER.error("Invalid Sound Revert used for pokemon: {}, sound id: {}",
                            context.getPokemon().getSpecies().getName(),
                            sound_revert);
                } else {
                    serverLevel.playSound(
                            null, entityPos.x, entityPos.y, entityPos.z,
                            soundEvent,
                            SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
                    );
                }
            });

            this.particle_revert.ifPresent((particle_revert) -> {
                String[] partsParticle;
                partsParticle = particle_revert.split(":");

                ResourceLocation custom_particle_id = ResourceLocation.fromNamespaceAndPath(partsParticle[0], partsParticle[1]);
                ParticleType<?> particleType = BuiltInRegistries.PARTICLE_TYPE.get(custom_particle_id);

                double entityWidth = context.getBbWidth();
                double entityHeight = context.getBbHeight();

                int baseParticleCount = (int) (100 * entityWidth * entityHeight);
                int amplifier = 1;

                if (this.particle_revert_amplifier.isPresent()) {
                    amplifier = Math.max(1, this.particle_revert_amplifier.get());
                }

                int particleCount = baseParticleCount * amplifier; // Amplify particle count
                double radius = entityWidth * (0.8 + amplifier * 0.1); // Slightly increase radius with amplifier

                if (particleType instanceof ParticleOptions particle) {
                    for (int i = 0; i < particleCount; i++) {
                        double angle = Math.random() * 2 * Math.PI;
                        double xOffset = Math.cos(angle) * radius;
                        double zOffset = Math.sin(angle) * radius;
                        double yOffset = Math.random() * entityHeight;

                        serverLevel.sendParticles(
                                particle,
                                entityPos.x + xOffset,
                                entityPos.y + yOffset,
                                entityPos.z + zOffset,
                                1,
                                0, 0, 0,
                                0.1
                        );
                    }
                } else {
                    MegaShowdown.LOGGER.error("Invalid Particle Revert used for pokemon: {}, particle id: {}",
                            context.getPokemon().getSpecies().getName(),
                            particle_revert);
                }
            });
        }
    }
}