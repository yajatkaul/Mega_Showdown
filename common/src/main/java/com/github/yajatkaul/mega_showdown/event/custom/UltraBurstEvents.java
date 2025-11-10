package com.github.yajatkaul.mega_showdown.event.custom;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;

public interface UltraBurstEvents {
    Event<UltraBurst> ULTRA_BURST = EventFactory.createLoop(UltraBurst.class);

    @FunctionalInterface
    interface UltraBurst {
        void onUltraBurst(PokemonBattle battle, BattlePokemon pokemon);
    }
}
