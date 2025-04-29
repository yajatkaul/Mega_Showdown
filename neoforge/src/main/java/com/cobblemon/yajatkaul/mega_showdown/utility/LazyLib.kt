package com.cobblemon.yajatkaul.mega_showdown.utility

import com.cobblemon.mod.common.CobblemonNetwork
import com.cobblemon.mod.common.net.messages.client.animation.PlayPosableAnimationPacket
import com.cobblemon.mod.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

class LazyLib {
    companion object {
        fun cryAnimation(pokemon: Entity) {
            val playPoseableAnimationPacket = PlayPosableAnimationPacket(pokemon.id, setOf("cry"), emptyList())
            playPoseableAnimationPacket.sendToPlayersAround(pokemon.x, pokemon.y, pokemon.z, 128.0, pokemon.level().dimension())
        }

        fun snowStormPartileSpawner(entity: Entity, particle: String){
            val packet = SpawnSnowstormEntityParticlePacket(
                ResourceLocation.fromNamespaceAndPath("cobblemon", particle),
                sourceEntityId = entity.id,
                sourceLocators = listOf("root")
            )

            CobblemonNetwork.sendToAllPlayers(
                packet
            )
        }
    }
}