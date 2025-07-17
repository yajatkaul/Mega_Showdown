package com.cobblemon.yajatkaul.mega_showdown.features;

import com.cobblemon.mod.common.api.pokemon.feature.IntSpeciesFeature;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.SpeciesFeatureUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer;

public class DynamaxLevelHandler {
    public static void update(Pokemon pokemon, ServerPlayer player) {
        if (pokemon.getSpecies().getDynamaxBlocked()) return;

        final IntSpeciesFeature dmaxLevel;
        IntSpeciesFeature existing = pokemon.getFeature("dynamax_level");
        if (existing == null) {
            dmaxLevel = new IntSpeciesFeature("dynamax_level", pokemon.getDmaxLevel());
            pokemon.getFeatures().add(dmaxLevel);
            pokemon.updateAspects();
        } else {
            existing.setValue(pokemon.getDmaxLevel());
            dmaxLevel = existing;
        }
        pokemon.notify(new SpeciesFeatureUpdatePacket(() -> pokemon, pokemon.getSpecies().resourceIdentifier, dmaxLevel));

        if (player != null) {
            player.server.execute(() ->
                    new SpeciesFeatureUpdatePacket(() -> pokemon, pokemon.getSpecies().resourceIdentifier, dmaxLevel).sendToPlayer(player)
            );
        }
    }

    public static void update(Pokemon pokemon) {
        update(pokemon, null);
    }
}
