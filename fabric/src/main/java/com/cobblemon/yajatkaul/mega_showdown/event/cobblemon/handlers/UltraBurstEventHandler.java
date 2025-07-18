package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.EventUtils;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.UltraLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;

import java.util.List;
import java.util.Set;


public class UltraBurstEventHandler {
    public static void ultraBurstHandler(PokemonBattle battle, BattlePokemon pokemon) {
        Pokemon pokemon2 = pokemon.getEntity().getPokemon();
        if (pokemon2.getAspects().contains("dawn-fusion")) {
            pokemon2.getPersistentData().putString("fusion_form", "dawn");
        } else {
            pokemon2.getPersistentData().putString("fusion_form", "dusk");
        }

        new StringSpeciesFeature("prism_fusion", "ultra").apply(pokemon.getEffectedPokemon());
        UltraLogic.ultraAnimation(pokemon.getEntity());

        EventUtils.updatePackets(battle, pokemon);

        battle.dispatchWaitingToFront(3F, () -> {
            SnowStormHandler.Companion.playAnimation(pokemon.getEntity(), Set.of("cry"), List.of());
            return Unit.INSTANCE;
        });
    }
}