package com.cobblemon.yajatkaul.mega_showdown.utility

import com.cobblemon.mod.common.CobblemonNetwork
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.net.messages.client.animation.PlayPosableAnimationPacket
import com.cobblemon.mod.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket
import net.minecraft.entity.Entity
import net.minecraft.util.Identifier

class SnowStormHandler {
    companion object {
        fun playAnimation(pokemon: Entity, animations: Set<String>, expressions: List<String> = emptyList()) {
            val playPoseableAnimationPacket = PlayPosableAnimationPacket(pokemon.id, animations, expressions)
            playPoseableAnimationPacket.sendToPlayersAround(
                pokemon.x,
                pokemon.y,
                pokemon.z,
                128.0,
                pokemon.world.registryKey
            )
        }

        fun snowStormPartileSpawner(entity: PokemonEntity, particleId: Identifier, source: List<String>?) {
            val packet = SpawnSnowstormEntityParticlePacket(
                particleId,
                sourceEntityId = entity.id,
                sourceLocators = source ?: listOf()
            )

            CobblemonNetwork.sendToAllPlayers(
                packet
            )
        }

        fun snowStormPartileSpawner(
            entity: PokemonEntity,
            particleId: Identifier,
            source: List<String>?,
            targetEntity: PokemonEntity,
            target: List<String>?
        ) {
            val packet = SpawnSnowstormEntityParticlePacket(
                particleId,
                sourceEntityId = entity.id,
                sourceLocators = source ?: listOf(),
                targetedEntityId = targetEntity.id,
                targetLocators = target ?: listOf()
            )

            CobblemonNetwork.sendToAllPlayers(
                packet
            )
        }
    }
}