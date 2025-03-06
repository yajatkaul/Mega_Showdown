package com.cobblemon.yajatkaul.mega_showdown.networking.packets;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.UltraLogic;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class UltraPacket implements CustomPayload{
    private static final Identifier PACKET_ID = Identifier.of(MegaShowdown.MOD_ID, "ultra_trans");
    public static final Id<UltraPacket> ULTRA_TRANS = new Id<>(PACKET_ID);

    // Add constructor - can be empty since we don't need to send any data
    public UltraPacket() {
    }

    // Factory method to create new instances
    public static UltraPacket create() {
        return new UltraPacket();
    }

    public static final PacketCodec<PacketByteBuf, UltraPacket> CODEC = new PacketCodec<>() {
        @Override
        public void encode(PacketByteBuf buf, UltraPacket packet) {
            // No data to write
        }

        @Override
        public UltraPacket decode(PacketByteBuf buf) {
            return new UltraPacket();
        }
    };

    public static ServerPlayNetworking.PlayPayloadHandler<UltraPacket> recieve() {
        return (server, player) -> {
            UltraLogic.ultraTransform(player.player());
        };
    }

    @Override
    public Id<UltraPacket> getId() {
        return ULTRA_TRANS;
    }

    public static void send() {
        ClientPlayNetworking.send(new UltraPacket());
    }
}
