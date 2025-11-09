package com.github.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import com.github.yajatkaul.mega_showdown.utils.Effect;

import java.util.List;

public class FormChangeItem extends ToolTipItem {
    private final String revertAspect;
    private final String applyAspect;
    private final List<String> pokemons;
    private final Effect effect;

    public FormChangeItem(Properties properties, String revertAspect, String applyAspect, List<String> pokemons, Effect effect) {
        super(properties);
        this.revertAspect = revertAspect;
        this.applyAspect = applyAspect;
        this.pokemons = pokemons;
        this.effect = effect;
    }

    public void apply(Pokemon pokemon) {
        if(pokemons.contains(pokemon.getSpecies().getName())) {
            effect.applyEffects(pokemon.getEntity(), List.of(applyAspect), null);
        }
    }

    public void revert(Pokemon pokemon) {
        if(pokemons.contains(pokemon.getSpecies().getName())) {
            effect.revertEffects(pokemon.getEntity(), List.of(revertAspect), null);
        }
    }
}
