package com.github.yajatkaul.mega_showdown.cobblemon.features;

import com.cobblemon.mod.common.api.pokemon.feature.IntSpeciesFeature;
import com.cobblemon.mod.common.pokemon.Pokemon;

public class DynamaxLevelHandler {
    private static final String FEATURE_NAME = "dynamax_level";

    public static void update(Pokemon pokemon) {
        if (pokemon == null || pokemon.getSpecies().getDynamaxBlocked()) return;

        pokemon.getFeatures().removeIf(feature -> feature.getName().equals(FEATURE_NAME));
        pokemon.getFeatures().add(new IntSpeciesFeature(FEATURE_NAME, pokemon.getDmaxLevel()));
    }
}
