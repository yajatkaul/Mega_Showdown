package com.cobblemon.yajatkaul.mega_showdown.utility

import com.cobblemon.mod.common.CobblemonNetwork
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.net.messages.client.animation.PlayPosableAnimationPacket
import com.cobblemon.mod.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

class SnowStormHandler {
    companion object {
        fun playAnimation(pokemon: Entity, animations: Set<String>, expressions: List<String> = emptyList()) {
            val playPoseableAnimationPacket = PlayPosableAnimationPacket(pokemon.id, animations, expressions)
            playPoseableAnimationPacket.sendToPlayersAround(
                pokemon.x,
                pokemon.y,
                pokemon.z,
                128.0,
                pokemon.level().dimension()
            )
        }

        fun snowStormPartileSpawner(entity: PokemonEntity, particleId: ResourceLocation, source: List<String>?) {
            val packet = SpawnSnowstormEntityParticlePacket(
                particleId,
                sourceEntityId = entity.id,
                sourceLocators = source ?: listOf()
            )

            CobblemonNetwork.sendToAllPlayers(
                packet
            )
        }

        fun snowStormPartileSpawner(entity: PokemonEntity, particleId: ResourceLocation, source: List<String>?, targetEntity: PokemonEntity, target: List<String>?) {
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