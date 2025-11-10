package com.github.yajatkaul.mega_showdown.gimmick;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;

import java.util.List;

public class ZGimmick {
    public static void UltraBurst(Pokemon pokemon) {
        if (canUltraBurst(pokemon)) {
            ParticlesList.ultraBurst.revertEffects(pokemon.getEntity(), List.of("ultra=false"), null);
        } else if (pokemon.getAspects().contains("ultra")) {
            ParticlesList.ultraBurst.applyEffects(pokemon.getEntity(), List.of("ultra=true"), null);
        }
    }

    private static boolean canUltraBurst(Pokemon pokemon) {
        if (pokemon.getSpecies().getName().equals("Necrozma")) {
            return pokemon.getAspects().contains("dawn-fusion") || pokemon.getAspects().contains("dusk-fusion");
        }

        return false;
    }
}
