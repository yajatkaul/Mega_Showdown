package com.github.yajatkaul.mega_showdown.item.custom.form_change;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import com.github.yajatkaul.mega_showdown.utils.Effect;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class FormChangeHeldItem extends ToolTipItem {
    private final String revertAspect;
    private final String applyAspect;
    private final List<String> pokemons;
    private final Effect effect;
    private final boolean tradable;
    private final @Nullable Consumer<Pokemon> onApplyCallback;
    private final @Nullable Consumer<Pokemon> onRevertCallback;

    public FormChangeHeldItem(Properties properties, String revertAspect, String applyAspect, List<String> pokemons, Effect effect, boolean tradable, @Nullable Consumer<Pokemon> onApplyCallback, @Nullable Consumer<Pokemon> onRevertCallback) {
        super(properties);
        this.revertAspect = revertAspect;
        this.applyAspect = applyAspect;
        this.pokemons = pokemons;
        this.effect = effect;
        this.tradable = tradable;
        this.onApplyCallback = onApplyCallback;
        this.onRevertCallback = onRevertCallback;
    }

    public void apply(Pokemon pokemon) {
        if (pokemons.contains(pokemon.getSpecies().getName())) {
            if (onApplyCallback != null) {
                onApplyCallback.accept(pokemon);
            }
            effect.applyEffects(pokemon, List.of(applyAspect), null);
            if (!tradable) {
                pokemon.setTradeable(false);
            }
        }
    }

    public void revert(Pokemon pokemon) {
        if (pokemons.contains(pokemon.getSpecies().getName())) {
            if (onRevertCallback != null) {
                onRevertCallback.accept(pokemon);
            }
            effect.revertEffects(pokemon, List.of(revertAspect), null);
            if (!tradable) {
                pokemon.setTradeable(true);
            }
        }
    }
}
