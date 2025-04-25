package com.cobblemon.yajatkaul.mega_showdown.mixin.pokemon;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.utility.TeraAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Pokemon.class)
public class PokemonMixin implements TeraAccessor {
    @Unique
    private boolean teraEnabled = false;

    @Override
    public boolean isTeraEnabled() {
        return teraEnabled;
    }

    @Override
    public void setTeraEnabled(boolean value) {
        this.teraEnabled = value;
    }
}