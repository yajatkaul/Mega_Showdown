package com.cobblemon.yajatkaul.mega_showdown.event.Dynamax;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface DynamaxEventEnd {
    Event<DynamaxEventEnd> EVENT = EventFactory.createArrayBacked(DynamaxEventEnd.class,
            listeners -> (battle, pokemon) -> {
                for (DynamaxEventEnd listener : listeners) {
                    listener.onDynamaxEnd(battle, pokemon);
                }
            }
    );

    void onDynamaxEnd(PokemonBattle battle, BattlePokemon pokemon);
}