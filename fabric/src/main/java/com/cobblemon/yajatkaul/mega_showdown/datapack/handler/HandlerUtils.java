package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.EffectsData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.FusionData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;

public class HandlerUtils {
    static boolean checkEnabled(FusionData fusion, Pokemon pk) {
        for (String aspects : fusion.fusion_aspects()) {
            String[] aspectsDiv = aspects.split("=");
            if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                if (pk.getAspects().contains(aspectsDiv[0])) return true;
            } else {
                for (String aspect : pk.getAspects()) {
                    if (aspect.startsWith(aspectsDiv[1])) return true;
                }
            }
        }
        return false;
    }

    public static EntityHitResult getEntityLookingAt(PlayerEntity player, double distance) {
        Vec3d eyePos = player.getEyePos();
        Vec3d lookVec = player.getRotationVec(1.0F);
        Vec3d targetPos = eyePos.add(lookVec.multiply(distance));

        return ProjectileUtil.raycast(
                player,
                eyePos,
                targetPos,
                player.getBoundingBox().stretch(lookVec.multiply(distance)).expand(1.0, 1.0, 1.0),
                entity -> !entity.isSpectator() && entity.canHit() && entity instanceof LivingEntity,
                distance * distance
        );
    }

    public static void particleEffect(LivingEntity context, EffectsData effects, boolean apply) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            int amplifier = apply ? effects.particle_apply_amplifier() : effects.particle_revert_amplifier();

            String[] partsParticle;
            String[] partsSound;

            if (apply) {
                partsParticle = effects.particle_apply().split(":");
                partsSound = effects.sound_apply().split(":");
            } else {
                partsParticle = effects.particle_revert().split(":");
                partsSound = effects.sound_revert().split(":");
            }

            Identifier custom_particle_id = Identifier.of(partsParticle[0], partsParticle[1]);
            ParticleType<?> particleType = Registries.PARTICLE_TYPE.get(custom_particle_id);

            Identifier custom_sound_id = Identifier.of(partsSound[0], partsSound[1]);
            SoundEvent soundEvent = Registries.SOUND_EVENT.get(custom_sound_id);

            Vec3d entityPos = context.getPos();

            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            double scaleFactor = apply ? effects.particle_apply_amplifier() : effects.particle_revert_amplifier();
            double ampFactor = Math.max(1, amplifier); // Ensure amplifier is at least 1

            double adjustedWidth = entityWidth * scaleFactor * ampFactor;
            double adjustedHeight = entityHeight * scaleFactor * ampFactor;
            double adjustedDepth = entityWidth * scaleFactor * ampFactor;

            if (soundEvent == null) {
                if (apply && !effects.sound_apply().isEmpty()) {
                    MegaShowdown.LOGGER.error("A: Invalid Sound used for pokemon: {}, sound id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.sound_apply());
                } else if (!apply && !effects.sound_revert().isEmpty()) {
                    MegaShowdown.LOGGER.error("R: Invalid Sound used for pokemon: {}, sound id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.sound_revert());
                }
            } else {
                serverWorld.playSound(
                        null, entityPos.x, entityPos.y, entityPos.z,
                        soundEvent,
                        SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
                );
            }

            int particleCount = (int) (175 * adjustedWidth * adjustedHeight);

            if (particleType instanceof ParticleEffect particle) {
                for (int i = 0; i < particleCount; i++) {
                    double xOffset = (Math.random() - 0.5) * adjustedWidth;
                    double yOffset = Math.random() * adjustedHeight;
                    double zOffset = (Math.random() - 0.5) * adjustedDepth;

                    serverWorld.spawnParticles(
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
                if (apply && !effects.particle_apply().isEmpty()) {
                    MegaShowdown.LOGGER.error("A: Invalid Particle used for pokemon: {}, sound id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.particle_apply());
                } else if (!apply && !effects.particle_revert().isEmpty()) {
                    MegaShowdown.LOGGER.error("R: Invalid Particle used for pokemon: {}, sound id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.particle_revert());
                }
            }
        }
    }

}
