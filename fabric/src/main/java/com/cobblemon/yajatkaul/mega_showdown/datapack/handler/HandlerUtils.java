package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.FusionData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles.EffectsData;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class HandlerUtils {
    public static boolean checkEnabled(FusionData fusion, Pokemon pk) {
        for (String aspects : fusion.fusion_aspects()) {
            String[] aspectsDiv = aspects.split("=");
            if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                if (!pk.getAspects().contains(aspectsDiv[0])) return false;
            } else {
                for (String aspect : pk.getAspects()) {
                    if (!aspect.startsWith(aspectsDiv[1])) return false;
                }
            }
        }
        return true;
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

    public static void applyEffects(EffectsData effects, PokemonEntity pokemon, List<String> aspects, boolean apply){
        if(apply){
            if (effects.snowStorm() != null && effects.minecraft() != null) {
                HandlerUtils.particleEffect(pokemon, effects, true);
                HandlerUtils.snowStromParticleEffect(pokemon, effects, true, aspects);
            } else if(effects.minecraft() != null){
                HandlerUtils.particleEffect(pokemon, effects, true);
                HandlerUtils.applyAspects(aspects, pokemon.getPokemon());
            } else if (effects.snowStorm() != null) {
                HandlerUtils.snowStromParticleEffect(pokemon, effects, true, aspects);
            }
        }else {
            if (effects.snowStorm() != null && effects.minecraft() != null) {
                HandlerUtils.particleEffect(pokemon, effects, false);
                HandlerUtils.snowStromParticleEffect(pokemon, effects, false, aspects);
            } else if(effects.minecraft() != null){
                HandlerUtils.particleEffect(pokemon, effects, false);
                HandlerUtils.applyAspects(aspects, pokemon.getPokemon());
            } else if (effects.snowStorm() != null) {
                HandlerUtils.snowStromParticleEffect(pokemon, effects, false, aspects);
            }
        }
    }

    private static void particleEffect(PokemonEntity context, EffectsData effects, boolean apply) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            int amplifier = apply ? effects.minecraft().particle_apply_amplifier() : effects.minecraft().particle_revert_amplifier();

            String[] partsParticle;
            String[] partsSound;

            if (apply) {
                partsParticle = effects.minecraft().particle_apply().split(":");
                partsSound = effects.minecraft().sound_apply().split(":");
            } else {
                partsParticle = effects.minecraft().particle_revert().split(":");
                partsSound = effects.minecraft().sound_revert().split(":");
            }

            Identifier custom_particle_id = Identifier.of(partsParticle[0], partsParticle[1]);
            ParticleType<?> particleType = Registries.PARTICLE_TYPE.get(custom_particle_id);

            Identifier custom_sound_id = Identifier.of(partsSound[0], partsSound[1]);
            SoundEvent soundEvent = Registries.SOUND_EVENT.get(custom_sound_id);

            Vec3d entityPos = context.getPos();

            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            double scaleFactor = apply ? effects.minecraft().particle_apply_amplifier() : effects.minecraft().particle_revert_amplifier();
            double ampFactor = Math.max(1, amplifier); // Ensure amplifier is at least 1

            double adjustedWidth = entityWidth * scaleFactor * ampFactor;
            double adjustedHeight = entityHeight * scaleFactor * ampFactor;
            double adjustedDepth = entityWidth * scaleFactor * ampFactor;

            if (soundEvent == null) {
                if (apply && !effects.minecraft().sound_apply().isEmpty()) {
                    MegaShowdown.LOGGER.error("A: Invalid Sound used for pokemon: {}, sound id: {}",
                            context.getPokemon().getSpecies().getName(),
                            effects.minecraft().sound_apply());
                } else if (!apply && !effects.minecraft().sound_revert().isEmpty()) {
                    MegaShowdown.LOGGER.error("R: Invalid Sound used for pokemon: {}, sound id: {}",
                            context.getPokemon().getSpecies().getName(),
                            effects.minecraft().sound_revert());
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
                if (apply && !effects.minecraft().particle_apply().isEmpty()) {
                    MegaShowdown.LOGGER.error("A: Invalid Particle used for pokemon: {}, sound id: {}",
                            context.getPokemon().getSpecies().getName(),
                            effects.minecraft().particle_apply());
                } else if (!apply && !effects.minecraft().particle_revert().isEmpty()) {
                    MegaShowdown.LOGGER.error("R: Invalid Particle used for pokemon: {}, sound id: {}",
                            context.getPokemon().getSpecies().getName(),
                            effects.minecraft().particle_revert());
                }
            }
        }
    }

    private static void snowStromParticleEffect(PokemonEntity context, EffectsData effects, boolean apply, List<String> aspects) {
        if (apply) {
            context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);
            SnowStormHandler.Companion.snowStormPartileSpawner(context, effects.snowStorm().particle_apply(), effects.snowStorm().locator_apply());
            context.after(effects.snowStorm().apply_after(), () -> {
                HandlerUtils.applyAspects(aspects, context.getPokemon());
                context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                return Unit.INSTANCE;
            });
        } else {
            context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);
            SnowStormHandler.Companion.snowStormPartileSpawner(context, effects.snowStorm().particle_revert(), effects.snowStorm().locator_revert());
            context.after(effects.snowStorm().revert_after(), () -> {
                HandlerUtils.applyAspects(aspects, context.getPokemon());
                context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                return Unit.INSTANCE;
            });
        }
    }

    public static boolean itemValidator(Item item, Integer custom_model_data, ItemStack itemStack) {
        CustomModelDataComponent nbt = itemStack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
        return itemStack.isOf(item) && ((nbt != null && custom_model_data == nbt.value()) || custom_model_data == 0);
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
}
