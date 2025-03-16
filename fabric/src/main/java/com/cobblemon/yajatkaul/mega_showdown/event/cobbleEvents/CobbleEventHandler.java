package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.drop.ItemDropEntry;
import com.cobblemon.mod.common.api.events.battles.*;
import com.cobblemon.mod.common.api.events.battles.instruction.MegaEvolutionEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.TerastallizationEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.ZMoveUsedEvent;
import com.cobblemon.mod.common.api.events.drops.LootDroppedEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.events.pokemon.TradeCompletedEvent;
import com.cobblemon.mod.common.api.events.pokemon.healing.PokemonHealedEvent;
import com.cobblemon.mod.common.api.events.storage.ReleasePokemonEvent;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
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
import com.cobblemon.yajatkaul.mega_showdown.item.custom.TeraItem;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ZRingItem;
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

        checkUltra(post);
        primalEvent(post);
        crownedEvent(post);
        ogerponChange(post);
        eternamaxChange(post);

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
            new FlagSpeciesFeature("hearthflame-mask", true).apply(pokemon);
            new FlagSpeciesFeature("wellspring-mask", false).apply(pokemon);
            new FlagSpeciesFeature("cornerstone-mask", false).apply(pokemon);

            pokemon.setTeraType(TeraTypes.getFIRE());
        } else if (post.getReceived().isOf(ModItems.CORNERSTONE_MASK)) {
            new FlagSpeciesFeature("cornerstone-mask", true).apply(pokemon);
            new FlagSpeciesFeature("wellspring-mask", false).apply(pokemon);
            new FlagSpeciesFeature("hearthflame-mask", false).apply(pokemon);

            pokemon.setTeraType(TeraTypes.getGROUND());
        }else if (post.getReceived().isOf(ModItems.WELLSPRING_MASK)) {
            new FlagSpeciesFeature("wellspring-mask", true).apply(pokemon);
            new FlagSpeciesFeature("cornerstone-mask", false).apply(pokemon);
            new FlagSpeciesFeature("hearthflame-mask", false).apply(pokemon);

            pokemon.setTeraType(TeraTypes.getWATER());
        }else {
            new FlagSpeciesFeature("wellspring-mask", false).apply(pokemon);
            new FlagSpeciesFeature("cornerstone-mask", false).apply(pokemon);
            new FlagSpeciesFeature("hearthflame-mask", false).apply(pokemon);

            pokemon.setTeraType(TeraTypes.getGRASS());
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

    public static Unit getBattleEndInfo(BattleVictoryEvent battleVictoryEvent) {
        battleVictoryEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
            for (Pokemon pokemon: playerPartyStore){
                if(pokemon.getEntity() != null){
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
}
