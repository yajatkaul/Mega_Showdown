package com.cobblemon.yajatkaul.mega_showdown.utility

import com.cobblemon.mod.common.net.messages.client.animation.PlayPosableAnimationPacket
import net.minecraft.entity.LivingEntity

class LazyLib {
    companion object {
        fun cryAnimation(pokemon: LivingEntity) {
            val playPoseableAnimationPacket = PlayPosableAnimationPacket(pokemon.id, setOf("cry"), emptyList())
            playPoseableAnimationPacket.sendToPlayersAround(pokemon.x, pokemon.y, pokemon.z, 128.0, pokemon.world.registryKey)
        }
    }
}