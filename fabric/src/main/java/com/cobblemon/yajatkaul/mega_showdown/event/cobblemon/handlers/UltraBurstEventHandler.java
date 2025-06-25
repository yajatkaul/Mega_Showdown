package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.UltraLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;


public class UltraBurstEventHandler {
    public static void register() {
        com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.UltraBurstEvent.EVENT.register((battle, pokemon) -> {
            new FlagSpeciesFeature("ultra", true).apply(pokemon.getEffectedPokemon());
            UltraLogic.ultraAnimation(pokemon.getEntity());

            for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()) {
                if (activeBattlePokemon.getBattlePokemon() != null &&
                        activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pokemon.getEffectedPokemon().getOwnerPlayer()
                        && activeBattlePokemon.getBattlePokemon() == pokemon) {
                    battle.sendSidedUpdate(activeBattlePokemon.getActor(),
                            new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, true),
                            new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, false),
                            false);

                }
            }

            battle.sendUpdate(new AbilityUpdatePacket(pokemon::getEffectedPokemon, pokemon.getEffectedPokemon().getAbility().getTemplate()));
            battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon.getEffectedPokemon()));

            battle.dispatchWaitingToFront(3F, () -> {
                SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                return Unit.INSTANCE;
            });
        });
    }
}