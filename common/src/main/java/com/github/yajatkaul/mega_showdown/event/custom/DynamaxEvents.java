package com.github.yajatkaul.mega_showdown.event.custom;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;

public interface DynamaxEvents {
    Event<Dynamax> DYNAMAX_EVENT = EventFactory.createLoop(Dynamax.class);

    interface Dynamax {
        void onDynamaxStart(PokemonBattle battle, BattlePokemon pokemon, Boolean gmax);
        void onDynamaxEnd(PokemonBattle battle, BattlePokemon pokemon);
    }
}
