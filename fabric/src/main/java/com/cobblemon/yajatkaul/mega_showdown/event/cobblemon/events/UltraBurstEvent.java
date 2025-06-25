package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface UltraBurstEvent {
    Event<UltraBurstEvent> EVENT = EventFactory.createArrayBacked(UltraBurstEvent.class,
            listeners -> (battle, pokemon) -> {
                for (UltraBurstEvent listener : listeners) {
                    listener.onUltra(battle, pokemon);
                }
            }
    );

    void onUltra(PokemonBattle battle, BattlePokemon pokemon);
}