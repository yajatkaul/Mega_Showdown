package com.cobblemon.yajatkaul.mega_showdown.event.ultra;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.UltraLogic;
import net.neoforged.bus.api.SubscribeEvent;

public class UltraEventListener {
    @SubscribeEvent
    public void onUltra(UltraEvent event) {
        new FlagSpeciesFeature("ultra", true).apply(event.getPokemon().getEffectedPokemon());
        UltraLogic.ultraAnimation(event.getPokemon().getEntity());
    }
}
