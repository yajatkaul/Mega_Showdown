package com.github.yajatkaul.mega_showdown.gimmick;

import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.codec.Effect;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.tag.MegaShowdownTags;
import com.github.yajatkaul.mega_showdown.utils.AccessoriesUtils;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class UltraGimmick {
    public static int ultraBurst(Pokemon pokemon) {
        if (MegaShowdownConfig.outSideUltraBurst) {
            return 0;
        }

        ServerPlayer player = pokemon.getOwnerPlayer();

        if (player != null && !AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.Z_RING)) {
            return 0;
        }

        if (canUltraBurst(pokemon)) {
            if (pokemon.getAspects().contains("dawn-fusion")) {
                pokemon.getPersistentData().putString("necrozma_form", "prism_fusion=dusk");
                AspectUtils.appendRevertDataPokemon(
                        Effect.getEffect("mega_showdown:ultra_burst"),
                        List.of("prism_fusion=dusk"),
                        pokemon,
                        "revert_aspects"
                );
            } else {
                pokemon.getPersistentData().putString("necrozma_form", "prism_fusion=dawn");
                AspectUtils.appendRevertDataPokemon(
                        Effect.getEffect("mega_showdown:ultra_burst"),
                        List.of("prism_fusion=dawn"),
                        pokemon,
                        "revert_aspects"
                );
            }
            Effect.getEffect("mega_showdown:ultra_burst").applyEffects(pokemon, List.of("prism_fusion=ultra"), null);
            pokemon.setTradeable(false);

            return -1;
        } else if (pokemon.getAspects().contains("ultra")) {
            String org_form = pokemon.getPersistentData().getString("necrozma_form");
            pokemon.getPersistentData().remove("necrozma_form");

            Effect.getEffect("mega_showdown:ultra_burst").revertEffects(pokemon, List.of(org_form), null);
            pokemon.setTradeable(true);

            return 1;
        }
        return 0;
    }

    public static void ultraBurstInBattle(Pokemon pokemon, BattlePokemon battlePokemon) {
        if (pokemon.getAspects().contains("dawn-fusion")) {
            AspectUtils.appendRevertDataPokemon(
                    Effect.getEffect("mega_showdown:ultra_burst"),
                    List.of("prism_fusion=dusk"),
                    pokemon,
                    "battle_end_revert"
            );
        } else {
            AspectUtils.appendRevertDataPokemon(
                    Effect.getEffect("mega_showdown:ultra_burst"),
                    List.of("prism_fusion=dawn"),
                    pokemon,
                    "battle_end_revert"
            );
        }

        Effect.getEffect("mega_showdown:ultra_burst").applyEffectsBattle(pokemon, List.of("prism_fusion=ultra"), null, battlePokemon);
    }

    private static boolean canUltraBurst(Pokemon pokemon) {
        if (pokemon.getSpecies().getName().equals("Necrozma")) {
            return pokemon.getAspects().contains("dawn-fusion") || pokemon.getAspects().contains("dusk-fusion");
        }
        return false;
    }
}
