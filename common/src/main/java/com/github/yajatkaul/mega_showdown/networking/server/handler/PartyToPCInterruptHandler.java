package com.github.yajatkaul.mega_showdown.networking.server.handler;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.networking.server.packet.PartyToPCInterruptPacket;
import com.github.yajatkaul.mega_showdown.utils.PlayerUtils;
import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;

public class PartyToPCInterruptHandler {
    public static void handle(PartyToPCInterruptPacket packet, NetworkManager.PacketContext context) {
        ServerPlayer player = (ServerPlayer) context.getPlayer();
        Pokemon pokemon = PlayerUtils.getPCPokemonFromUUID(player, packet.pokemonId());

        if (pokemon != null && pokemon.getAspects().stream().anyMatch(MegaGimmick.getMegaAspects()::contains)) {
            MegaGimmick.megaToggle(pokemon);
        }
    }
}
