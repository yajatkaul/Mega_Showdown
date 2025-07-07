package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.EventUtils;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.UltraLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;


public class UltraBurstEventHandler {
    public static void ultraBurstHandler(PokemonBattle battle, BattlePokemon pokemon) {
        new FlagSpeciesFeature("ultra", true).apply(pokemon.getEffectedPokemon());
        UltraLogic.ultraAnimation(pokemon.getEntity());

        EventUtils.updatePackets(battle, pokemon);

        battle.dispatchWaitingToFront(3F, () -> {
            SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
            return Unit.INSTANCE;
        });
    }
}