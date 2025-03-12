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
import com.cobblemon.mod.common.api.events.entity.SpawnEvent;
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
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.ZMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.TeraItem;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ZRingItem;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import kotlin.UInt;
import kotlin.Unit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.neoforged.neoforge.registries.DeferredItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.utility.TeraTypeHelper.*;

public class CobbleEventsHandler {
    public static Unit onMegaTraded(TradeCompletedEvent tradeCompletedEvent) {
        if(!Config.multipleMegas){
            ServerPlayer player1 = tradeCompletedEvent.getTradeParticipant1Pokemon().getOwnerPlayer();
            ServerPlayer player2 = tradeCompletedEvent.getTradeParticipant2Pokemon().getOwnerPlayer();

            if(player1 == null || player2 == null || player2.level().isClientSide || player1.level().isClientSide){
                return Unit.INSTANCE;
            }

            Pokemon pokemon1 = tradeCompletedEvent.getTradeParticipant1Pokemon();
            Pokemon pokemon2 = tradeCompletedEvent.getTradeParticipant2Pokemon();

            boolean mega1 = false;
            boolean mega2 = false;

            List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

            for (String key : megaKeys) {
                FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));

                FlagSpeciesFeature feature = featureProvider.get(pokemon1);
                FlagSpeciesFeature feature2 = featureProvider.get(pokemon2);
                if(feature != null){
                    boolean enabled = featureProvider.get(pokemon1).getEnabled();
                    if (enabled && feature.getName().equals("mega")) {
                        mega1 = true;
                    }else if(enabled && feature.getName().equals("mega-x")){
                        mega1 = true;
                    } else if (enabled && feature.getName().equals("mega-y")) {
                        mega1 = true;
                    }
                }

                if(feature2 != null){
                    boolean enabled = featureProvider.get(pokemon2).getEnabled();

                    if (enabled && feature2.getName().equals("mega")) {
                        mega2 = true;
                    }else if(enabled && feature2.getName().equals("mega-x")){
                        mega2 = true;
                    } else if (enabled && feature2.getName().equals("mega-y")) {
                        mega2 = true;
                    }
                }
            }

