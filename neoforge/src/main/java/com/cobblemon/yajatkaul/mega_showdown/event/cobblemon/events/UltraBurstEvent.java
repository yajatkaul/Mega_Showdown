package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import net.neoforged.bus.api.Event;

public class UltraBurstEvent extends Event {
    private final PokemonBattle battle;
    private final BattlePokemon pokemon;

    public UltraBurstEvent(PokemonBattle battle, BattlePokemon pokemon) {
        this.battle = battle;
        this.pokemon = pokemon;
    }

    public PokemonBattle getBattle() {
        return battle;
    }

    public BattlePokemon getPokemon() {
        return pokemon;
    }
}