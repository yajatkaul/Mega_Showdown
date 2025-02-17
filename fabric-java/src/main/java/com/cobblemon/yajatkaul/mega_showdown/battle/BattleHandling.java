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
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import kotlin.Unit;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BattleHandling {
    public static List<Pokemon> battlePokemonUsed = new ArrayList<>();

    private static void broadCastEvoMsg(BattlePokemon battlePokemon, PokemonBattle battle, ServerPlayerEntity player){
        battle.sendUpdate(new AbilityUpdatePacket(battlePokemon::getEffectedPokemon, battlePokemon.getEffectedPokemon().getAbility().getTemplate()));
        battle.sendUpdate(new BattleUpdateTeamPokemonPacket(battlePokemon.getEffectedPokemon()));

        AdvancementHelper.grantAdvancement(player, "mega_evolve");

        battle.broadcastChatMessage(
                Text.literal(battlePokemon.getName().getString())
                        .styled(style -> style.withColor(Formatting.GOLD))
                        .append(" ")
                        .append(Text.literal(battlePokemon.getOriginalPokemon().getAbility().getName())
                                .styled(style -> style.withColor(Formatting.GOLD)))
                        .append(" activated!")
        );

        String translatedDescription = Text.translatable(battlePokemon.getOriginalPokemon().getAbility().getDescription()).getString();

        battle.broadcastChatMessage(
                Text.literal(translatedDescription)
                        .styled(style -> style.withColor(Formatting.WHITE))
        );
    }

    public static void handleMegaEvolution(ServerPlayNetworking.Context playerContext) {
        ServerPlayerEntity serverPlayer = playerContext.player();
        if(serverPlayer.getAttached(DataManage.MEGA_DATA) == null){
            serverPlayer.setAttached(DataManage.MEGA_DATA, false);
        }

        List<ShowdownActionResponse> skipTurn = Collections.singletonList(new ShowdownActionResponse(ShowdownActionResponseType.FORCE_PASS) {
            @NotNull
            @Override
            public String toShowdownString(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveset) {
                return "pass";
            }

            @Override
            public boolean isValid(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveset, boolean forceSwitch) {
                return true;
            }
        });

        if(serverPlayer.getAttached(DataManage.BATTLE_ID) == null){
            return;
        }

        PokemonBattle battle = BattleRegistry.INSTANCE.getBattle(serverPlayer.getAttached(DataManage.BATTLE_ID));

        for (BattlePokemon battlePokemon : battle.getActor(serverPlayer.getUuid()).getPokemonList()) {
            if (battlePokemon.getOriginalPokemon().getEntity() == null ||
                    battlePokemon.getOriginalPokemon().getEntity().getWorld().isClient) {
                continue;
            }

            if(battlePokemonUsed.contains(battlePokemon.getOriginalPokemon())){
                serverPlayer.sendMessage(
                        Text.literal("Pokemon already mega'ed").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                continue;
            }

            Pokemon pokemon = battlePokemon.getOriginalPokemon();
            Species species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());

            if (pokemon.getSpecies().getName().equals(Utils.getSpecies("rayquaza").getName()) &&
                    (!serverPlayer.getAttached(DataManage.MEGA_DATA) || ShowdownConfig.multipleMegas.get())) {
                boolean found = false;
                for (int i = 0; i < 4; i++) {
                    if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {
                        serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);
                        serverPlayer.setAttached(DataManage.MEGA_DATA, true);

                        new FlagSpeciesFeature("mega", true).apply(pokemon);
                        found = true;

                        //Mega turn
                        if(ShowdownConfig.megaTurns.get()){
                            battle.getActor(serverPlayer).setActionResponses(skipTurn);
                        }
                        broadCastEvoMsg(battlePokemon, battle, serverPlayer);
                        battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                        break;
                    }
                }
                if(!found){
                    serverPlayer.sendMessage(
                            Text.literal("Rayquaza doesn't have dragonascent").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                            true
                    );
                }
                return;
            }else if (pokemon.getSpecies().getName().equals(Utils.getSpecies("rayquaza").getName()) && serverPlayer.getAttached(DataManage.MEGA_DATA)) {
                serverPlayer.sendMessage(
                        Text.literal("You can only have one mega at a time").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return;
            }

            if (pokemon.getEntity().isBattling() && species.getName().equals(pokemon.getSpecies().getName()) &&
                    // Multiple megas
                    (!serverPlayer.getAttached(DataManage.MEGA_DATA) || ShowdownConfig.multipleMegas.get())) {

                if (species.getName().equals(Utils.getSpecies("charizard").getName())) {
                    if (pokemon.heldItem().isOf(ModItems.CHARIZARDITE_X)) {
                        serverPlayer.setAttached(DataManage.MEGA_DATA, true);
                        serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);

                        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-x", true).apply(pokemon);


                        //Mega turn
                        if(ShowdownConfig.megaTurns.get()){
                            battle.getActor(serverPlayer).setActionResponses(skipTurn);
                        }

                        broadCastEvoMsg(battlePokemon, battle, serverPlayer);
                        battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                        break;
                    } else if (pokemon.heldItem().isOf(ModItems.CHARIZARDITE_Y)) {
                        serverPlayer.setAttached(DataManage.MEGA_DATA, true);
                        serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);

                        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-y", true).apply(pokemon);

                        //Mega turn
                        if(ShowdownConfig.megaTurns.get()){
                            battle.getActor(serverPlayer).setActionResponses(skipTurn);
                        }
                        broadCastEvoMsg(battlePokemon, battle, serverPlayer);
                        battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                        break;
                    }
                } else if (species.getName().equals(Utils.getSpecies("mewtwo").getName())) {
                    if (pokemon.heldItem().isOf(ModItems.MEWTWONITE_X)) {
                        serverPlayer.setAttached(DataManage.MEGA_DATA, true);
                        serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);

                        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                        //Mega turn
                        if(ShowdownConfig.megaTurns.get()){
                            battle.getActor(serverPlayer).setActionResponses(skipTurn);
                        }
                        broadCastEvoMsg(battlePokemon, battle, serverPlayer);
                        battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                        break;
                    } else if (pokemon.heldItem().isOf(ModItems.MEWTWONITE_Y)) {
                        serverPlayer.setAttached(DataManage.MEGA_DATA, true);
                        serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);

                        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                        //Mega turn
                        if(ShowdownConfig.megaTurns.get()){
                            battle.getActor(serverPlayer).setActionResponses(skipTurn);
                        }
                        broadCastEvoMsg(battlePokemon, battle, serverPlayer);
                        battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                        break;
                    }
                } else {
                    serverPlayer.setAttached(DataManage.MEGA_DATA, true);
                    serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega", true).apply(pokemon);

                    //Mega turn
                    if(ShowdownConfig.megaTurns.get()){
                        battle.getActor(serverPlayer).setActionResponses(skipTurn);
                    }
                    broadCastEvoMsg(battlePokemon, battle, serverPlayer);
                    battlePokemonUsed.add(battlePokemon.getOriginalPokemon());
                    break;
                }
            } else if (species.getName().equals(pokemon.getSpecies().getName()) && serverPlayer.getAttached(DataManage.MEGA_DATA)) {
                serverPlayer.sendMessage(
                        Text.literal("You can only have one mega at a time").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            } else {
                serverPlayer.sendMessage(
                        Text.literal("Don't have the correct stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }
        }
    }

    public static Unit getBattleInfo(BattleStartedPostEvent battleStartedPostEvent) {
        PokemonBattle battle = battleStartedPostEvent.getBattle();

        battlePokemonUsed.clear();

        for (ServerPlayerEntity player: battle.getPlayers()){
            player.setAttached(DataManage.BATTLE_ID, battle.getBattleId());
            player.removeAttached(DataManage.MEGA_DATA);
        }

        return Unit.INSTANCE;
    }

    public static Unit getBattleEndInfo(BattleVictoryEvent battleVictoryEvent) {

        battleVictoryEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            for (BattlePokemon battlePokemon : battleVictoryEvent.getBattle().getActor(serverPlayer.getUuid()).getPokemonList()) {
                if (battlePokemon.getOriginalPokemon().getEntity() == null ||
                        battlePokemon.getOriginalPokemon().getEntity().getWorld().isClient) {
                    continue;
                }

                Pokemon pokemon = battlePokemon.getOriginalPokemon();

                new FlagSpeciesFeature("mega", false).apply(pokemon);
                new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                new FlagSpeciesFeature("mega-y", false).apply(pokemon);
            }

            serverPlayer.setAttached(DataManage.MEGA_DATA, false);
            serverPlayer.setAttached(DataManage.MEGA_POKEMON, null);
            serverPlayer.setAttached(DataManage.BATTLE_ID, null);
        });

        battlePokemonUsed.clear();
        return Unit.INSTANCE;
    }

    public static Unit devolveFainted(BattleFaintedEvent battleFaintedEvent) {
        Pokemon pokemon = battleFaintedEvent.getKilled().getOriginalPokemon();
        ServerPlayerEntity serverPlayer = battleFaintedEvent.getKilled().getOriginalPokemon().getOwnerPlayer();

        if(serverPlayer == null || serverPlayer.getWorld().isClient){
            return Unit.INSTANCE;
        }

        serverPlayer.setAttached(DataManage.MEGA_DATA, false);
        serverPlayer.setAttached(DataManage.MEGA_POKEMON, null);

        new FlagSpeciesFeature("mega", false).apply(pokemon);
        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
        new FlagSpeciesFeature("mega-y", false).apply(pokemon);

        return Unit.INSTANCE;
    }

    public static Unit deVolveFlee(BattleFledEvent battleFledEvent) {

        battleFledEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            for (BattlePokemon battlePokemon : battleFledEvent.getBattle().getActor(serverPlayer.getUuid()).getPokemonList()) {
                if (battlePokemon.getOriginalPokemon().getEntity() == null ||
                        battlePokemon.getOriginalPokemon().getEntity().getWorld().isClient) {
                    continue;
                }

                serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                serverPlayer.setAttached(DataManage.MEGA_POKEMON, null);

                Pokemon pokemon = battlePokemon.getOriginalPokemon();

                new FlagSpeciesFeature("mega", false).apply(pokemon);
                new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                new FlagSpeciesFeature("mega-y", false).apply(pokemon);
            }

            serverPlayer.setAttached(DataManage.BATTLE_ID, null);
        });

        battlePokemonUsed.clear();
        return Unit.INSTANCE;
    }
}