package com.cobblemon.yajatkaul.mega_showdown.event.dynamax;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface DynamaxEvent {
    Event<DynamaxEvent> EVENT = EventFactory.createArrayBacked(DynamaxEvent.class,
            listeners -> (battle, pokemon, gmax) -> {
                for (DynamaxEvent listener : listeners) {
                    listener.onDynamax(battle, pokemon, gmax);
                }
            }
    );

    void onDynamax(PokemonBattle battle, BattlePokemon pokemon, Boolean gmax);
}