package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents.CobbleEventsHandler;

public class CobbleEvents {
    public static void register(){
        CobblemonEvents.HELD_ITEM_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::onHeldItemChange);

        CobblemonEvents.POKEMON_RELEASED_EVENT_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::onReleasePokemon);

        CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, CobbleEventsHandler::devolveFainted);

        CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, CobbleEventsHandler::battleStarted);

        CobblemonEvents.MEGA_EVOLUTION.subscribe(Priority.NORMAL, CobbleEventsHandler::megaEvolution);

        CobblemonEvents.ZPOWER_USED.subscribe(Priority.NORMAL, CobbleEventsHandler::zMovesUsed);

        CobblemonEvents.TERASTALLIZATION.subscribe(Priority.NORMAL, CobbleEventsHandler::terrastallizationUsed);

        CobblemonEvents.POKEMON_HEALED.subscribe(Priority.NORMAL, CobbleEventsHandler::healedPokemons);

        CobblemonEvents.LOOT_DROPPED.subscribe(Priority.NORMAL, CobbleEventsHandler::dropShardPokemon);
        // Battle mode only
        if(Config.battleModeOnly || Config.battleMode){
            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, CobbleEventsHandler::battleEnded);
            CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, CobbleEventsHandler::deVolveFlee);
        }
    }
}
