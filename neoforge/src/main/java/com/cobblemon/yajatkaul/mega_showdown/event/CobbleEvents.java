package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents.CobbleEventsHandler;
import com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents.RevertEvents;

public class CobbleEvents {
    public static void register(){
        CobblemonEvents.HELD_ITEM_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::onHeldItemChange);

        CobblemonEvents.POKEMON_RELEASED_EVENT_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::onReleasePokemon);

        CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, RevertEvents::devolveFainted);

        CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, RevertEvents::battleStarted);

        CobblemonEvents.MEGA_EVOLUTION.subscribe(Priority.NORMAL, CobbleEventsHandler::megaEvolution);

        CobblemonEvents.ZPOWER_USED.subscribe(Priority.NORMAL, CobbleEventsHandler::zMovesUsed);

        CobblemonEvents.TERASTALLIZATION.subscribe(Priority.NORMAL, CobbleEventsHandler::terrastallizationUsed);

        CobblemonEvents.POKEMON_HEALED.subscribe(Priority.NORMAL, CobbleEventsHandler::healedPokemons);

        CobblemonEvents.LOOT_DROPPED.subscribe(Priority.NORMAL, CobbleEventsHandler::dropShardPokemon);

        CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, CobbleEventsHandler::fixOgerTera);

        CobblemonEvents.FORME_CHANGE.subscribe(Priority.NORMAL, CobbleEventsHandler::formeChanges);
        // Battle mode only
        if(Config.battleModeOnly || Config.battleMode){
            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, RevertEvents::battleEnded);
            CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, RevertEvents::deVolveFlee);
        }
    }
}
