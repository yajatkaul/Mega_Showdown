package com.cobblemon.yajatkaul.mega_showdown.utility

import com.cobblemon.mod.common.CobblemonNetwork
import com.cobblemon.mod.common.net.messages.client.animation.PlayPosableAnimationPacket
import com.cobblemon.mod.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.Identifier

class LazyLib {
    companion object {
        fun cryAnimation(pokemon: LivingEntity) {
            val playPoseableAnimationPacket = PlayPosableAnimationPacket(pokemon.id, setOf("cry"), emptyList())
            playPoseableAnimationPacket.sendToPlayersAround(pokemon.x, pokemon.y, pokemon.z, 128.0, pokemon.world.registryKey)
        }

        fun snowStormPartileSpawner(entity: Entity, particle: String){
            val packet = SpawnSnowstormEntityParticlePacket(
                Identifier.of("cobblemon", particle),
                sourceEntityId = entity.id,
                sourceLocators = listOf("target")
            )

            CobblemonNetwork.sendToAllPlayers(
                packet
            )
        }
    }
}