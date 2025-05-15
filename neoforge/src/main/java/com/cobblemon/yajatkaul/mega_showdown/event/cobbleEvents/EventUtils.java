package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.config.Config;
import com.cobblemon.yajatkaul.mega_showdown.config.structure.CustomConfig;
import com.cobblemon.yajatkaul.mega_showdown.config.structure.FormeChange;
import com.cobblemon.yajatkaul.mega_showdown.event.dynamax.DynamaxEventListener;
import com.cobblemon.yajatkaul.mega_showdown.item.CompiItems;
import com.cobblemon.yajatkaul.mega_showdown.item.configActions.ConfigResults;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.TeraAccessor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class EventUtils {
    public static void revertFormesEnd(Pokemon pokemon){
        if(pokemon.getEntity() != null){
            pokemon.getEntity().removeEffect(MobEffects.GLOWING);
            DynamaxEventListener.startGradualScaling(pokemon.getEntity(), 1.0f);
        }

        if(pokemon instanceof TeraAccessor pk){
            pk.setTeraEnabled(false);
        }

        if(Config.revertMegas && !Config.multipleMegas && (pokemon.getAspects().contains("mega_x") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega"))){
            MegaLogic.Devolve(pokemon, true);
        }

        new StringSpeciesFeature("dynamax_form", "none").apply(pokemon);

        if(pokemon.getSpecies().getName().equals("Castform")){
            new StringSpeciesFeature("forecast_form", "normal").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Aegislash")) {
            new StringSpeciesFeature("stance_forme", "shield").apply(pokemon);
        } else if(pokemon.getSpecies().getName().equals("Greninja") && pokemon.getAspects().contains("ash")){
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
        } else if (pokemon.getSpecies().getName().equals("Arceus") && pokemon.heldItem().is(CompiItems.LEGEND_PLATE)){
            new StringSpeciesFeature("multitype", "normal").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Xerneas")) {
            new StringSpeciesFeature("life_mode", "neutral").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Necrozma") && pokemon.getAspects().contains("ultra")) {
            if(pokemon.getEntity() != null){
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

        for(FormeChange forme: CustomConfig.formeChange){
            if(forme.battle_mode_only){
                if(forme.pokemons.contains(pokemon.getSpecies().getName())){
                    for(String aspects: forme.default_aspects){
                        String[] aspectsDiv = aspects.split("=");
                        if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                            new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                        }else{
                            new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                        }
                    }
                    ConfigResults.particleEffect(pokemon.getEntity(), forme.effects, false);
                }
            }
        }
    }

    public static void playFormeChangeAnimation(LivingEntity context) {
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverLevel.sendParticles(
                        ParticleTypes.END_ROD, // Change this to any particle type
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
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (10 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverLevel.sendParticles(
                        ParticleTypes.ANGRY_VILLAGER, // Change this to any particle type
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
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 1.2; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityWidth * scaleFactor;

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverLevel.sendParticles(
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
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverLevel.sendParticles(
                        ParticleTypes.END_ROD, // Change this to any particle type
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
