package com.github.yajatkaul.mega_showdown.networking.server.handler;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.networking.server.packet.MegaEvoPacket;
import com.github.yajatkaul.mega_showdown.utils.PlayerUtils;
import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;

public class MegaEvoHandler {
    public static void handle(MegaEvoPacket packet, NetworkManager.PacketContext context) {
        ServerPlayer player = (ServerPlayer) context.getPlayer();
        Pokemon pokemon = PlayerUtils.getPartyPokemonFromUUID(player, packet.pokemonId());

        if (pokemon != null) {
            MegaGimmick.megaToggle(pokemon);
        }
    }
}
