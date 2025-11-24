package com.github.yajatkaul.mega_showdown.cobblemon.features;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.pokemon.Pokemon;
import kotlin.Unit;

public class GlobalFeatureManager {
    public static void update(Pokemon pokemon) {
        DynamaxLevelHandler.update(pokemon);
    }

    public static void registerEarly() {
        CobblemonEvents.POKEMON_PROPERTY_INITIALISED.subscribe(Priority.NORMAL, GlobalFeatureManager::initialiseProperties);
    }

    private static void initialiseProperties(Unit unit) {
        DynamaxLevelHandler.register();
    }
}
