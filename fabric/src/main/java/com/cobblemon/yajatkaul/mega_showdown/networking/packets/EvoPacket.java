package com.cobblemon.yajatkaul.mega_showdown.networking.packets;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.MegaLogic;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class EvoPacket implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, EvoPacket> CODEC = new PacketCodec<>() {
        @Override
        public void encode(PacketByteBuf buf, EvoPacket packet) {
            // No data to write
        }

        @Override
        public EvoPacket decode(PacketByteBuf buf) {
            return new EvoPacket();
        }
    };
    private static final Identifier PACKET_ID = Identifier.of(MegaShowdown.MOD_ID, "mega_evo");
    public static final CustomPayload.Id<EvoPacket> MEGA_EVO = new CustomPayload.Id<>(PACKET_ID);

    // Add constructor - can be empty since we don't need to send any data
    public EvoPacket() {
    }

    // Factory method to create new instances
    public static EvoPacket create() {
        return new EvoPacket();
    }

    public static ServerPlayNetworking.PlayPayloadHandler<EvoPacket> recieve() {
        return (server, player) -> {
            MegaLogic.EvoLogic(player.player());
        };
    }

    public static void send() {
        ClientPlayNetworking.send(new EvoPacket());
    }

    @Override
    public Id<EvoPacket> getId() {
        return MEGA_EVO;
    }
}
