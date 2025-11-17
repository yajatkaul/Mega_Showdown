package com.github.yajatkaul.mega_showdown.item.custom.z;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.item.custom.form_change.FormChangeHeldItem;
import com.github.yajatkaul.mega_showdown.utils.Effect;

import java.util.List;

public class ElementalZCrystal extends FormChangeHeldItem {
    private final ElementalType element;
    private final List<String> pokemons;
    private final Effect effect;
    private final boolean tradable;

    public ElementalZCrystal(Properties properties,
                             String revertAspect,
                             String applyAspect,
                             List<String> pokemons,
                             Effect effect,
                             boolean tradable,
                             ElementalType element
    ) {
        super(properties, revertAspect, applyAspect, pokemons, effect, tradable, null, null);
        this.element = element;
        this.pokemons = pokemons;
        this.effect = effect;
        this.tradable = tradable;
    }

    @Override
    public void apply(Pokemon pokemon) {
        if (pokemons.contains(pokemon.getSpecies().getName())) {
            effect.applyEffects(pokemon, List.of(String.format("multitype=%s", this.element.getName())), null);
            if (!tradable) {
                pokemon.setTradeable(false);
            }
        }
    }

    @Override
    public void revert(Pokemon pokemon) {
        if (pokemons.contains(pokemon.getSpecies().getName())) {
            effect.revertEffects(pokemon, List.of("multitype=normal"), null);
            if (!tradable) {
                pokemon.setTradeable(true);
            }
        }
    }

    public ElementalType getElement() {
        return element;
    }
}
