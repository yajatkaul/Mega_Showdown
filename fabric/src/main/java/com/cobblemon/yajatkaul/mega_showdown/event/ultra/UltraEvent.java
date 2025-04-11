package com.cobblemon.yajatkaul.mega_showdown.event.ultra;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface UltraEvent {
    Event<UltraEvent> EVENT = EventFactory.createArrayBacked(UltraEvent.class,
            listeners -> (battle, pokemon) -> {
                for (UltraEvent listener : listeners) {
                    listener.onUltra(battle, pokemon);
                }
            }
    );

    void onUltra(PokemonBattle battle, BattlePokemon pokemon);
}