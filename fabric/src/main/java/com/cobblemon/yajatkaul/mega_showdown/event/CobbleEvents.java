package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents.CobbleEventHandler;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;

public class CobbleEvents {
    public static void register(){
        CobblemonEvents.HELD_ITEM_POST.subscribe(Priority.NORMAL, CobbleEventHandler::onHeldItemChange);

        CobblemonEvents.POKEMON_RELEASED_EVENT_POST.subscribe(Priority.NORMAL, CobbleEventHandler::onReleasePokemon);

        CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, CobbleEventHandler::devolveFainted);

        CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, CobbleEventHandler::battleStarted);

        CobblemonEvents.MEGA_EVOLUTION.subscribe(Priority.NORMAL, CobbleEventHandler::megaEvolution);

        CobblemonEvents.ZPOWER_USED.subscribe(Priority.NORMAL, CobbleEventHandler::zMovesUsed);

        CobblemonEvents.TERASTALLIZATION.subscribe(Priority.NORMAL, CobbleEventHandler::terrastallizationUsed);

        CobblemonEvents.LOOT_DROPPED.subscribe(Priority.NORMAL, CobbleEventHandler::dropShardPokemon);

        CobblemonEvents.POKEMON_HEALED.subscribe(Priority.NORMAL, CobbleEventHandler::healedPokemons);

        CobblemonEvents.FORME_CHANGE.subscribe(Priority.NORMAL, CobbleEventHandler::formeChanges);

        CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, CobbleEventHandler::fixOgerTera);

        if(ShowdownConfig.battleModeOnly.get() || ShowdownConfig.battleMode.get()){
            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, CobbleEventHandler::getBattleEndInfo);
            CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, CobbleEventHandler::deVolveFlee);
        }
    }
}
