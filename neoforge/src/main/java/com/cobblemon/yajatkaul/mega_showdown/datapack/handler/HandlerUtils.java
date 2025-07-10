package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles.EffectsData;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;

public class HandlerUtils {
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
                SnowStormHandler.Companion.playAnimation(context,new HashSet<>(effects.minecraft().animations().animations_apply()), effects.minecraft().animations().expressions_apply());
            } else {
                partsParticle = effects.minecraft().particle_revert().split(":");
                partsSound = effects.minecraft().sound_revert().split(":");
                SnowStormHandler.Companion.playAnimation(context,new HashSet<>(effects.minecraft().animations().animations_revert()), effects.minecraft().animations().expressions_revert());
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

    public static boolean itemValidator(Item item, Integer custom_model_data, ItemStack itemStack) {
        CustomModelData nbt = itemStack.get(DataComponents.CUSTOM_MODEL_DATA);
        return itemStack.is(item) && ((nbt != null && custom_model_data == nbt.value()) || custom_model_data == 0);
    }

    public static void applyEffects(EffectsData effects, PokemonEntity pokemon, List<String> aspects, boolean apply) {
        if (apply) {
            if (effects.snowStorm() != null && effects.minecraft() != null) {
                HandlerUtils.particleEffect(pokemon, effects, true);
                HandlerUtils.snowStromParticleEffect(pokemon, effects, true, aspects);
            } else if (effects.minecraft() != null) {
                HandlerUtils.particleEffect(pokemon, effects, true);
                HandlerUtils.applyAspects(aspects, pokemon.getPokemon());
            } else if (effects.snowStorm() != null) {
                HandlerUtils.snowStromParticleEffect(pokemon, effects, true, aspects);
            }
        } else {
            if (effects.snowStorm() != null && effects.minecraft() != null) {
                HandlerUtils.particleEffect(pokemon, effects, false);
                HandlerUtils.snowStromParticleEffect(pokemon, effects, false, aspects);
            } else if (effects.minecraft() != null) {
                HandlerUtils.particleEffect(pokemon, effects, false);
                HandlerUtils.applyAspects(aspects, pokemon.getPokemon());
            } else if (effects.snowStorm() != null) {
                HandlerUtils.snowStromParticleEffect(pokemon, effects, false, aspects);
            }
        }
    }

    private static void snowStromParticleEffect(PokemonEntity context, EffectsData effects, boolean apply, List<String> aspects) {
        if (apply) {
            context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);
            ResourceLocation particleId = ResourceLocation.tryParse(effects.snowStorm().particle_apply());
            if(particleId == null){
                MegaShowdown.LOGGER.error("Invalid snowstorm apply particle");
                return;
            }
            SnowStormHandler.Companion.snowStormPartileSpawner(context, particleId, effects.snowStorm().source_apply());
            context.after(effects.snowStorm().apply_after(), () -> {
                HandlerUtils.applyAspects(aspects, context.getPokemon());
                context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                SnowStormHandler.Companion.playAnimation(context,new HashSet<>(effects.snowStorm().animations().animations_apply()), effects.snowStorm().animations().expressions_apply());
                return Unit.INSTANCE;
            });
        } else {
            context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);
            ResourceLocation particleId = ResourceLocation.tryParse(effects.snowStorm().particle_revert());
            if(particleId == null){
                MegaShowdown.LOGGER.error("Invalid snowstorm revert particle");
                return;
            }
            SnowStormHandler.Companion.snowStormPartileSpawner(context, particleId, effects.snowStorm().source_revert());
            context.after(effects.snowStorm().revert_after(), () -> {
                HandlerUtils.applyAspects(aspects, context.getPokemon());
                context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                SnowStormHandler.Companion.playAnimation(context,new HashSet<>(effects.snowStorm().animations().animations_revert()), effects.snowStorm().animations().expressions_revert());
                return Unit.INSTANCE;
            });
        }
    }

    public static void applyAspects(List<String> aspects, Pokemon pokemon) {
        for (String aspect : aspects) {
            String[] div = aspect.split("=");
            if (div[1].equals("true") || div[1].equals("false")) {
                new FlagSpeciesFeature(div[0], Boolean.parseBoolean(div[1])).apply(pokemon);
            } else {
                new StringSpeciesFeature(div[0], div[1]).apply(pokemon);
            }
        }
    }

    //TARGET
    public static void applyEffects(EffectsData effects, PokemonEntity pokemon, List<String> aspects, boolean apply, PokemonEntity other) {
        if (apply) {
            if (effects.snowStorm() != null && effects.minecraft() != null) {
                HandlerUtils.particleEffect(pokemon, effects, true);
                HandlerUtils.snowStromParticleEffect(pokemon, effects, true, aspects, other);
            } else if (effects.minecraft() != null) {
                HandlerUtils.particleEffect(pokemon, effects, true);
                HandlerUtils.applyAspects(aspects, pokemon.getPokemon());
            } else if (effects.snowStorm() != null) {
                HandlerUtils.snowStromParticleEffect(pokemon, effects, true, aspects, other);
            }
        } else {
            if (effects.snowStorm() != null && effects.minecraft() != null) {
                HandlerUtils.particleEffect(pokemon, effects, false);
                HandlerUtils.snowStromParticleEffect(pokemon, effects, false, aspects, other);
            } else if (effects.minecraft() != null) {
                HandlerUtils.particleEffect(pokemon, effects, false);
                HandlerUtils.applyAspects(aspects, pokemon.getPokemon());
            } else if (effects.snowStorm() != null) {
                HandlerUtils.snowStromParticleEffect(pokemon, effects, false, aspects, other);
            }
        }
    }

    private static void snowStromParticleEffect(PokemonEntity context, EffectsData effects, boolean apply, List<String> aspects, PokemonEntity other) {
        if (apply) {
            context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);
            ResourceLocation particleId = ResourceLocation.tryParse(effects.snowStorm().particle_apply());
            if(particleId == null){
                MegaShowdown.LOGGER.error("Invalid snowstorm apply particle");
                return;
            }
            SnowStormHandler.Companion.snowStormPartileSpawner(context, particleId, effects.snowStorm().source_apply(), other, effects.snowStorm().target_apply());
            context.after(effects.snowStorm().apply_after(), () -> {
                HandlerUtils.applyAspects(aspects, context.getPokemon());
                context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                SnowStormHandler.Companion.playAnimation(context,new HashSet<>(effects.snowStorm().animations().animations_apply()), effects.snowStorm().animations().expressions_apply());
                return Unit.INSTANCE;
            });
        } else {
            context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);
            ResourceLocation particleId = ResourceLocation.tryParse(effects.snowStorm().particle_revert());
            if(particleId == null){
                MegaShowdown.LOGGER.error("Invalid snowstorm revert particle");
                return;
            }
            SnowStormHandler.Companion.snowStormPartileSpawner(context, particleId, effects.snowStorm().source_revert(), other, effects.snowStorm().target_apply());
            context.after(effects.snowStorm().revert_after(), () -> {
                HandlerUtils.applyAspects(aspects, context.getPokemon());
                context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                SnowStormHandler.Companion.playAnimation(context,new HashSet<>(effects.snowStorm().animations().animations_revert()), effects.snowStorm().animations().expressions_revert());
                return Unit.INSTANCE;
            });
        }
    }
}
