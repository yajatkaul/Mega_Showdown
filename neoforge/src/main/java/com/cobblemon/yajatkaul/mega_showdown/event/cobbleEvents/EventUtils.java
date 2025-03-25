package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.pokemon.Pokemon;

public class EventUtils {
    public static void revertFormesEnd(Pokemon pokemon){
        new StringSpeciesFeature("forecast_form", "normal").apply(pokemon);
        new StringSpeciesFeature("meteor_shield", "meteor").apply(pokemon);
        new StringSpeciesFeature("stance_forme", "shield").apply(pokemon);
        if(pokemon.getSpecies().getName().equals("Greninja") && pokemon.getAspects().contains("ash")){
            new StringSpeciesFeature("battle_bond", "bond").apply(pokemon);
        }
        new StringSpeciesFeature("hunger_mode", "full_belly").apply(pokemon);
        new StringSpeciesFeature("disguise_form", "disguised").apply(pokemon);
        new FlagSpeciesFeature("embody_aspect", false).apply(pokemon);
        new StringSpeciesFeature("schooling_form", "solo").apply(pokemon);
        new StringSpeciesFeature("penguin_head", "ice_face").apply(pokemon);
        new StringSpeciesFeature("missile_form", "none").apply(pokemon);
        new StringSpeciesFeature("blazing_mode", "standard").apply(pokemon);
        new StringSpeciesFeature("dolphin_form", "zero").apply(pokemon);
        new StringSpeciesFeature("blossom_form", "overcast").apply(pokemon);
    }
}
