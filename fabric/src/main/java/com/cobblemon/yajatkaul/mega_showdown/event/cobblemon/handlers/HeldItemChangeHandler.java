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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class HeldItemChangeHandler {
    public static void genesectChange(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();
        if (pokemon.getSpecies().getName().equals("Genesect")) {
            if (event.getReceiving().isOf(FormeChangeItems.DOUSE_DRIVE)) {
                SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive", "water").apply(pokemon);
            } else if (event.getReceiving().isOf(FormeChangeItems.BURN_DRIVE)) {
                SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive", "fire").apply(pokemon);
            } else if (event.getReceiving().isOf(FormeChangeItems.CHILL_DRIVE)) {
                SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive", "ice").apply(pokemon);
            } else if (event.getReceiving().isOf(FormeChangeItems.SHOCK_DRIVE)) {
                SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive", "electric").apply(pokemon);
            } else if (!(event.getReceiving().getItem() instanceof Drives) && event.getReturning().getItem() instanceof Drives) {
                SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive", "none").apply(pokemon);
            }
        }
    }

    public static void silvallyChange(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();
        if (pokemon.getSpecies().getName().equals("Silvally")) {
            if (event.getReceiving().getItem() instanceof Memory memory) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory", memory.getType()).apply(pokemon);
            } else if (event.getReturning().getItem() instanceof Memory) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory", "normal").apply(pokemon);
            }
        }
    }

    public static void arcuesChange(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();
        PokemonEntity pokemonEntity = pokemon.getEntity();
        BlockPos entityPos = pokemonEntity.getBlockPos();

        if (pokemon.getSpecies().getName().equals("Arceus")) {
            if (event.getReceiving().getItem() instanceof ArceusType plate) {
                pokemonEntity.getWorld().playSound(
                        null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                        ModSounds.ARCEUS_MULTITYPE,
                        SoundCategory.PLAYERS, 0.2f, 1.3f
                );

                SnowStormHandler.Companion.snowStormPartileSpawner(pokemon.getEntity(),
                        "arceus_" + plate.getType(), "target");
                pokemon.getEntity().getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);

                pokemon.getEntity().after(3F, () -> {
                    new StringSpeciesFeature("multitype", plate.getType()).apply(pokemon);
                    SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                    pokemon.getEntity().getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                    return Unit.INSTANCE;
                });
            } else if (event.getReceiving().getItem() instanceof ElementalZCrystal crystal) {
                pokemonEntity.getWorld().playSound(
                        null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                        ModSounds.ARCEUS_MULTITYPE,
                        SoundCategory.PLAYERS, 0.2f, 1.3f
                );

                SnowStormHandler.Companion.snowStormPartileSpawner(pokemon.getEntity(),
                        "arceus_" + crystal.getType(), "target");
                pokemon.getEntity().getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);

                pokemon.getEntity().after(3F, () -> {
                    new StringSpeciesFeature("multitype", crystal.getType()).apply(pokemon);
                    SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                    pokemon.getEntity().getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                    return Unit.INSTANCE;
                });
            } else if (event.getReturning().getItem() instanceof ArceusType || event.getReturning().getItem() instanceof ElementalZCrystal) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "normal").apply(pokemon);
                SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
            }
        }
    }

    public static void originChange(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        if (pokemon.getSpecies().getName().equals("Giratina")) {
            if (event.getReceiving().isOf(FormeChangeItems.GRISEOUS_CORE)) {
                originAnimation(pokemon.getEntity(), true);
            } else if (event.getReturning().isOf(FormeChangeItems.GRISEOUS_CORE)) {
                originAnimation(pokemon.getEntity(), false);
            }
        } else if (pokemon.getSpecies().getName().equals("Palkia")) {
            if (event.getReceiving().isOf(FormeChangeItems.LUSTROUS_GLOBE)) {
                originAnimation(pokemon.getEntity(), true);
            } else if (event.getReturning().isOf(FormeChangeItems.LUSTROUS_GLOBE)) {
                originAnimation(pokemon.getEntity(), false);
            }
        } else if (pokemon.getSpecies().getName().equals("Dialga")) {
            if (event.getReceiving().isOf(FormeChangeItems.ADAMANT_CRYSTAL)) {
                originAnimation(pokemon.getEntity(), true);
            } else if (event.getReturning().isOf(FormeChangeItems.ADAMANT_CRYSTAL)) {
                originAnimation(pokemon.getEntity(), false);
            }
        }
    }

    public static void eternamaxChange(HeldItemEvent.Pre event) {
        if (!MegaShowdownConfig.etermaxForme.get()) {
            return;
        }
        Pokemon pokemon = event.getPokemon();

        if (!pokemon.getSpecies().getName().equals("Eternatus")) {
            return;
        }

        if (event.getReceiving().isOf(FormeChangeItems.STAR_CORE)) {
            SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
            new FlagSpeciesFeature("eternamax", true).apply(pokemon);
            setTradable(pokemon, false);
        } else if (event.getReturning().isOf(FormeChangeItems.STAR_CORE) && event.getReturning().isOf(FormeChangeItems.STAR_CORE)) {
            new FlagSpeciesFeature("eternamax", false).apply(pokemon);
            setTradable(pokemon, true);
        }
    }

    public static void ogerponChange(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        if (!pokemon.getSpecies().getName().equals("Ogerpon")) {
            return;
        }
        if (event.getReceiving().isOf(FormeChangeItems.HEARTHFLAME_MASK)) {
            new StringSpeciesFeature("ogre_mask", "hearthflame").apply(pokemon);
            SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
            playHeldItemFormeChange(pokemon.getEntity());

            pokemon.setTeraType(TeraTypes.getFIRE());

        } else if (event.getReceiving().isOf(FormeChangeItems.CORNERSTONE_MASK)) {
            new StringSpeciesFeature("ogre_mask", "cornerstone").apply(pokemon);
            SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
            playHeldItemFormeChange(pokemon.getEntity());

            pokemon.setTeraType(TeraTypes.getROCK());

        } else if (event.getReceiving().isOf(FormeChangeItems.WELLSPRING_MASK)) {
            new StringSpeciesFeature("ogre_mask", "wellspring").apply(pokemon);
            SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
            playHeldItemFormeChange(pokemon.getEntity());

            pokemon.setTeraType(TeraTypes.getWATER());

        } else if (event.getReturning().isOf(FormeChangeItems.WELLSPRING_MASK)
                || event.getReturning().isOf(FormeChangeItems.CORNERSTONE_MASK)
                || event.getReturning().isOf(FormeChangeItems.HEARTHFLAME_MASK)) {
            new StringSpeciesFeature("ogre_mask", "teal").apply(pokemon);
            SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
            playHeldItemFormeChange(pokemon.getEntity());

            pokemon.setTeraType(TeraTypes.getGRASS());

        }
    }

    public static void primalEvent(HeldItemEvent.Pre event) {
        ServerPlayerEntity player = event.getPokemon().getOwnerPlayer();
        Species species = event.getPokemon().getSpecies();

        if (!species.getName().equals("Kyogre") && !species.getName().equals("Groudon")) {
            return;
        }

        boolean primalData = FormChangeHelper.hasPrimal(player);

        if (species.getName().equals("Kyogre") && event.getReceiving().isOf(MegaStones.BLUE_ORB) && !event.getPokemon().getAspects().contains("primal")) {
            if (!primalData || MegaShowdownConfig.multiplePrimals.get()) {
                new StringSpeciesFeature("reversion_state", "primal").apply(event.getPokemon());
                primalRevertAnimation(event.getPokemon().getEntity(), ParticleTypes.BUBBLE, true);
                setTradable(event.getPokemon(), false);
            } else {
                event.cancel();
                player.sendMessage(
                        Text.translatable("message.mega_showdown.primal_limit").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }
        } else if (species.getName().equals("Groudon") && event.getReceiving().isOf(MegaStones.RED_ORB) && !event.getPokemon().getAspects().contains("primal")) {
            if (!primalData || MegaShowdownConfig.multiplePrimals.get()) {
                new StringSpeciesFeature("reversion_state", "primal").apply(event.getPokemon());
                primalRevertAnimation(event.getPokemon().getEntity(), ParticleTypes.CAMPFIRE_COSY_SMOKE, true);
                setTradable(event.getPokemon(), false);
            } else {
                event.cancel();
                player.sendMessage(
                        Text.translatable("message.mega_showdown.primal_limit").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }
        } else if (species.getName().equals("Kyogre") && !event.getReceiving().isOf(MegaStones.BLUE_ORB) && event.getReturning().isOf(MegaStones.BLUE_ORB)) {
            new StringSpeciesFeature("reversion_state", "standard").apply(event.getPokemon());
            primalRevertAnimation(event.getPokemon().getEntity(), ParticleTypes.END_ROD, false);
            setTradable(event.getPokemon(), true);
        } else if (species.getName().equals("Groudon") && !event.getReceiving().isOf(MegaStones.RED_ORB) && event.getReturning().isOf(MegaStones.RED_ORB)) {
            new StringSpeciesFeature("reversion_state", "standard").apply(event.getPokemon());
            primalRevertAnimation(event.getPokemon().getEntity(), ParticleTypes.END_ROD, false);
            setTradable(event.getPokemon(), true);
        }
    }

    public static void megaEvent(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        if (pokemon.getAspects().contains("mega_x") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega")) {
            MegaLogic.Devolve(pokemon, true);
        }
    }

    public static void crownedEvent(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        if (pokemon.getSpecies().getName().equals("Zacian") ||
                pokemon.getSpecies().getName().equals("Zamazenta")) {
            if (event.getReceiving().isOf(FormeChangeItems.RUSTED_SWORD) && pokemon.getSpecies().getName().equals("Zacian")) {
                crownAnimation((ServerWorld) pokemon.getEntity().getWorld(), pokemon.getEntity().getBlockPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if (event.getReceiving().isOf(FormeChangeItems.RUSTED_SHIELD) && pokemon.getSpecies().getName().equals("Zamazenta")) {
                crownAnimation((ServerWorld) pokemon.getEntity().getWorld(), pokemon.getEntity().getBlockPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if (pokemon.getSpecies().getName().equals("Zacian") && event.getReturning().isOf(FormeChangeItems.RUSTED_SWORD)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            } else if (pokemon.getSpecies().getName().equals("Zamazenta") && event.getReturning().isOf(FormeChangeItems.RUSTED_SHIELD)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            }
        }
    }

    public static void ultraEvent(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        if (pokemon.getSpecies().getName().equals("Necrozma") && pokemon.getAspects().contains("ultra-fusion")) {
            if (event.getReturning().isOf(ZCrystals.ULTRANECROZIUM_Z)) {
                if (!pokemon.getEntity().isBattling()) {
                    SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                }
                EventUtils.ultraAnimation(pokemon.getEntity());
                new StringSpeciesFeature("prism_fusion", pokemon.getPersistentData().getString("fusion_form")).apply(pokemon);
            }
        }
    }

    private static void playHeldItemFormeChange(LivingEntity context) {
        SnowStormHandler.Companion.cryAnimation(context);
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

    private static void originAnimation(PokemonEntity context, boolean enabled) {
        BlockPos entityPos = context.getBlockPos();

        if (context.getPokemon().getSpecies().getName().equals("Giratina")) {
            context.getWorld().playSound(
                    null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                    ModSounds.GIRATINIA_FORM,
                    SoundCategory.PLAYERS, 0.2f, 1.1f
            );

            SnowStormHandler.Companion.snowStormPartileSpawner(context, "origin_g_effect", "target");
            context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);

            context.after(3.8F, () -> {
                if (enabled) {
                    new StringSpeciesFeature("orb_forme", "origin").apply(context.getPokemon());
                } else {
                    new StringSpeciesFeature("orb_forme", "altered").apply(context.getPokemon());
                }
                context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                SnowStormHandler.Companion.cryAnimation(context);
                return Unit.INSTANCE;
            });
        } else {
            context.getWorld().playSound(
                    null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                    ModSounds.ORIGIN_FORM,
                    SoundCategory.PLAYERS, 0.2f, 1.1f
            );

            SnowStormHandler.Companion.snowStormPartileSpawner(context, "origin_effect", "target");
            context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);

            context.after(4F, () -> {
                if (enabled) {
                    new StringSpeciesFeature("orb_forme", "origin").apply(context.getPokemon());
                } else {
                    new StringSpeciesFeature("orb_forme", "altered").apply(context.getPokemon());
                }
                context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                SnowStormHandler.Companion.cryAnimation(context);
                return Unit.INSTANCE;
            });
        }
    }

    private static void crownAnimation(ServerWorld level, BlockPos pos, LivingEntity context) {
        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(level);
        if (lightning != null) {
            lightning.setPosition(Vec3d.ofBottomCenter(pos));
            lightning.setCosmetic(true);
            level.spawnEntity(lightning);
            playHeldItemFormeChange(context);
        }
    }

    private static void primalRevertAnimation(PokemonEntity context, SimpleParticleType particleType, Boolean revert) {
        if (revert) {
            context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);
            context.after(2.5f, () -> {
                SnowStormHandler.Companion.cryAnimation(context);
                context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                return Unit.INSTANCE;
            });
        }

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
                    SoundEvents.BLOCK_BEACON_ACTIVATE, // Change this if needed
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverWorld.spawnParticles(
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
