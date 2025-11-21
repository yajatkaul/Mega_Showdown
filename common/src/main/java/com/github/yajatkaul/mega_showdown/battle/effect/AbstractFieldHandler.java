package com.github.yajatkaul.mega_showdown.battle.effect;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.api.battles.model.actor.EntityBackedBattleActor;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.net.messages.client.effect.SpawnSnowstormParticlePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFieldHandler {
    protected static void sendFieldEffect(ResourceLocation snowstormParticle, PokemonBattle battle) {
        Vec3 centre = getBattleCentre(battle);
        Level level = getBattleWorld(battle);
        if (level == null) return;

        SpawnSnowstormParticlePacket packet = new SpawnSnowstormParticlePacket(snowstormParticle, centre);
        battle.sendUpdate(packet);
    }

    protected static Vec3 getBattleCentre(PokemonBattle battle) {
        List<Vec3> pokemonPositions = new ArrayList<>();

        for (ActiveBattlePokemon pokemon : battle.getActivePokemon()) {
            if (pokemon.getPosition() != null) {
                pokemonPositions.add(pokemon.getPosition().getSecond());
            }
        }
        return pokemonPositions.stream().reduce(Vec3.ZERO, Vec3::add).scale(1.0 / pokemonPositions.size());
    }

    protected static Level getBattleWorld(PokemonBattle battle) {
        for (BattleActor actor : battle.getActors()) {
            if (actor instanceof EntityBackedBattleActor<?> entityActor && entityActor.getEntity() != null) {
                return entityActor.getEntity().level();
            }
        }

        return null;
    }
}
