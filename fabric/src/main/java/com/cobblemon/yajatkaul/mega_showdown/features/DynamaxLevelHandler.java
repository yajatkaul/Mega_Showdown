package com.cobblemon.yajatkaul.mega_showdown.features;

import com.cobblemon.mod.common.api.pokemon.feature.IntSpeciesFeature;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.SpeciesFeatureUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.network.ServerPlayerEntity;

public class DynamaxLevelHandler {
    public static void update(Pokemon pokemon, ServerPlayerEntity player) {
        if (pokemon.getSpecies().getDynamaxBlocked()) return;

        IntSpeciesFeature dmaxLevel = pokemon.getFeature("dynamax_level");
        if (dmaxLevel == null) {
            dmaxLevel = new IntSpeciesFeature("dynamax_level", pokemon.getDmaxLevel());
            pokemon.getFeatures().add(dmaxLevel);
            pokemon.updateAspects();
        } else {
            dmaxLevel.setValue(pokemon.getDmaxLevel());
        }
        pokemon.notify(new SpeciesFeatureUpdatePacket(() -> pokemon, pokemon.getSpecies().resourceIdentifier, dmaxLevel));

        if (player != null) {
            new SpeciesFeatureUpdatePacket(() -> pokemon, pokemon.getSpecies().resourceIdentifier, dmaxLevel).sendToPlayer(player);
        }
    }

    public static void update(Pokemon pokemon) {
        update(pokemon, null);
    }
}
