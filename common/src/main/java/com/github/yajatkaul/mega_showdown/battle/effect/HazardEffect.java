package com.github.yajatkaul.mega_showdown.battle.effect;

import com.cobblemon.mod.common.battles.BattleSide;

public class HazardEffect extends AbstractSideHandler {
    public static void handleHazard(BattleSide side, String hazardName, int ticks) {
        EffectWrapper effect = getEffect(hazardName);
        if (effect == null || ticks % effect.tickInterval() != 0) return;

        side.getActivePokemon().forEach(pokemon -> {
            if (pokemon.getBattlePokemon() != null && pokemon.getBattlePokemon().getEntity() != null) {
                sendEntityEffect(side.getBattle(), effect, pokemon.getBattlePokemon().getEntity(), "root");
            }
        });
    }

    private static EffectWrapper getEffect(String hazard) {
        return switch (hazard) {
            case "stealthrock" -> EffectWrapper.STEALTH_ROCKS;
            case "stickyweb" -> EffectWrapper.STICKY_WEB;
            case "spikes" -> EffectWrapper.SPIKES;
            case "toxicspikes" -> EffectWrapper.TOXIC_SPIKES;
            case null, default -> null;
        };
    }
}
