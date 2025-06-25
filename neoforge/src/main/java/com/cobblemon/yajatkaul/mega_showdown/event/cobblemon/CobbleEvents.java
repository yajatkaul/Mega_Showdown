package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers.CobbleEventsHandler;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers.DynamaxEventHandler;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers.RevertEventsHandler;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers.UltraBurstEventHandler;
import net.neoforged.neoforge.common.NeoForge;

public class CobbleEvents {
    public static void register() {
        CobblemonEvents.HELD_ITEM_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::onHeldItemChange);
        CobblemonEvents.HELD_ITEM_PRE.subscribe(Priority.NORMAL, CobbleEventsHandler::onHeldItemChangePrimals);

        CobblemonEvents.POKEMON_RELEASED_EVENT_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::onReleasePokemon);

        CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, RevertEventsHandler::devolveFainted);

        CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, RevertEventsHandler::battleStarted);
        CobblemonEvents.BATTLE_STARTED_POST.subscribe(Priority.NORMAL, RevertEventsHandler::hookBattleEnded);

        CobblemonEvents.MEGA_EVOLUTION.subscribe(Priority.NORMAL, CobbleEventsHandler::megaEvolution);

        CobblemonEvents.ZPOWER_USED.subscribe(Priority.NORMAL, CobbleEventsHandler::zMovesUsed);

        CobblemonEvents.TERASTALLIZATION.subscribe(Priority.NORMAL, CobbleEventsHandler::terrastallizationUsed);

        CobblemonEvents.POKEMON_HEALED.subscribe(Priority.NORMAL, CobbleEventsHandler::healedPokemons);

        CobblemonEvents.LOOT_DROPPED.subscribe(Priority.NORMAL, CobbleEventsHandler::dropShardPokemon);

        CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, CobbleEventsHandler::fixTera);

        CobblemonEvents.FORME_CHANGE.subscribe(Priority.NORMAL, CobbleEventsHandler::formeChanges);

        CobblemonEvents.POKEMON_SENT_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::pokemonSent);

        CobblemonEvents.THROWN_POKEBALL_HIT.subscribe(Priority.NORMAL, CobbleEventsHandler::pokeballHit);

        NeoForge.EVENT_BUS.register(new DynamaxEventHandler());
        NeoForge.EVENT_BUS.register(new UltraBurstEventHandler());
    }
}
