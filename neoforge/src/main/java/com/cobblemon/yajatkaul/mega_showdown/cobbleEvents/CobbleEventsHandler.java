package com.cobblemon.yajatkaul.mega_showdown.cobbleEvents;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.events.battles.*;
import com.cobblemon.mod.common.api.events.battles.instruction.MegaEvolutionEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.events.pokemon.TradeCompletedEvent;
import com.cobblemon.mod.common.api.events.storage.ReleasePokemonEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import kotlin.Unit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import javax.xml.crypto.Data;
import java.util.List;

import static net.minecraft.Util.NIL_UUID;

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
        if(Config.battleModeOnly){
            return Unit.INSTANCE;
        }

        Pokemon pokemon = event.getPokemon();

        if(pokemon.getEntity() == null){
            return Unit.INSTANCE;
        }

        Species species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());
        if(pokemon.getEntity().level().isClientSide){
            return Unit.INSTANCE;
        }

        List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

        for (String key : megaKeys) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
            ServerPlayer player = pokemon.getOwnerPlayer();

            FlagSpeciesFeature feature = featureProvider.get(pokemon);
            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if (enabled && feature.getName().equals("mega") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())) {
                    player.setData(DataManage.MEGA_DATA, false);
                    player.setData(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega", false).apply(pokemon);

                }else if(enabled && feature.getName().equals("mega-x") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())){
                    player.setData(DataManage.MEGA_DATA, false);
                    player.setData(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);

                } else if (enabled && feature.getName().equals("mega-y") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())) {
                    player.setData(DataManage.MEGA_DATA, false);
                    player.setData(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                }
            }
        }


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

    public static Unit primalEvent(HeldItemEvent.Post post) {
        if(post.getReturned() == post.getReceived() || post.getPokemon().getOwnerPlayer() == null){
            return Unit.INSTANCE;
        }

        ServerPlayer player = post.getPokemon().getOwnerPlayer();
        Species species = post.getPokemon().getSpecies();

        if(species.getName().equals(Utils.getSpecies("kyogre").getName()) && post.getReceived().is(MegaStones.BLUE_ORB)){
            if(player.getData(DataManage.PRIMAL_DATA) && !Config.multiplePrimals){
                player.displayClientMessage(Component.literal("You can only have one primal at a time")
                        .withColor(0xFF0000), true);
                return Unit.INSTANCE;
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
                return Unit.INSTANCE;
            }
            new FlagSpeciesFeature("primal", true).apply(post.getPokemon());
            primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.CAMPFIRE_COSY_SMOKE);
            AdvancementHelper.grantAdvancement(player, "primal_evo");
            player.setData(DataManage.PRIMAL_DATA, true);
        }else{
            SpeciesFeature feature = post.getPokemon().getFeature("primal");
            if(feature == null){
                return Unit.INSTANCE;
            }

            new FlagSpeciesFeature("primal", false).apply(post.getPokemon());
            primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.END_ROD);
            player.setData(DataManage.PRIMAL_DATA, false);
        }

        return Unit.INSTANCE;
    }

    public static void primalRevertAnimation(LivingEntity context, SimpleParticleType particleType) {
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


    public static Unit battleStarted(BattleStartedPreEvent battleEvent) {
        for(ServerPlayer player: battleEvent.getBattle().getPlayers()){
            if(Config.battleMode){
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

                for (Pokemon pokemon : playerPartyStore) {
                    if(pokemon.getSpecies().getName().equals("rayquaza")){
                        continue;
                    }
                    List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

                    for (String key : megaKeys) {
                        FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
                        FlagSpeciesFeature feature = featureProvider.get(pokemon);

                        if(feature != null){
                            boolean enabled = featureProvider.get(pokemon).getEnabled();

                            if(enabled){
                                MegaLogic.Devolve(pokemon.getEntity(), player, true);

                                if(!Config.multipleMegas){
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);

            if((Config.battleMode || Config.scuffedMode) && MegaLogic.Possible(player, true) && !player.getData(DataManage.MEGA_DATA)){
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","key_stone"));
            }else{
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","key_stone"));
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

        MegaLogic.Devolve(pokemon.getEntity(), serverPlayer, true);

        return Unit.INSTANCE;
    }

    public static Unit deVolveFlee(BattleFledEvent battleFledEvent) {
        battleFledEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            for (BattlePokemon battlePokemon : battleFledEvent.getBattle().getActor(serverPlayer.getUUID()).getPokemonList()) {
                if (battlePokemon.getOriginalPokemon().getEntity() == null ||
                        battlePokemon.getOriginalPokemon().getEntity().level().isClientSide) {
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
}
