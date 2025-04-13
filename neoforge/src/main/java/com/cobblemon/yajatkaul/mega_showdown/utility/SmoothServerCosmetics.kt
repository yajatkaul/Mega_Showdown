package com.cobblemon.yajatkaul.mega_showdown.utility

import com.cobblemon.mod.common.client.net.pokemon.update.PokemonUpdatePacketHandler
import com.cobblemon.mod.common.net.PacketRegisterInfo
import com.cobblemon.mod.common.net.messages.client.pokemon.update.DmaxLevelUpdatePacket
import com.cobblemon.mod.common.net.messages.client.pokemon.update.GmaxFactorUpdatePacket
import com.cobblemon.mod.common.net.messages.client.pokemon.update.TeraTypeUpdatePacket

object SmoothServerCosmetics {
    fun handleCobblemonPacket(list: MutableList<PacketRegisterInfo<*>>) {
        list.add(PacketRegisterInfo(
            TeraTypeUpdatePacket.ID,
            TeraTypeUpdatePacket::decode,
            PokemonUpdatePacketHandler()
        ))

        list.add(PacketRegisterInfo(
            DmaxLevelUpdatePacket.ID,
            DmaxLevelUpdatePacket::decode,
            PokemonUpdatePacketHandler()
        ))

        list.add(PacketRegisterInfo(
            GmaxFactorUpdatePacket.ID,
            GmaxFactorUpdatePacket::decode,
            PokemonUpdatePacketHandler()
        ))
    }
}