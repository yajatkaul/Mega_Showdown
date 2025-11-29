package com.github.yajatkaul.mega_showdown.networking.server.handler;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.gimmick.UltraGimmick;
import com.github.yajatkaul.mega_showdown.networking.server.packet.UltraBurstPacket;
import com.github.yajatkaul.mega_showdown.utils.PlayerUtils;
import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;

public class UltraBurstHandler {
    public static void handle(UltraBurstPacket packet, NetworkManager.PacketContext context) {
        ServerPlayer player = (ServerPlayer) context.getPlayer();
        Pokemon pokemon = PlayerUtils.getPartyPokemonFromUUID(player, packet.pokemonId());

        if (pokemon != null) {
            UltraGimmick.ultraBurstToggle(pokemon);
        }
    }
}
