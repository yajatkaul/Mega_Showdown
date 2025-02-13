package com.cobblemon.yajatkaul.mega_showdown.networking.packets;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.battle.BattleHandling;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class EvoPacketBattle implements CustomPayload {
    private static final Identifier PACKET_ID = Identifier.of(MegaShowdown.MOD_ID, "mega_evo_battle");
    public static final CustomPayload.Id<EvoPacketBattle> MEGA_EVO_BATTLE = new CustomPayload.Id<>(PACKET_ID);

    // Add constructor - can be empty since we don't need to send any data
    public EvoPacketBattle() {
    }

    // Factory method to create new instances
    public static EvoPacketBattle create() {
        return new EvoPacketBattle();
    }

    public static final PacketCodec<PacketByteBuf, EvoPacketBattle> CODEC = new PacketCodec<>() {
        @Override
        public void encode(PacketByteBuf buf, EvoPacketBattle packet) {
            // No data to write
        }

        @Override
        public EvoPacketBattle decode(PacketByteBuf buf) {
            return new EvoPacketBattle();
        }
    };

    public static ServerPlayNetworking.PlayPayloadHandler<EvoPacketBattle> recieve() {
        return (server, player) -> {
            BattleHandling.handleMegaEvolution(player);
        };
    }

    @Override
    public Id<EvoPacketBattle> getId() {
        return MEGA_EVO_BATTLE;
    }

    public static void send() {
        ClientPlayNetworking.send(new EvoPacketBattle());
    }
}