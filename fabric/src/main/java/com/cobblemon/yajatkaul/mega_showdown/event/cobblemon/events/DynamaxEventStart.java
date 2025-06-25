package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface DynamaxEventStart {
    Event<DynamaxEventStart> EVENT = EventFactory.createArrayBacked(DynamaxEventStart.class,
            listeners -> (battle, pokemon, gmax) -> {
                for (DynamaxEventStart listener : listeners) {
                    listener.onDynamax(battle, pokemon, gmax);
                }
            }
    );

    void onDynamax(PokemonBattle battle, BattlePokemon pokemon, Boolean gmax);
}