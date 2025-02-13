package com.cobblemon.yajatkaul.mega_showdown.networking.packets;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.battle.BattleHandling;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class EvoPacket implements CustomPayload{
    private static final Identifier PACKET_ID = Identifier.of(MegaShowdown.MOD_ID, "mega_evo");
    public static final CustomPayload.Id<com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket> MEGA_EVO = new CustomPayload.Id<>(PACKET_ID);

    // Add constructor - can be empty since we don't need to send any data
    public EvoPacket() {
    }

    // Factory method to create new instances
    public static com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket create() {
        return new com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket();
    }

    public static final PacketCodec<PacketByteBuf, com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket> CODEC = new PacketCodec<>() {
        @Override
        public void encode(PacketByteBuf buf, com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket packet) {
            // No data to write
        }

        @Override
        public com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket decode(PacketByteBuf buf) {
            return new com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket();
        }
    };

    public static ServerPlayNetworking.PlayPayloadHandler<com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket> recieve() {
        return (server, player) -> {
            MegaLogic.EvoLogic(player.player());
        };
    }

    @Override
    public Id<com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket> getId() {
        return MEGA_EVO;
    }

    public static void send() {
        ClientPlayNetworking.send(new com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket());
    }
}
