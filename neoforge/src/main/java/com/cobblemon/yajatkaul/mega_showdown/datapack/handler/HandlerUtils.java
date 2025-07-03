package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles.EffectsData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.FusionData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class HandlerUtils {
    public static boolean checkEnabled(FusionData fusion, Pokemon pk) {
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

    public static EntityHitResult getEntityLookingAt(Player player, float distance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 targetPos = eyePos.add(lookVec.scale(distance));

        AABB rayTraceBox = new AABB(eyePos, targetPos);

        return ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                eyePos,
                targetPos,
                rayTraceBox, // Use the ray trace box directly
                entity -> !entity.isSpectator() && entity instanceof LivingEntity && entity.isPickable(),
                0.3f // Smaller collision expansion value for more precise detection
        );
    }

    public static void particleEffect(LivingEntity context, EffectsData effects, boolean apply) {
        int amplifier = apply ? effects.minecraft().particle_apply_amplifier() : effects.minecraft().particle_revert_amplifier();

        if (context.level() instanceof ServerLevel serverLevel) {
            String[] partsParticle;
            String[] partsSound;

            if (apply) {
                partsParticle = effects.minecraft().particle_apply().split(":");
                partsSound = effects.minecraft().sound_apply().split(":");
            } else {
                partsParticle = effects.minecraft().particle_revert().split(":");
                partsSound = effects.minecraft().sound_revert().split(":");
            }

            ResourceLocation custom_particle_id = ResourceLocation.fromNamespaceAndPath(partsParticle[0], partsParticle[1]);
            ParticleType<?> particleType = BuiltInRegistries.PARTICLE_TYPE.get(custom_particle_id);

            ResourceLocation custom_sound_id = ResourceLocation.fromNamespaceAndPath(partsSound[0], partsSound[1]);
            SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(custom_sound_id);

            Vec3 entityPos = context.position();

            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            if (soundEvent == null) {
                if (apply && !effects.minecraft().sound_apply().isEmpty()) {
                    MegaShowdown.LOGGER.error("A: Invalid Sound used for pokemon: {}, sound id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.minecraft().sound_apply());
                } else if (!apply && !effects.minecraft().sound_revert().isEmpty()) {
                    MegaShowdown.LOGGER.error("R: Invalid Sound used for pokemon: {}, sound id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.minecraft().sound_revert());
                }
            } else {
                serverLevel.playSound(
                        null, entityPos.x, entityPos.y, entityPos.z,
                        soundEvent,
                        SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
                );
            }

            int baseParticleCount = (int) (100 * entityWidth * entityHeight);
            int particleCount = baseParticleCount * Math.max(1, amplifier); // Amplify particle count
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
                if (apply && !effects.minecraft().particle_apply().isEmpty()) {
                    MegaShowdown.LOGGER.error("A: Invalid Particle used for pokemon: {}, particle id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.minecraft().particle_apply());
                } else if (!apply && !effects.minecraft().particle_revert().isEmpty()) {
                    MegaShowdown.LOGGER.error("R: Invalid Particle used for pokemon: {}, particle id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.minecraft().particle_revert());
                }
            }
        }
    }
}
