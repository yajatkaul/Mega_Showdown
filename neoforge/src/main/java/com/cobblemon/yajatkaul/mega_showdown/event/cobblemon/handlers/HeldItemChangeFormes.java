package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.EventUtils;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.FormChangeHelper;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.UltraLogic;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange.ArceusType;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange.Drives;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange.Memory;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove.ElementalZCrystal;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Set;

public class HeldItemChangeFormes {
    public static void genesectChange(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();
        if (pokemon.getSpecies().getName().equals("Genesect")) {
            if (event.getReceiving().is(FormeChangeItems.DOUSE_DRIVE)) {
                SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
                new StringSpeciesFeature("techno_drive", "water").apply(pokemon);
            } else if (event.getReceiving().is(FormeChangeItems.BURN_DRIVE)) {
                SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
                new StringSpeciesFeature("techno_drive", "fire").apply(pokemon);
            } else if (event.getReceiving().is(FormeChangeItems.CHILL_DRIVE)) {
                SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
                new StringSpeciesFeature("techno_drive", "ice").apply(pokemon);
            } else if (event.getReceiving().is(FormeChangeItems.SHOCK_DRIVE)) {
                SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
                new StringSpeciesFeature("techno_drive", "electric").apply(pokemon);
            } else if (!(event.getReceiving().getItem() instanceof Drives) && event.getReturning().getItem() instanceof Drives) {
                SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
                new StringSpeciesFeature("techno_drive", "none").apply(pokemon);
            }
        }
    }

