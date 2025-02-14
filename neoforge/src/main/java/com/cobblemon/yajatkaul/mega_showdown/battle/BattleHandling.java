package com.cobblemon.yajatkaul.mega_showdown.battle;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent;
import com.cobblemon.mod.common.api.events.battles.BattleFledEvent;
import com.cobblemon.mod.common.api.events.battles.BattleStartedPostEvent;
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.battles.*;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.*;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import kotlin.Unit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.Util.NIL_UUID;

public class BattleHandling {
    public static List<Pokemon> battlePokemonUsed = new ArrayList<>();

    public static Unit getBattleInfo(BattleStartedPostEvent battleStartedPostEvent) {
        PokemonBattle battle = battleStartedPostEvent.getBattle();

        battlePokemonUsed.clear();

        for (ServerPlayer player: battle.getPlayers()){
            player.setData(DataManage.BATTLE_ID, battle.getBattleId());
            player.removeData(DataManage.MEGA_DATA);
        }
        return Unit.INSTANCE;
    }

    private static void broadCastEvoMsg(BattlePokemon battlePokemon, PokemonBattle battle) {
        battle.sendUpdate(new AbilityUpdatePacket(battlePokemon::getEffectedPokemon, battlePokemon.getEffectedPokemon().getAbility().getTemplate()));
        battle.sendUpdate(new BattleUpdateTeamPokemonPacket(battlePokemon.getEffectedPokemon()));
        
        MutableComponent message = Component.literal(battlePokemon.getName().getString())
                .withStyle(style -> style.withColor(ChatFormatting.GOLD))
                .append(" ")
                .append(Component.literal(battlePokemon.getOriginalPokemon().getAbility().getName())
                        .withStyle(style -> style.withColor(ChatFormatting.GOLD)))
                .append(" activated!");

        battle.broadcastChatMessage(message);

        String translatedDescription = Component.translatable(battlePokemon.getOriginalPokemon().getAbility().getDescription()).getString();

        battle.broadcastChatMessage(
                Component.literal(translatedDescription)
                        .withStyle(style -> style.withColor(ChatFormatting.WHITE))
        );
    }

