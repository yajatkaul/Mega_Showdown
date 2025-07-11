package com.cobblemon.yajatkaul.mega_showdown.features;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer;

public class GlobalFeatureManager {
    public static void update(Pokemon pokemon, ServerPlayer player) {
        DynamaxLevelHandler.update(pokemon, player);
    }
}
