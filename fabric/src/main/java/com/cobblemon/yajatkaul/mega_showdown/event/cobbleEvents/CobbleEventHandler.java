package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.drop.ItemDropEntry;
import com.cobblemon.mod.common.api.events.battles.*;
import com.cobblemon.mod.common.api.events.battles.instruction.FormeChangeEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.MegaEvolutionEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.TerastallizationEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.ZMoveUsedEvent;
import com.cobblemon.mod.common.api.events.drops.LootDroppedEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.events.pokemon.PokemonCapturedEvent;
import com.cobblemon.mod.common.api.events.pokemon.TradeCompletedEvent;
import com.cobblemon.mod.common.api.events.pokemon.healing.PokemonHealedEvent;
import com.cobblemon.mod.common.api.events.storage.ReleasePokemonEvent;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.ZMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.*;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import dev.emi.trinkets.api.TrinketsApi;
import kotlin.Unit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.predicate.entity.LightningBoltPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static com.cobblemon.yajatkaul.mega_showdown.utility.TeraTypeHelper.*;
import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class CobbleEventHandler {
    public static Unit onHeldItemChange(HeldItemEvent.Post post) {
        if(post.getReturned() == post.getReceived() || post.getPokemon().getOwnerPlayer() == null){
            return Unit.INSTANCE;
        }

        genesectChange(post);
        silvallyChange(post);
        arcuesChange(post);
        checkUltra(post);
        primalEvent(post);
        crownedEvent(post);
        ogerponChange(post);
        eternamaxChange(post);
        originChange(post);

        // Battle mode only
        if(ShowdownConfig.battleModeOnly.get()){
            return Unit.INSTANCE;
        }

        megaEvent(post);

        return Unit.INSTANCE;
    }

    public static Unit onReleasePokemon(ReleasePokemonEvent.Post post) {
        if(post.getPlayer().getWorld().isClient){
            return Unit.INSTANCE;
        }

        if(!post.getPlayer().hasAttached(DataManage.PRIMAL_POKEMON)){
            post.getPlayer().setAttached(DataManage.PRIMAL_POKEMON, new Pokemon());
        }

        if(!post.getPlayer().hasAttached(DataManage.MEGA_POKEMON)){
            post.getPlayer().setAttached(DataManage.MEGA_POKEMON, new Pokemon());
        }

        if(post.getPlayer().getAttached(DataManage.MEGA_POKEMON) == post.getPokemon()){
            post.getPlayer().removeAttached(DataManage.MEGA_DATA);
            post.getPlayer().removeAttached(DataManage.MEGA_POKEMON);
        }

        if(post.getPlayer().getAttached(DataManage.PRIMAL_POKEMON) == post.getPokemon()){
            post.getPlayer().setAttached(DataManage.PRIMAL_DATA, false);
            post.getPlayer().setAttached(DataManage.PRIMAL_POKEMON, new Pokemon());
        }

        return Unit.INSTANCE;
    }

    public static void genesectChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Genesect")){
            if(post.getReceived().isOf(ModItems.DOUSE_DRIVE)){
                new StringSpeciesFeature("techno_drive","water").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.BURN_DRIVE)){
                new StringSpeciesFeature("techno_drive","fire").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.CHILL_DRIVE)){
                new StringSpeciesFeature("techno_drive","ice").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.SHOCK_DRIVE)){
                new StringSpeciesFeature("techno_drive","electric").apply(pokemon);
            }
            else if (!(post.getReceived().getItem() instanceof Drives)) {
                new StringSpeciesFeature("techno_drive","none").apply(pokemon);
            }
        }
    }
    public static void silvallyChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Silvally")){
            if(post.getReceived().isOf(ModItems.BUG_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","bug").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.DARK_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","dark").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.DRAGON_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","dragon").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.ELECTRIC_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","electric").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.FAIRY_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fairy").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.FIGHTING_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fighting").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.FIRE_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fire").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.FLYING_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","flying").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.GHOST_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ghost").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.GRASS_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","grass").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.GROUND_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ground").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.ICE_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ice").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.POISON_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","poison").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.PSYCHIC_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","psychic").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.ROCK_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","rock").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.STEEL_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","steel").apply(pokemon);
            }
            else if(post.getReceived().isOf(ModItems.WATER_MEMORY)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","water").apply(pokemon);
            } else if (!(post.getReceived().getItem() instanceof Memories)) {
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","normal").apply(pokemon);
            }
        }
    }
    public static void arcuesChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Arceus")){
            if(post.getReceived().isOf(ModItems.FLAME_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fire").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.SPLASH_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","water").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.ZAP_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","electric").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.MEADOW_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","grass").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.ICICLE_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ice").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.FIST_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fighting").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.TOXIC_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","poison").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.EARTH_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ground").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.SKY_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","flying").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.MIND_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","psychic").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.INSECT_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","bug").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.STONE_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","rock").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.SPOOKY_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ghost").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.DRACO_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","dragon").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.DREAD_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","dark").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.IRON_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","steel").apply(pokemon);
            } else if(post.getReceived().isOf(ModItems.PIXIE_PLATE)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fairy").apply(pokemon);
            } else if(!(post.getReceived().getItem() instanceof ArceusPlates)){
                playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","normal").apply(pokemon);
            }
        }
    }
    public static void originChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();

        if(pokemon.getSpecies().getName().equals("Giratina")){
            if(post.getReceived().isOf(ModItems.GRISEOUS_ORB)){
                originAnimation(pokemon.getEntity(), ParticleTypes.ASH);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().isOf(ModItems.GRISEOUS_ORB)){
                originAnimation(pokemon.getEntity(), ParticleTypes.ASH);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        } else if (pokemon.getSpecies().getName().equals("Palkia")) {
            if(post.getReceived().isOf(ModItems.LUSTROUS_GLOBE)){
                originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().isOf(ModItems.LUSTROUS_GLOBE)){
                originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Dialga")) {
            if(post.getReceived().isOf(ModItems.ADAMANT_ORB)){
                originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().isOf(ModItems.ADAMANT_ORB)){
                originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        }
    }
    public static void eternamaxChange(HeldItemEvent.Post post){
        if(!ShowdownConfig.etermaxForme.get()){
            return;
        }
        Pokemon pokemon = post.getPokemon();

        if(pokemon.getSpecies().getName().equals("Eternatus") && post.getReceived().isOf(ModItems.STAR_CORE)){
            new FlagSpeciesFeature("eternamax",true).apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Eternatus")) {
            new FlagSpeciesFeature("eternamax",false).apply(pokemon);
        }
    }
    public static void ogerponChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();

        if(!pokemon.getSpecies().getName().equals("Ogerpon")){
            return;
        }
        if(post.getReceived().isOf(ModItems.HEARTHFLAME_MASK)){
            new StringSpeciesFeature("ogre_mask","hearthflame").apply(pokemon);
            try {
                pokemon.setTeraType(TeraTypes.getFIRE());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        } else if (post.getReceived().isOf(ModItems.CORNERSTONE_MASK)) {
            new StringSpeciesFeature("ogre_mask","cornerstone").apply(pokemon);
            try {
                pokemon.setTeraType(TeraTypes.getROCK());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        }else if (post.getReceived().isOf(ModItems.WELLSPRING_MASK)) {
            new StringSpeciesFeature("ogre_mask","wellspring").apply(pokemon);
            try {
                pokemon.setTeraType(TeraTypes.getWATER());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        }else {
            new StringSpeciesFeature("ogre_mask","teal").apply(pokemon);
            try {
                pokemon.setTeraType(TeraTypes.getGRASS());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        }
    }
    public static void primalEvent(HeldItemEvent.Post post) {
        ServerPlayerEntity player = post.getPokemon().getOwnerPlayer();
        Species species = post.getPokemon().getSpecies();

        if(species.getName().equals(Utils.getSpecies("kyogre").getName()) && post.getReceived().isOf(MegaStones.BLUE_ORB)){
            if(player.getAttached(DataManage.PRIMAL_DATA) && !ShowdownConfig.multiplePrimals.get()){
                player.sendMessage(
                        Text.literal("You can only have one primal at a time").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return;
            }
            new FlagSpeciesFeature("primal", true).apply(post.getPokemon());
            setTradable(post.getPokemon(), false);
            primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.BUBBLE);
            AdvancementHelper.grantAdvancement(player, "primal_evo");
            player.setAttached(DataManage.PRIMAL_DATA, true);
        }
        else if(species.getName().equals(Utils.getSpecies("groudon").getName()) && post.getReceived().isOf(MegaStones.RED_ORB)){
            if(player.getAttached(DataManage.PRIMAL_DATA) && !ShowdownConfig.multiplePrimals.get()){
                player.sendMessage(
                        Text.literal("You can only have one primal at a time").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return;
            }
            new FlagSpeciesFeature("primal", true).apply(post.getPokemon());
            setTradable(post.getPokemon(), false);
            primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.CAMPFIRE_COSY_SMOKE);
            AdvancementHelper.grantAdvancement(player, "primal_evo");
            player.setAttached(DataManage.PRIMAL_DATA, true);
        }else{
            SpeciesFeature feature = post.getPokemon().getFeature("primal");
            if(feature == null){
                return;
            }

            new FlagSpeciesFeature("primal", false).apply(post.getPokemon());
            setTradable(post.getPokemon(), true);
            primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.END_ROD);
            player.setAttached(DataManage.PRIMAL_DATA, false);
        }

        return;
    }
    public static void megaEvent(HeldItemEvent.Post event) {
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getEntity() == null){
            return;
        }

        if(pokemon.getEntity().getWorld().isClient){
            return;
        }

        Species species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());


        List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

        for (String key : megaKeys) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
            ServerPlayerEntity player = pokemon.getOwnerPlayer();

            FlagSpeciesFeature feature = featureProvider.get(pokemon);
            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if (enabled && feature.getName().equals("mega") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())) {
                    MegaLogic.Devolve(pokemon.getEntity(), player, true);
                }else if(enabled && feature.getName().equals("mega-x") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())){
                    MegaLogic.Devolve(pokemon.getEntity(), player, true);
                } else if (enabled && feature.getName().equals("mega-y") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())) {
                    MegaLogic.Devolve(pokemon.getEntity(), player, true);
                }
            }
        }
    }
    public static void crownedEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getSpecies().getName().equals("Zacian") ||
                pokemon.getSpecies().getName().equals("Zamazenta")){
            if(event.getReceived().isOf(ModItems.RUSTED_SWORD) && pokemon.getSpecies().getName().equals("Zacian")){
                crownAnimation((ServerWorld) pokemon.getEntity().getWorld(), pokemon.getEntity().getBlockPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if (event.getReceived().isOf(ModItems.RUSTED_SHIELD) && pokemon.getSpecies().getName().equals("Zamazenta")) {
                crownAnimation((ServerWorld) pokemon.getEntity().getWorld(), pokemon.getEntity().getBlockPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if(pokemon.getSpecies().getName().equals("Zacian") && event.getReturned().isOf(ModItems.RUSTED_SWORD)){
                playEvolveAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            }else if(pokemon.getSpecies().getName().equals("Zamazenta") && event.getReturned().isOf(ModItems.RUSTED_SHIELD)){
                playEvolveAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            }
        }
    }
    public static void checkUltra(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getSpecies().getName().equals("Necrozma")){
            if(event.getReturned().isOf(ZMoves.ULTRANECROZIUM_Z)){
                ultraAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("ultra",false).apply(pokemon);
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
    private static void crownAnimation(ServerWorld level, BlockPos pos, LivingEntity context) {
        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(level);
        if (lightning != null) {
            lightning.setPosition(Vec3d.ofBottomCenter(pos));
            lightning.setCosmetic(true);
            level.spawnEntity(lightning);
            playEvolveAnimation(context);
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
    public static void primalRevertAnimation(LivingEntity context, SimpleParticleType particleType) {
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
    public static void originAnimation(LivingEntity context, SimpleParticleType particleType) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 2; // Adjust this for more spread
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

    public static Unit getBattleEndInfo(BattleVictoryEvent battleVictoryEvent) {
        battleVictoryEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
            for (Pokemon pokemon: playerPartyStore){
                if(pokemon.getEntity() != null){
                    EventUtils.revertFormesEnd(pokemon);
                    pokemon.getEntity().removeStatusEffect(StatusEffects.GLOWING);
                }
            }

            for (BattlePokemon battlePokemon : battleVictoryEvent.getBattle().getActor(serverPlayer.getUuid()).getPokemonList()) {
                if (battlePokemon.getOriginalPokemon().getEntity() == null ||
                        battlePokemon.getOriginalPokemon().getEntity().getWorld().isClient) {
                    continue;
                }

                Pokemon pokemon = battlePokemon.getOriginalPokemon();

                List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

                for (String key : megaKeys) {
                    FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
                    FlagSpeciesFeature feature = featureProvider.get(pokemon);

                    if(feature != null){
                        boolean enabled = featureProvider.get(pokemon).getEnabled();

                        if(enabled){
                            MegaLogic.Devolve(pokemon.getEntity(), serverPlayer, true);
                            serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                            if(!ShowdownConfig.multipleMegas.get()){
                                break;
                            }
                        }
                    }
                }
            }
        });

        return Unit.INSTANCE;
    }

    public static Unit devolveFainted(BattleFaintedEvent battleFaintedEvent) {
        Pokemon pokemon = battleFaintedEvent.getKilled().getOriginalPokemon();
        ServerPlayerEntity serverPlayer = battleFaintedEvent.getKilled().getOriginalPokemon().getOwnerPlayer();

        if(serverPlayer == null || serverPlayer.getWorld().isClient){
            return Unit.INSTANCE;
        }

        List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

        for (String key : megaKeys) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
            FlagSpeciesFeature feature = featureProvider.get(pokemon);

            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if(enabled){
                    MegaLogic.Devolve(pokemon.getEntity(), serverPlayer, true);
                    serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                    break;
                }
            }
        }

        return Unit.INSTANCE;
    }

    public static Unit deVolveFlee(BattleFledEvent battleFledEvent) {
        battleFledEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            for (BattlePokemon battlePokemon : battleFledEvent.getBattle().getActor(serverPlayer.getUuid()).getPokemonList()) {
                if (battlePokemon.getOriginalPokemon().getEntity() == null ||
                        battlePokemon.getOriginalPokemon().getEntity().getWorld().isClient) {
                    continue;
                }

                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
                for (Pokemon pokemon: playerPartyStore){
                    if(pokemon.getEntity() != null){
                        EventUtils.revertFormesEnd(pokemon);
                        pokemon.getEntity().removeStatusEffect(StatusEffects.GLOWING);
                    }
                }

                Pokemon pokemon = battlePokemon.getOriginalPokemon();

                List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

                for (String key : megaKeys) {
                    FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
                    FlagSpeciesFeature feature = featureProvider.get(pokemon);

                    if(feature != null){
                        boolean enabled = featureProvider.get(pokemon).getEnabled();

                        if(enabled){
                            MegaLogic.Devolve(pokemon.getEntity(), serverPlayer, true);
                            serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                            if(!ShowdownConfig.multipleMegas.get()){
                                break;
                            }
                        }
                    }
                }
            }

        });

        return Unit.INSTANCE;
    }

    public static void checkKeldeo(PlayerPartyStore pokemons){
        for(Pokemon pokemon: pokemons){
            if(pokemon.getSpecies().getName().equals("Keldeo")){
                FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("resolute"));
                FlagSpeciesFeature feature = featureProvider.get(pokemon);
                boolean hasMove = false;

                for(Move move: pokemon.getMoveSet().getMoves()){
                    if(move.getName().equals(Moves.INSTANCE.getByName("secretsword").getName())){
                        hasMove = true;
                    }
                }

                if(feature != null){
                    boolean enabled = featureProvider.get(pokemon).getEnabled();

                    if(!enabled){
                        if(hasMove){
                            new FlagSpeciesFeature("resolute", true).apply(pokemon);
                            playEvolveAnimation(pokemon.getEntity());
                        }
                    }else{
                        if(!hasMove){
                            new FlagSpeciesFeature("resolute", false).apply(pokemon);
                            playEvolveAnimation(pokemon.getEntity());
                        }
                    }
                }
            }
        }
    }
    public static Unit battleStarted(BattleStartedPreEvent battleEvent) {
        for(ServerPlayerEntity player: battleEvent.getBattle().getPlayers()){
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

            checkKeldeo(playerPartyStore);
            if(ShowdownConfig.battleMode.get()){
                for (Pokemon pokemon : playerPartyStore) {
                    EventUtils.revertFormesEnd(pokemon);

                    List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

                    for (String key : megaKeys) {
                        FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
                        FlagSpeciesFeature feature = featureProvider.get(pokemon);

                        if(feature != null){
                            boolean enabled = featureProvider.get(pokemon).getEnabled();

                            if(enabled){
                                MegaLogic.Devolve(pokemon.getEntity(), player, true);
                            }
                        }
                    }
                }
            }

            GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);

            boolean hasTeraItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> item.getItem() instanceof TeraItem)).orElse(false);

            if(hasTeraItemTrinkets && ShowdownConfig.teralization.get()){
                data.getKeyItems().add(Identifier.of("cobblemon","tera_orb"));
            }else{
                data.getKeyItems().remove(Identifier.of("cobblemon","tera_orb"));
            }

            if((ShowdownConfig.scuffedMode.get() || ShowdownConfig.battleMode.get() || ShowdownConfig.battleModeOnly.get()) && MegaLogic.Possible(player, true) && (player.getAttached(DataManage.MEGA_DATA) == null || !player.getAttached(DataManage.MEGA_DATA))){
                data.getKeyItems().add(Identifier.of("cobblemon","key_stone"));
            }else{
                data.getKeyItems().remove(Identifier.of("cobblemon","key_stone"));
            }

            boolean hasZItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> item.getItem() instanceof ZRingItem)).orElse(false);

            if((player.getOffHandStack().isOf(ZMoves.Z_RING) || hasZItemTrinkets) && ShowdownConfig.zMoves.get()){
                data.getKeyItems().add(Identifier.of("cobblemon","z_ring"));
            }else{
                data.getKeyItems().remove(Identifier.of("cobblemon","z_ring"));
            }
        }

        return Unit.INSTANCE;
    }

    public static Unit megaEvolution(MegaEvolutionEvent megaEvolutionEvent) {
        PokemonBattle battle = megaEvolutionEvent.getBattle();
        Pokemon pokemon = megaEvolutionEvent.getPokemon().getEffectedPokemon();

        ServerPlayerEntity player = megaEvolutionEvent.getPokemon().getOriginalPokemon().getOwnerPlayer();

        if(player == null){
            return Unit.INSTANCE;
        }

        MegaLogic.Evolve(pokemon.getEntity(), player, true);

        battle.sendUpdate(new AbilityUpdatePacket(megaEvolutionEvent.getPokemon()::getEffectedPokemon, pokemon.getAbility().getTemplate()));
        battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon));

        return Unit.INSTANCE;
    }

    public static Unit zMovesUsed(ZMoveUsedEvent zMoveUsedEvent) {
        LivingEntity pokemon = zMoveUsedEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = zMoveUsedEvent.getPokemon().getEffectedPokemon();

        pokemon.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 115, 0,false, false));

        if (pokemon.getWorld() instanceof ServerWorld serverLevel) {
            AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "z_moves");
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);

            Team team = scoreboard.getTeam(teamName);
            if (team == null) {
                team = scoreboard.addTeam(teamName);
                team.setColor(getGlowColorForType(zMoveUsedEvent.getPokemon().getOriginalPokemon()));
            }

            scoreboard.addScoreHolderToTeam(pokemon.getUuid().toString(), team);
        }
        return Unit.INSTANCE;
    }

    public static Unit terrastallizationUsed(TerastallizationEvent terastallizationEvent) {
        LivingEntity pokemon = terastallizationEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = terastallizationEvent.getPokemon().getEffectedPokemon();

        pokemon.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE, 0,false, false));

        if (pokemon.getWorld() instanceof ServerWorld serverLevel) {
            AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "terastallized");
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);

            Team team = scoreboard.getTeam(teamName);

            Formatting color = getGlowColorForTeraType(terastallizationEvent.getPokemon().getEffectedPokemon().getTeraType());
            if (team == null) {
                team = scoreboard.addTeam(teamName);
                team.setColor(color);
            }

            if(pk.getSpecies().getName().equals("Ogerpon")){
                new FlagSpeciesFeature("embody_aspect", true).apply(pk);
            }

            scoreboard.addScoreHolderToTeam(pokemon.getUuid().toString(), team);
        }

        PlayerEntity player = terastallizationEvent.getPokemon().getEffectedPokemon().getOwnerPlayer();
        AtomicReference<ItemStack> teraOrb = new AtomicReference<>(ItemStack.EMPTY); // Default to empty stack if not found

        TrinketsApi.getTrinketComponent(player).ifPresent(trinketComponent -> {
            trinketComponent.getEquipped(TeraMoves.TERA_ORB).forEach(pair -> {
                ItemStack stack = pair.getRight(); // The ItemStack of the equipped trinket
                if (!stack.isEmpty()) {
                    teraOrb.set(stack); // Assign the found stack
                }
            });
        });

        if (teraOrb.get() != null) {
            // Reduce durability by a specific amount (e.g., 10 points)
            teraOrb.get().setDamage(teraOrb.get().getDamage() + 10);
        }

        return Unit.INSTANCE;
    }

    public static Unit dropShardPokemon(LootDroppedEvent lootDroppedEvent) {
        if (!(lootDroppedEvent.getEntity() instanceof PokemonEntity)){
            return Unit.INSTANCE;
        }
        Pokemon pokemon = ((PokemonEntity) lootDroppedEvent.getEntity()).getPokemon();

        Item correspondingTeraShard = getTeraShardForType(pokemon.getTypes());

        ItemDropEntry teraShardDropEntry = new ItemDropEntry();
        teraShardDropEntry.setItem(Registries.ITEM.getId(correspondingTeraShard));

        int randomValue = new Random().nextInt(101);
        if(randomValue >= 10 && randomValue <= 20){
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
        } else if (randomValue == 33) {
            teraShardDropEntry.setItem(Registries.ITEM.getId(TeraMoves.STELLAR_TERA_SHARD));
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
        }

        return Unit.INSTANCE;
    }

    public static Unit healedPokemons(PokemonHealedEvent pokemonHealedEvent) {
        if(pokemonHealedEvent.getPokemon().getOwnerPlayer() == null){
            return Unit.INSTANCE;
        }

        PlayerEntity player = pokemonHealedEvent.getPokemon().getOwnerPlayer();
        AtomicReference<ItemStack> teraOrb = new AtomicReference<>(ItemStack.EMPTY); // Default to empty stack if not found

        TrinketsApi.getTrinketComponent(player).ifPresent(trinketComponent -> {
            trinketComponent.getEquipped(TeraMoves.TERA_ORB).forEach(pair -> {
                ItemStack stack = pair.getRight(); // The ItemStack of the equipped trinket
                if (!stack.isEmpty()) {
                    teraOrb.set(stack); // Assign the found stack
                }
            });
        });

        if (teraOrb != null) {
            teraOrb.get().setDamage(0);
        }

        return Unit.INSTANCE;
    }

    public static Unit formeChanges(FormeChangeEvent formeChangeEvent) {
        Pokemon pokemon = formeChangeEvent.getPokemon().getEffectedPokemon();

        MegaShowdown.LOGGER.info(formeChangeEvent.getFormeName());
        if(pokemon.getSpecies().getName().equals("Aegislash")){
            if(formeChangeEvent.getFormeName().equals("blade")){
                new StringSpeciesFeature("stance_forme", "blade").apply(pokemon);
            } else if (formeChangeEvent.getFormeName().equals("aegislash")) {
                new StringSpeciesFeature("stance_forme", "shield").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Minior") && formeChangeEvent.getFormeName().equals("meteor")) {
            playFormeChangeAnimation(pokemon.getEntity());
            new StringSpeciesFeature("meteor_shield", "core").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Castform")) {
            if(formeChangeEvent.getFormeName().equals("sunny")){
                playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("forecast_form", "sunny").apply(pokemon);
            } else if (formeChangeEvent.getFormeName().equals("rainy")) {
                playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("forecast_form", "rainy").apply(pokemon);
            } else if (formeChangeEvent.getFormeName().equals("snowy")){
                playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("forecast_form", "snowy").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Wishiwashi")) {
            playFormeChangeAnimation(pokemon.getEntity());
            if(formeChangeEvent.getFormeName().equals("school")){
                playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("schooling_form", "school").apply(pokemon);
            } else if (formeChangeEvent.getFormeName().equals("wishiwashi")) {
                playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("schooling_form", "solo").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Mimikyu")) {
            if(formeChangeEvent.getFormeName().equals("busted")){
                new StringSpeciesFeature("disguise_form", "busted").apply(pokemon);
            }
        }
        else if (pokemon.getSpecies().getName().equals("Greninja")) {
            if(formeChangeEvent.getFormeName().equals("ash")){
                playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("battle_bond", "ash").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Cherrim")) {
            if(formeChangeEvent.getFormeName().equals("sunshine")){
                playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("blossom_form", "sunshine").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Palafin")) {
            if(formeChangeEvent.getFormeName().equals("hero")){
                playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("dolphin_form", "hero").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Morpeko")) {
            if(formeChangeEvent.getFormeName().equals("hangry")){
                playFormeChangeAngryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("hunger_mode", "hangry").apply(pokemon);
            }
        }

        return Unit.INSTANCE;
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


    public static Unit fixOgerTera(PokemonCapturedEvent pokemonCapturedEvent) {
        Pokemon pokemon = pokemonCapturedEvent.getPokemon();

        if(pokemon.getSpecies().getName().equals("Ogerpon")){
            pokemon.setTeraType(TeraTypes.getGRASS());
        }

        return Unit.INSTANCE;
    }
}