    public static void handleMegaEvolution(IPayloadContext userContext){
        ServerPlayer player = (ServerPlayer) userContext.player();

        if(player.getData(DataManage.BATTLE_ID) == NIL_UUID){
            return;
        }

        PokemonBattle battle = BattleRegistry.INSTANCE.getBattle(player.getData(DataManage.BATTLE_ID));

        List<ShowdownActionResponse> skipTurn = Collections.singletonList(new ShowdownActionResponse(ShowdownActionResponseType.FORCE_PASS) {
            @NotNull
            @Override
            public String toShowdownString(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveset) {
                return "pass"; // Or whatever the correct format for passing is in your Showdown implementation
            }

            @Override
            public boolean isValid(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveset, boolean forceSwitch) {
                return true; // This action should be valid when we want to force a pass
            }
        });

        battle.getPlayers().forEach(serverPlayer -> {
            for (BattlePokemon battlePokemon : battle.getActor(serverPlayer.getUUID()).getPokemonList()) {
                if (battlePokemon.getOriginalPokemon().getEntity() == null) {
                    continue; // Skip to the next iteration
                }

                Pokemon pokemon = battlePokemon.getOriginalPokemon();

                if(battlePokemonUsed.contains(pokemon)){
                    serverPlayer.displayClientMessage(Component.literal("Pokemon already mega'ed")
                            .withColor(0xFF0000), true);
                    continue;
                }

                Species species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());

                if (pokemon.getEntity().isBattling() && species.getName().equals(pokemon.getSpecies().getName()) &&
                        (!serverPlayer.getData(DataManage.MEGA_DATA) || Config.multipleMegas)) {

                    if (species.getName().equals(Utils.getSpecies("charizard").getName())) {
                        if (pokemon.heldItem().is(ModItems.CHARIZARDITE_X)) {
                            serverPlayer.setData(DataManage.MEGA_DATA, true);
                            serverPlayer.setData(DataManage.MEGA_POKEMON, pokemon);

                            new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                            new FlagSpeciesFeature("mega-x", true).apply(pokemon);

                            if(Config.megaTurns){
                                battle.getActor(serverPlayer).setActionResponses(skipTurn);
                            }
                            broadCastEvoMsg(battlePokemon, battle);
                            battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                            break;
                        } else if (pokemon.heldItem().is(ModItems.CHARIZARDITE_Y)) {
                            serverPlayer.setData(DataManage.MEGA_DATA, true);
                            serverPlayer.setData(DataManage.MEGA_POKEMON, pokemon);

                            new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                            new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                            if(Config.megaTurns){
                                battle.getActor(serverPlayer).setActionResponses(skipTurn);
                            }
                            battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                            broadCastEvoMsg(battlePokemon, battle);
                            break;
                        }
                    } else if (species.getName().equals(Utils.getSpecies("mewtwo").getName())) {
                        if (pokemon.heldItem().is(ModItems.MEWTWONITE_X)) {
                            serverPlayer.setData(DataManage.MEGA_DATA, true);
                            serverPlayer.setData(DataManage.MEGA_POKEMON, pokemon);

                            new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                            new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                            if(Config.megaTurns){
                                battle.getActor(serverPlayer).setActionResponses(skipTurn);
                            }
                            battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                            broadCastEvoMsg(battlePokemon, battle);
                            break;
                        } else if (pokemon.heldItem().is(ModItems.MEWTWONITE_Y)) {
                            serverPlayer.setData(DataManage.MEGA_DATA, true);
                            serverPlayer.setData(DataManage.MEGA_POKEMON, pokemon);

                            new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                            new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                            if(Config.megaTurns){
                                battle.getActor(serverPlayer).setActionResponses(skipTurn);
                            }
                            battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                            broadCastEvoMsg(battlePokemon, battle);
                            break;
                        }
                    } else {
                        serverPlayer.setData(DataManage.MEGA_DATA, true);
                        serverPlayer.setData(DataManage.MEGA_POKEMON, pokemon);

                        new FlagSpeciesFeature("mega", true).apply(pokemon);

                        if(Config.megaTurns){
                            battle.getActor(serverPlayer).setActionResponses(skipTurn);
                        }
                        battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                        broadCastEvoMsg(battlePokemon, battle);
                        break;
                    }
                } else if (pokemon.getSpecies().getName().equals(Utils.getSpecies("rayquaza").getName()) &&
                        (!serverPlayer.getData(DataManage.MEGA_DATA) || Config.multipleMegas)) {
                    boolean found = false;
                    for (int i = 0; i < 4; i++) {
                        if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {
                            serverPlayer.setData(DataManage.MEGA_POKEMON, pokemon);
                            serverPlayer.setData(DataManage.MEGA_DATA, true);

                            new FlagSpeciesFeature("mega", true).apply(pokemon);
                            found = true;

                            if(Config.megaTurns){
                                battle.getActor(serverPlayer).setActionResponses(skipTurn);
                            }
                            battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                            broadCastEvoMsg(battlePokemon, battle);
                            break;
                        }
                    }
                    if(!found){
                        ((Player) serverPlayer).displayClientMessage(Component.literal("Rayquaza doesn't have dragonascent")
                                .withColor(0xFF0000), true);
                    }
                } else if (species.getName().equals(pokemon.getSpecies().getName()) && serverPlayer.getData(DataManage.MEGA_DATA)) {
                    ((Player) serverPlayer).displayClientMessage(Component.literal("You can only have one mega at a time")
                            .withColor(0xFF0000), true);
                } else {
                    ((Player) serverPlayer).displayClientMessage(Component.literal("Don't have the correct stone")
                            .withColor(0xFF0000), true);
                }
            }

        });
    }

    public static Unit getBattleEndInfo(BattleVictoryEvent battleVictoryEvent) {
        battleVictoryEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            for (BattlePokemon battlePokemon : battleVictoryEvent.getBattle().getActor(serverPlayer.getUUID()).getPokemonList()) {
                if (battlePokemon.getOriginalPokemon().getEntity() == null) {
                    continue;
                }

                Pokemon pokemon = battlePokemon.getOriginalPokemon();

                serverPlayer.setData(DataManage.MEGA_DATA, false);
                serverPlayer.setData(DataManage.MEGA_POKEMON, new Pokemon());

                new FlagSpeciesFeature("mega", false).apply(pokemon);
                new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                new FlagSpeciesFeature("mega-y", false).apply(pokemon);
            }
            serverPlayer.setData(DataManage.BATTLE_ID, NIL_UUID);
        });

        battlePokemonUsed.clear();
        return Unit.INSTANCE;
    }

    public static Unit devolveFainted(BattleFaintedEvent battleFaintedEvent) {
        Pokemon pokemon = battleFaintedEvent.getKilled().getOriginalPokemon();
        ServerPlayer serverPlayer = battleFaintedEvent.getKilled().getOriginalPokemon().getOwnerPlayer();

        if(serverPlayer == null){
            return Unit.INSTANCE;
        }

        serverPlayer.setData(DataManage.MEGA_DATA, false);
        serverPlayer.setData(DataManage.MEGA_POKEMON, new Pokemon());

        new FlagSpeciesFeature("mega", false).apply(pokemon);
        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
        new FlagSpeciesFeature("mega-y", false).apply(pokemon);

        return Unit.INSTANCE;
    }

    public static Unit deVolveFlee(BattleFledEvent battleFledEvent) {
        battleFledEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            for (BattlePokemon battlePokemon : battleFledEvent.getBattle().getActor(serverPlayer.getUUID()).getPokemonList()) {
                if (battlePokemon.getOriginalPokemon().getEntity() == null ||
                        battlePokemon.getOriginalPokemon().getEntity().level().isClientSide) {
                    continue;
                }

                serverPlayer.setData(DataManage.MEGA_DATA, false);
                serverPlayer.setData(DataManage.MEGA_POKEMON, new Pokemon());

                Pokemon pokemon = battlePokemon.getOriginalPokemon();

                new FlagSpeciesFeature("mega", false).apply(pokemon);
                new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                new FlagSpeciesFeature("mega-y", false).apply(pokemon);
            }

            serverPlayer.setData(DataManage.BATTLE_ID, NIL_UUID);
        });

        battlePokemonUsed.clear();
        return Unit.INSTANCE;
    }
}