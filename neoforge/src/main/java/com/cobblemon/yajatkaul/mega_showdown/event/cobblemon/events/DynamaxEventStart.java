package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import net.neoforged.bus.api.Event;


public class DynamaxEventStart extends Event {
    private final PokemonBattle battle;
    private final BattlePokemon pokemon;
    private final Boolean gmax;

    public DynamaxEventStart(PokemonBattle battle, BattlePokemon pokemon, Boolean gmax) {
        this.battle = battle;
        this.pokemon = pokemon;
        this.gmax = gmax;
    }

    public PokemonBattle getBattle() {
        return battle;
    }

    public BattlePokemon getPokemon() {
        return pokemon;
    }

    public Boolean getGmax() {
        return gmax;
    }
}