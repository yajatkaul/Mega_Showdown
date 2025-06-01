package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.FormChangeData;
import com.cobblemon.yajatkaul.mega_showdown.event.dynamax.DynamaxEventLogic;
import com.cobblemon.yajatkaul.mega_showdown.item.CompiItems;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.configActions.ConfigResults;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.TeraAccessor;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class EventUtils {
    public static void revertFormesEnd(Pokemon pokemon) {
        if (pokemon.getEntity() != null) {
            pokemon.getEntity().removeStatusEffect(StatusEffects.GLOWING);
            DynamaxEventLogic.startGradualScaling(pokemon.getEntity(), 1.0f);
        }
        new StringSpeciesFeature("dynamax_form", "none").apply(pokemon);

        if (pokemon instanceof TeraAccessor pk) {
            pk.setTeraEnabled(false);
        }

        boolean isMega = pokemon.getAspects().stream()
                .anyMatch(aspect -> aspect.startsWith("mega"));

        if ((ShowdownConfig.revertMegas.get() || ShowdownConfig.battleModeOnly.get()) && isMega) {
            MegaLogic.Devolve(pokemon, true);
        }

        if (pokemon.getSpecies().getName().equals("Castform")) {
            new StringSpeciesFeature("forecast_form", "normal").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Aegislash")) {
            new StringSpeciesFeature("stance_forme", "shield").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Greninja") && pokemon.getAspects().contains("ash")) {
            new StringSpeciesFeature("battle_bond", "bond").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Morpeko")) {
            new StringSpeciesFeature("hunger_mode", "full_belly").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Mimikyu")) {
            new StringSpeciesFeature("disguise_form", "disguised").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Ogerpon")) {
            new FlagSpeciesFeature("embody_aspect", false).apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Wishiwashi")) {
            new StringSpeciesFeature("schooling_form", "solo").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Eiscue")) {
            new StringSpeciesFeature("penguin_head", "ice_face").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Cramorant")) {
            new StringSpeciesFeature("missile_form", "none").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Palafin")) {
            new StringSpeciesFeature("dolphin_form", "zero").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Cherrim")) {
            new StringSpeciesFeature("blossom_form", "overcast").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Darmanitan")) {
            new StringSpeciesFeature("blazing_mode", "standard").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Arceus") && pokemon.heldItem().isOf(CompiItems.LEGEND_PLATE)) {
            new StringSpeciesFeature("multitype", "normal").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Xerneas")) {
            new StringSpeciesFeature("life_mode", "neutral").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Necrozma") && pokemon.getAspects().contains("ultra")) {
            if (pokemon.getEntity() != null) {
                ultraAnimation(pokemon.getEntity());
            }
            new FlagSpeciesFeature("ultra", false).apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Terapagos")) {
            new StringSpeciesFeature("tera_form", "normal").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Meloetta")) {
            new StringSpeciesFeature("song_forme", "aria").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Zygarde")) {
            new FlagSpeciesFeature("complete-percent", false).apply(pokemon);
        }
        // HELD ITEM
        else if (pokemon.getSpecies().getName().equals("Palkia")
                && !pokemon.getHeldItem$common().isOf(FormeChangeItems.LUSTROUS_GLOBE)) {
            new StringSpeciesFeature("orb_forme", "altered").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Dialga")
                && !pokemon.getHeldItem$common().isOf(FormeChangeItems.ADAMANT_CRYSTAL)) {
            new StringSpeciesFeature("orb_forme", "altered").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Giratina")
                && !pokemon.getHeldItem$common().isOf(FormeChangeItems.GRISEOUS_CORE)) {
            new StringSpeciesFeature("orb_forme", "altered").apply(pokemon);
        }


        for (FormChangeData forme : Utils.formChangeRegistry) {
            if (forme.battle_mode_only()) {
                if (forme.pokemons().contains(pokemon.getSpecies().getName())) {
                    for (String aspects : forme.default_aspects()) {
                        String[] aspectsDiv = aspects.split("=");
                        if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                            new FlagSpeciesFeature(aspectsDiv[0], Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                        } else {
                            new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                        }
                    }
                    if (pokemon.getEntity() != null) {
                        ConfigResults.particleEffect(pokemon.getEntity(), forme.effects(), false);
                    }
                }
            }
        }
    }

    public static void playFormeChangeAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Yarn mapping for BEACON_ACTIVATE
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverWorld.spawnParticles(
                        ParticleTypes.END_ROD, // Same particle type
                        entityPos.x + xOffset,
                        entityPos.y + yOffset,
                        entityPos.z + zOffset,
                        1, // One particle per call for better spread
                        0, 0, 0, // No movement velocity
                        0.1 // Slight motion
                );
            }
        }
    }

    public static void playFormeChangeAngryAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Yarn mapping for BEACON_ACTIVATE
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (10 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverWorld.spawnParticles(
                        ParticleTypes.ANGRY_VILLAGER, // Same particle type
                        entityPos.x + xOffset,
                        entityPos.y + yOffset,
                        entityPos.z + zOffset,
                        1, // One particle per call for better spread
                        0, 0, 0, // No movement velocity
                        0.1 // Slight motion
                );
            }
        }
    }

    public static void ultraAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 1.2; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityDepth * scaleFactor;

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverWorld.spawnParticles(
                        ParticleTypes.GLOW,
                        entityPos.x + xOffset,
                        entityPos.y + yOffset,
                        entityPos.z + zOffset,
                        1, // One particle per call for better spread
                        0, 0, 0, // No movement velocity
                        0.1 // Slight motion
                );
            }
        }
    }

    public static void playEvolveAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Yarn mapping for BEACON_ACTIVATE
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverWorld.spawnParticles(
                        ParticleTypes.END_ROD, // Same particle type
                        entityPos.x + xOffset,
                        entityPos.y + yOffset,
                        entityPos.z + zOffset,
                        1, // One particle per call for better spread
                        0, 0, 0, // No movement velocity
                        0.1 // Slight motion
                );
            }
        }
    }
}
