package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents.CobbleEventsHandler;
import com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents.RevertEvents;
import com.cobblemon.yajatkaul.mega_showdown.event.dynamax.DynamaxEventLogic;
import com.cobblemon.yajatkaul.mega_showdown.event.ultra.UltraEventLogic;

public class CobbleEvents {
    public static void register() {
        CobblemonEvents.HELD_ITEM_PRE.subscribe(Priority.NORMAL, CobbleEventsHandler::onHeldItemChangePrimals);

        CobblemonEvents.POKEMON_RELEASED_EVENT_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::onReleasePokemon);

        CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, RevertEvents::devolveFainted);

        CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, RevertEvents::battleStarted);

        CobblemonEvents.MEGA_EVOLUTION.subscribe(Priority.NORMAL, CobbleEventsHandler::megaEvolution);

        CobblemonEvents.ZPOWER_USED.subscribe(Priority.NORMAL, CobbleEventsHandler::zMovesUsed);

        CobblemonEvents.TERASTALLIZATION.subscribe(Priority.NORMAL, CobbleEventsHandler::terrastallizationUsed);

        CobblemonEvents.LOOT_DROPPED.subscribe(Priority.NORMAL, CobbleEventsHandler::dropShardPokemon);

        CobblemonEvents.POKEMON_HEALED.subscribe(Priority.NORMAL, CobbleEventsHandler::healedPokemons);

        CobblemonEvents.FORME_CHANGE.subscribe(Priority.NORMAL, CobbleEventsHandler::formeChanges);

        CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, CobbleEventsHandler::fixTeraTyping);

        CobblemonEvents.POKEMON_SENT_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::pokemonSent);

        CobblemonEvents.THROWN_POKEBALL_HIT.subscribe(Priority.NORMAL, CobbleEventsHandler::pokeballHit);

        DynamaxEventLogic.register();
        UltraEventLogic.register();

        CobblemonEvents.BATTLE_STARTED_POST.subscribe(Priority.NORMAL, RevertEvents::hookBattleEnded);
    }
}