package com.github.yajatkaul.mega_showdown.battle.effect;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket;

import java.util.List;

public abstract class AbstractSideHandler {
    protected static void sendEntityEffect(PokemonBattle battle, EffectWrapper effect, PokemonEntity entity, String locator) {
        SpawnSnowstormEntityParticlePacket packet = new SpawnSnowstormEntityParticlePacket(effect.effectId(), entity.getId(), List.of(locator), null, null);
        battle.sendUpdate(packet);
    }
}
