package com.cobblemon.yajatkaul.mega_showdown.networking.packets;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.battle.BattleHandling;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class ButtonPacket implements CustomPayload {
    private static final Identifier PACKET_ID = Identifier.of(MegaShowdown.MOD_ID, "button_status");
    public static final CustomPayload.Id<ButtonPacket> BUTTON_STATUS = new CustomPayload.Id<>(PACKET_ID);

    public ButtonPacket() {
    }

    public static final PacketCodec<PacketByteBuf, ButtonPacket> CODEC = new PacketCodec<>() {
        @Override
        public void encode(PacketByteBuf buf, ButtonPacket packet) {
            // No data to write
        }

        @Override
        public ButtonPacket decode(PacketByteBuf buf) {
            return new ButtonPacket();
        }
    };

    public static ServerPlayNetworking.PlayPayloadHandler<ButtonPacket> recieve() {
        return (server, player) -> {
            //Logic
        };
    }

    @Override
    public Id<ButtonPacket> getId() {
        return BUTTON_STATUS;
    }

    public static void send() {
        ClientPlayNetworking.send(new ButtonPacket());
    }
}
