package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface UltraBurstEventStart {
    Event<UltraBurstEventStart> EVENT = EventFactory.createArrayBacked(UltraBurstEventStart.class,
            listeners -> (battle, pokemon) -> {
                for (UltraBurstEventStart listener : listeners) {
                    listener.onUltra(battle, pokemon);
                }
            }
    );

    void onUltra(PokemonBattle battle, BattlePokemon pokemon);
}