            if(mega1){
                player1.setData(DataManage.MEGA_DATA, false);
                player1.setData(DataManage.MEGA_POKEMON, new Pokemon());
                DevolveOnTrade(pokemon1);
            }
            if(mega2){
                player2.setData(DataManage.MEGA_DATA, false);
                player2.setData(DataManage.MEGA_POKEMON, new Pokemon());
                DevolveOnTrade(pokemon2);
            }
        }

        return Unit.INSTANCE;
    }

    public static void DevolveOnTrade(Pokemon pokemon){
        new FlagSpeciesFeature("mega", false).apply(pokemon);
        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
    }

    public static Unit onHeldItemChange(HeldItemEvent.Post event) {
        if(event.getReturned() == event.getReceived() || event.getPokemon().getOwnerPlayer() == null){
            return Unit.INSTANCE;
        }

        primalEvent(event);
        crownedEvent(event);

        if(Config.battleModeOnly){
            return Unit.INSTANCE;
        }

        megaEvent(event);

        return Unit.INSTANCE;
    }

    public static Unit onReleasePokemon(ReleasePokemonEvent.Post post) {
        if(post.getPlayer().level().isClientSide){
            return Unit.INSTANCE;
        }

        if(!post.getPlayer().level().isClientSide && post.getPlayer().getData(DataManage.MEGA_POKEMON) == post.getPokemon()){
            post.getPlayer().setData(DataManage.MEGA_DATA, false);
            post.getPlayer().setData(DataManage.MEGA_POKEMON, new Pokemon());
        }

        if(!post.getPlayer().level().isClientSide && post.getPlayer().getData(DataManage.PRIMAL_POKEMON) == post.getPokemon()){
            post.getPlayer().setData(DataManage.PRIMAL_DATA, false);
            post.getPlayer().setData(DataManage.PRIMAL_POKEMON, new Pokemon());
        }

        return Unit.INSTANCE;
    }

    public static void primalEvent(HeldItemEvent.Post post) {
        ServerPlayer player = post.getPokemon().getOwnerPlayer();
        Species species = post.getPokemon().getSpecies();

        if(species.getName().equals(Utils.getSpecies("kyogre").getName()) && post.getReceived().is(MegaStones.BLUE_ORB)){
            if(player.getData(DataManage.PRIMAL_DATA) && !Config.multiplePrimals){
                player.displayClientMessage(Component.literal("You can only have one primal at a time")
                        .withColor(0xFF0000), true);
                return;
            }
            new FlagSpeciesFeature("primal", true).apply(post.getPokemon());
            primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.BUBBLE);
            AdvancementHelper.grantAdvancement(player, "primal_evo");
            player.setData(DataManage.PRIMAL_DATA, true);
        }
        else if(species.getName().equals(Utils.getSpecies("groudon").getName()) && post.getReceived().is(MegaStones.RED_ORB)){
            if(player.getData(DataManage.PRIMAL_DATA) && !Config.multiplePrimals){
                player.displayClientMessage(Component.literal("You can only have one primal at a time")
                        .withColor(0xFF0000), true);
                return;
            }
            new FlagSpeciesFeature("primal", true).apply(post.getPokemon());
            primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.CAMPFIRE_COSY_SMOKE);
            AdvancementHelper.grantAdvancement(player, "primal_evo");
            player.setData(DataManage.PRIMAL_DATA, true);
        }else{
            SpeciesFeature feature = post.getPokemon().getFeature("primal");
            if(feature == null){
                return;
            }

            new FlagSpeciesFeature("primal", false).apply(post.getPokemon());
            primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.END_ROD);
            player.setData(DataManage.PRIMAL_DATA, false);
        }

        return;
    }
    public static void megaEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getEntity() == null){
            return;
        }

        Species species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());
        if(pokemon.getEntity().level().isClientSide){
            return;
        }

        List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

        for (String key : megaKeys) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
            ServerPlayer player = pokemon.getOwnerPlayer();

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
            if(event.getReceived().is(ModItems.RUSTED_SWORD) && pokemon.getSpecies().getName().equals("Zacian")){
                crownAnimation((ServerLevel) pokemon.getEntity().level(), pokemon.getEntity().getOnPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
            } else if (event.getReceived().is(ModItems.RUSTED_SHIELD) && pokemon.getSpecies().getName().equals("Zamazenta")) {
                crownAnimation((ServerLevel) pokemon.getEntity().level(), pokemon.getEntity().getOnPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
            } else if(pokemon.getSpecies().getName().equals("Zacian") && event.getReturned().is(ModItems.RUSTED_SWORD)){
                playEvolveAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
            }else if(pokemon.getSpecies().getName().equals("Zamazenta") && event.getReturned().is(ModItems.RUSTED_SHIELD)){
                playEvolveAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
            }
        }
    }

    private static void primalRevertAnimation(LivingEntity context, SimpleParticleType particleType) {
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
    private static void crownAnimation(ServerLevel level, BlockPos pos, LivingEntity context) {
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
        if (lightning != null) {
            lightning.moveTo(Vec3.atBottomCenterOf(pos));
            lightning.setVisualOnly(true);
            level.addFreshEntity(lightning);
            playEvolveAnimation(context);
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
        for(ServerPlayer player: battleEvent.getBattle().getPlayers()){
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
            checkKeldeo(playerPartyStore);

            if(Config.battleMode){
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

            boolean hasTeraItemCurios = CuriosApi.getCuriosInventory(player)
                    .map(inventory -> inventory.isEquipped(stack -> stack.getItem() instanceof TeraItem))
                    .orElse(false);

            ItemStack teraOrb = CuriosApi.getCuriosInventory(player)
                    .flatMap(curiosInventory -> curiosInventory.findFirstCurio(TeraMoves.TERA_ORB.get()))
                    .map(SlotResult::stack)
                    .orElse(null);

            if(teraOrb != null && teraOrb.getDamageValue() >= 100){
                hasTeraItemCurios = false;
            }

            if(hasTeraItemCurios && Config.teralization){
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","tera_orb"));
            }else {
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","tera_orb"));
            }

            if((Config.battleMode || Config.scuffedMode || Config.battleModeOnly) && MegaLogic.Possible(player, true) && !player.getData(DataManage.MEGA_DATA)){
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","key_stone"));
            }else{
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","key_stone"));
            }

            boolean hasZItemCurios = CuriosApi.getCuriosInventory(player)
                    .map(inventory -> inventory.isEquipped(stack -> stack.getItem() instanceof ZRingItem))
                    .orElse(false);

            if((player.getOffhandItem().is(ZMoves.Z_RING) || hasZItemCurios) && Config.zMoves){
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","z_ring"));
            }else{
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","z_ring"));
            }
        }

        return Unit.INSTANCE;
    }

    public static Unit megaEvolution(MegaEvolutionEvent megaEvolutionEvent) {
        PokemonBattle battle = megaEvolutionEvent.getBattle();
        Pokemon pokemon = megaEvolutionEvent.getPokemon().getEffectedPokemon();

        ServerPlayer player = megaEvolutionEvent.getPokemon().getOriginalPokemon().getOwnerPlayer();

        if(player == null){
            return Unit.INSTANCE;
        }

        MegaLogic.Evolve(pokemon.getEntity(), player, true);

        battle.sendUpdate(new AbilityUpdatePacket(megaEvolutionEvent.getPokemon()::getEffectedPokemon, pokemon.getAbility().getTemplate()));
        battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon));

        return Unit.INSTANCE;
    }

    public static Unit battleEnded(BattleVictoryEvent battleVictoryEvent) {
        battleVictoryEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
            for (Pokemon pokemon: playerPartyStore){
                if(pokemon.getEntity() != null){
                    pokemon.getEntity().removeEffect(MobEffects.GLOWING);
                }
            }

            for (BattlePokemon battlePokemon : battleVictoryEvent.getBattle().getActor(serverPlayer.getUUID()).getPokemonList()) {
                if (battlePokemon.getOriginalPokemon().getEntity() == null) {
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

                            if(!Config.multipleMegas){
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
        ServerPlayer serverPlayer = battleFaintedEvent.getKilled().getOriginalPokemon().getOwnerPlayer();

        if(serverPlayer == null){
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
            for (BattlePokemon battlePokemon : battleFledEvent.getBattle().getActor(serverPlayer.getUUID()).getPokemonList()) {
                if (battlePokemon.getOriginalPokemon().getEntity() == null ||
                        battlePokemon.getOriginalPokemon().getEntity().level().isClientSide) {
                    continue;
                }

                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
                for (Pokemon pokemon: playerPartyStore){
                    if(pokemon.getEntity() != null){
                        pokemon.getEntity().removeEffect(MobEffects.GLOWING);
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

                            if(!Config.multipleMegas){
                                break;
                            }
                        }
                    }
                }
            }
        });

        return Unit.INSTANCE;
    }

    public static Unit zMovesUsed(ZMoveUsedEvent zMoveUsedEvent) {
        LivingEntity pokemon = zMoveUsedEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = zMoveUsedEvent.getPokemon().getEffectedPokemon();

        pokemon.addEffect(new MobEffectInstance(MobEffects.GLOWING, 115, 0,false, false));

        if (pokemon.level() instanceof ServerLevel serverLevel) {
            AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "z_moves");
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);

            PlayerTeam team = scoreboard.getPlayerTeam(teamName);
            if (team == null) {
                team = scoreboard.addPlayerTeam(teamName);
                team.setColor(getGlowColorForType(zMoveUsedEvent.getPokemon().getOriginalPokemon()));
                team.setSeeFriendlyInvisibles(false);
                team.setAllowFriendlyFire(true);
            }

            scoreboard.addPlayerToTeam(pokemon.getScoreboardName(), team);
        }

        return Unit.INSTANCE;
    }

    public static Unit terrastallizationUsed(TerastallizationEvent terastallizationEvent) {
        LivingEntity pokemon = terastallizationEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = terastallizationEvent.getPokemon().getEffectedPokemon();

        pokemon.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0,false, false));

        if (pokemon.level() instanceof ServerLevel serverLevel) {
            AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "terastallized");
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);

            PlayerTeam team = scoreboard.getPlayerTeam(teamName);

            TeraType teraType = terastallizationEvent.getPokemon().getEffectedPokemon().getTeraType();

            ChatFormatting color = getGlowColorForTeraType(teraType);
            if (team == null) {
                team = scoreboard.addPlayerTeam(teamName);
                team.setColor(color);
                team.setSeeFriendlyInvisibles(false);
                team.setAllowFriendlyFire(true);
            }

            scoreboard.addPlayerToTeam(pokemon.getScoreboardName(), team);
        }

        ItemStack teraOrb = CuriosApi.getCuriosInventory(terastallizationEvent.getPokemon().getEffectedPokemon().getOwnerPlayer()).get().findFirstCurio(TeraMoves.TERA_ORB.get()).get().stack();

        if (teraOrb != null) {
            // Reduce durability by a specific amount (e.g., 10 points)
            teraOrb.setDamageValue(teraOrb.getDamageValue() + 10);
        }

        return Unit.INSTANCE;
    }

    public static Unit healedPokemons(PokemonHealedEvent pokemonHealedEvent) {
        if(pokemonHealedEvent.getPokemon().getOwnerPlayer() == null){
            return Unit.INSTANCE;
        }

        ItemStack teraOrb = CuriosApi.getCuriosInventory(pokemonHealedEvent.getPokemon().getOwnerPlayer())
                .flatMap(curiosInventory -> curiosInventory.findFirstCurio(TeraMoves.TERA_ORB.get()))
                .map(SlotResult::stack)  // Safely extract the stack if present
                .orElse(null);

        if (teraOrb != null) {
            teraOrb.setDamageValue(0);
        }

        return Unit.INSTANCE;
    }

    public static Unit dropShardPokemon(LootDroppedEvent lootDroppedEvent) {
        if (!(lootDroppedEvent.getEntity() instanceof PokemonEntity)){
            return Unit.INSTANCE;
        }
        Pokemon pokemon = ((PokemonEntity) lootDroppedEvent.getEntity()).getPokemon();

        DeferredItem<Item> correspondingTeraShard = getTeraShardForType(pokemon.getTypes());

        ItemDropEntry teraShardDropEntry = new ItemDropEntry();
        teraShardDropEntry.setItem(BuiltInRegistries.ITEM.getKey(correspondingTeraShard.get()));

        int randomValue = new Random().nextInt(101);
        if(randomValue >= 10 && randomValue <= 20){
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
        } else if (randomValue == 33) {
            teraShardDropEntry.setItem(BuiltInRegistries.ITEM.getKey(TeraMoves.STELLAR_TERA_SHARD.get()));
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
        }

        return Unit.INSTANCE;
    }
}