    public static void silvallyChange(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();
        if (pokemon.getSpecies().getName().equals("Silvally")) {
            if (event.getReceiving().getItem() instanceof Memory memory) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory", memory.getType()).apply(pokemon);
            } else if (event.getReturning().getItem() instanceof Memory) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory", "normal").apply(pokemon);
            }
        }
    }

    public static void arcuesChange(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();
        PokemonEntity pokemonEntity = pokemon.getEntity();
        BlockPos entityPos = pokemonEntity.getOnPos();

        if (pokemon.getSpecies().getName().equals("Arceus")) {
            if (event.getReceiving().getItem() instanceof ArceusType plate) {
                pokemonEntity.level().playSound(
                        null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                        ModSounds.ARCEUS_MULTITYPE.get(),
                        SoundSource.PLAYERS, 0.2f, 1.3f
                );

                SnowStormHandler.Companion.snowStormPartileSpawner(pokemon.getEntity(),
                        ResourceLocation.tryParse("cobblemon:arceus_" + plate.getType()), List.of("target"));
                pokemon.getEntity().getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);

                pokemon.getEntity().after(3F, () -> {
                    new StringSpeciesFeature("multitype", plate.getType()).apply(pokemon);
                    SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
                    pokemon.getEntity().getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                    return Unit.INSTANCE;
                });
            }
            if (event.getReceiving().getItem() instanceof ElementalZCrystal crystal) {
                pokemonEntity.level().playSound(
                        null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                        ModSounds.ARCEUS_MULTITYPE.get(),
                        SoundSource.PLAYERS, 0.2f, 1.3f
                );

                SnowStormHandler.Companion.snowStormPartileSpawner(pokemon.getEntity(),
                        ResourceLocation.tryParse("cobblemon:arceus_" + crystal.getType()), List.of("target"));
                pokemon.getEntity().getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);

                pokemon.getEntity().after(3F, () -> {
                    new StringSpeciesFeature("multitype", crystal.getType()).apply(pokemon);
                    SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
                    pokemon.getEntity().getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                    return Unit.INSTANCE;
                });
            } else if (event.getReturning().getItem() instanceof ArceusType || event.getReturning().getItem() instanceof ElementalZCrystal) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "normal").apply(pokemon);
                SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
            }
        }
    }

    public static void originChange(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        if (pokemon.getSpecies().getName().equals("Giratina")) {
            if (event.getReceiving().is(FormeChangeItems.GRISEOUS_CORE)) {
                originAnimation(pokemon.getEntity(), true);
            } else if (event.getReturning().is(FormeChangeItems.GRISEOUS_CORE)) {
                originAnimation(pokemon.getEntity(), false);
            }
        } else if (pokemon.getSpecies().getName().equals("Palkia")) {
            if (event.getReceiving().is(FormeChangeItems.LUSTROUS_GLOBE)) {
                originAnimation(pokemon.getEntity(), true);
            } else if (event.getReturning().is(FormeChangeItems.LUSTROUS_GLOBE)) {
                originAnimation(pokemon.getEntity(), false);
            }
        } else if (pokemon.getSpecies().getName().equals("Dialga")) {
            if (event.getReceiving().is(FormeChangeItems.ADAMANT_CRYSTAL)) {
                originAnimation(pokemon.getEntity(), true);
            } else if (event.getReturning().is(FormeChangeItems.ADAMANT_CRYSTAL)) {
                originAnimation(pokemon.getEntity(), false);
            }
        }
    }

    public static void eternamaxChange(HeldItemEvent.Pre event) {
        if (!MegaShowdownConfig.etermaxForme) {
            return;
        }
        Pokemon pokemon = event.getPokemon();

        if (!pokemon.getSpecies().getName().equals("Eternatus")) {
            return;
        }

        if (event.getReceiving().is(FormeChangeItems.STAR_CORE)) {
            SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
            new FlagSpeciesFeature("eternamax", true).apply(pokemon);
            pokemon.setTradeable(false);
        } else if (event.getReturning().is(FormeChangeItems.STAR_CORE)) {
            new FlagSpeciesFeature("eternamax", false).apply(pokemon);
            pokemon.setTradeable(true);
        }
    }

    public static void ogerponChange(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        if (!pokemon.getSpecies().getName().equals("Ogerpon")) {
            return;
        }
        if (event.getReceiving().is(FormeChangeItems.HEARTHFLAME_MASK)) {
            new StringSpeciesFeature("ogre_mask", "hearthflame").apply(pokemon);
            playHeldItemChange(pokemon.getEntity());
            SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
            pokemon.setTeraType(TeraTypes.getFIRE());
        } else if (event.getReceiving().is(FormeChangeItems.CORNERSTONE_MASK)) {
            new StringSpeciesFeature("ogre_mask", "cornerstone").apply(pokemon);
            playHeldItemChange(pokemon.getEntity());
            SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
            pokemon.setTeraType(TeraTypes.getROCK());
        } else if (event.getReceiving().is(FormeChangeItems.WELLSPRING_MASK)) {
            new StringSpeciesFeature("ogre_mask", "wellspring").apply(pokemon);
            playHeldItemChange(pokemon.getEntity());
            SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
            pokemon.setTeraType(TeraTypes.getWATER());
        } else if (event.getReturning().is(FormeChangeItems.WELLSPRING_MASK)
                || event.getReturning().is(FormeChangeItems.CORNERSTONE_MASK)
                || event.getReturning().is(FormeChangeItems.HEARTHFLAME_MASK)) {
            new StringSpeciesFeature("ogre_mask", "teal").apply(pokemon);
            SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
            playHeldItemChange(pokemon.getEntity());
            pokemon.setTeraType(TeraTypes.getGRASS());
        }
    }

    public static void primalEvent(HeldItemEvent.Pre pre) {
        ServerPlayer player = pre.getPokemon().getOwnerPlayer();
        Species species = pre.getPokemon().getSpecies();

        if (!species.getName().equals("Kyogre") && !species.getName().equals("Groudon")) {
            return;
        }

        boolean primalData = FormChangeHelper.hasPrimal(player);

        if (species.getName().equals("Kyogre") && pre.getReceiving().is(MegaStones.BLUE_ORB) && !pre.getPokemon().getAspects().contains("primal")) {
            if (!primalData || MegaShowdownConfig.multiplePrimals) {
                new StringSpeciesFeature("reversion_state", "primal").apply(pre.getPokemon());
                primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.BUBBLE, true);
                pre.getPokemon().setTradeable(false);
            } else {
                pre.cancel();
                player.displayClientMessage(Component.translatable("message.mega_showdown.primal_limit")
                        .withColor(0xFF0000), true);
            }
        } else if (species.getName().equals("Groudon") && pre.getReceiving().is(MegaStones.RED_ORB) && !pre.getPokemon().getAspects().contains("primal")) {
            if (!primalData || MegaShowdownConfig.multiplePrimals) {
                new StringSpeciesFeature("reversion_state", "primal").apply(pre.getPokemon());
                primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.CAMPFIRE_COSY_SMOKE, true);
                pre.getPokemon().setTradeable(false);
            } else {
                pre.cancel();
                player.displayClientMessage(Component.translatable("message.mega_showdown.primal_limit")
                        .withColor(0xFF0000), true);
            }
        } else if (species.getName().equals("Kyogre") && !pre.getReceiving().is(MegaStones.BLUE_ORB) && pre.getReturning().is(MegaStones.BLUE_ORB)) {
            new StringSpeciesFeature("reversion_state", "standard").apply(pre.getPokemon());
            primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.END_ROD, false);
            pre.getPokemon().setTradeable(true);
        } else if (species.getName().equals("Groudon") && !pre.getReceiving().is(MegaStones.RED_ORB) && pre.getReturning().is(MegaStones.RED_ORB)) {
            new StringSpeciesFeature("reversion_state", "standard").apply(pre.getPokemon());
            primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.END_ROD, false);
            pre.getPokemon().setTradeable(true);
        }
    }

    public static void megaEvent(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        if (pokemon.getAspects().contains("mega_x") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega")) {
            MegaLogic.Devolve(pokemon, false);
        }
    }

    public static void crownedEvent(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        if (pokemon.getSpecies().getName().equals("Zacian") ||
                pokemon.getSpecies().getName().equals("Zamazenta")) {
            if (event.getReceiving().is(FormeChangeItems.RUSTED_SWORD) && pokemon.getSpecies().getName().equals("Zacian")) {
                crownAnimation((ServerLevel) pokemon.getEntity().level(), pokemon.getEntity().getOnPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                pokemon.setTradeable(false);
            } else if (event.getReceiving().is(FormeChangeItems.RUSTED_SHIELD) && pokemon.getSpecies().getName().equals("Zamazenta")) {
                crownAnimation((ServerLevel) pokemon.getEntity().level(), pokemon.getEntity().getOnPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                pokemon.setTradeable(false);
            } else if (pokemon.getSpecies().getName().equals("Zacian") && event.getReturning().is(FormeChangeItems.RUSTED_SWORD)) {
                playHeldItemChange(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                pokemon.setTradeable(true);
            } else if (pokemon.getSpecies().getName().equals("Zamazenta") && event.getReturning().is(FormeChangeItems.RUSTED_SHIELD)) {
                playHeldItemChange(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                pokemon.setTradeable(true);
            }
        }
    }

    public static void ultraEvent(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        if (pokemon.getSpecies().getName().equals("Necrozma") && pokemon.getAspects().contains("ultra-fusion")) {
            if (event.getReturning().is(ZCrystals.ULTRANECROZIUM_Z)) {
                if (!pokemon.getEntity().isBattling()) {
                    SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
                }
                UltraLogic.ultraAnimation(pokemon.getEntity());
                new StringSpeciesFeature("prism_fusion", pokemon.getPersistentData().getString("fusion_form")).apply(pokemon);
            }
        }
    }

    private static void playHeldItemChange(LivingEntity context) {
        SnowStormHandler.Companion.playAnimation(context, Set.of("cry"), List.of());
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

    private static void originAnimation(PokemonEntity context, boolean enabled) {
        BlockPos entityPos = context.getOnPos();

        if (context.getPokemon().getSpecies().getName().equals("Giratina")) {
            context.level().playSound(
                    null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                    ModSounds.GIRATINIA_FORM.get(),
                    SoundSource.PLAYERS, 0.2f, 1.1f
            );

            SnowStormHandler.Companion.snowStormPartileSpawner(context, ResourceLocation.tryParse("cobblemon:origin_g_effect"), List.of("target"));
            context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);

            context.after(3.8F, () -> {
                if (enabled) {
                    new StringSpeciesFeature("orb_forme", "origin").apply(context.getPokemon());
                } else {
                    new StringSpeciesFeature("orb_forme", "altered").apply(context.getPokemon());
                }
                context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                SnowStormHandler.Companion.playAnimation(context, Set.of("cry"), List.of());
                return Unit.INSTANCE;
            });
        } else {
            context.level().playSound(
                    null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                    ModSounds.ORIGIN_FORM.get(),
                    SoundSource.PLAYERS, 0.2f, 1.1f
            );

            SnowStormHandler.Companion.snowStormPartileSpawner(context, ResourceLocation.tryParse("cobblemon:origin_effect"), List.of("target"));
            context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);

            context.after(4F, () -> {
                if (enabled) {
                    new StringSpeciesFeature("orb_forme", "origin").apply(context.getPokemon());
                } else {
                    new StringSpeciesFeature("orb_forme", "altered").apply(context.getPokemon());
                }
                context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                SnowStormHandler.Companion.playAnimation(context, Set.of("cry"), List.of());
                return Unit.INSTANCE;
            });
        }
    }

    private static void crownAnimation(ServerLevel level, BlockPos pos, LivingEntity context) {
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
        if (lightning != null) {
            lightning.moveTo(Vec3.atBottomCenterOf(pos));
            lightning.setVisualOnly(true);
            level.addFreshEntity(lightning);
            playHeldItemChange(context);
        }
    }

    private static void primalRevertAnimation(PokemonEntity context, SimpleParticleType particleType, Boolean revert) {
        if (revert) {
            context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);
            context.after(2.5f, () -> {
                SnowStormHandler.Companion.playAnimation(context, Set.of("cry"), List.of());
                context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                return Unit.INSTANCE;
            });
        }

        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 1.2; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityDepth * scaleFactor;

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BEACON_ACTIVATE, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverLevel.sendParticles(
                        particleType,
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
