package com.github.yajatkaul.mega_showdown.api.event;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;

public interface DynamaxEndCallback {
    Event<DynamaxEndCallback> EVENT = EventFactory.createLoop(DynamaxEndCallback.class);

    void onDynamaxEnd(PokemonBattle battle, BattlePokemon pokemon);
}
