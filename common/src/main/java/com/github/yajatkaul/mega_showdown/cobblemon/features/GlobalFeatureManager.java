package com.github.yajatkaul.mega_showdown.cobblemon.features;

import com.cobblemon.mod.common.pokemon.Pokemon;

public class GlobalFeatureManager {
    public static void update(Pokemon pokemon) {
        DynamaxLevelHandler.update(pokemon);
    }

    public static void register() {
        DynamaxLevelHandler.register();
    }
}
