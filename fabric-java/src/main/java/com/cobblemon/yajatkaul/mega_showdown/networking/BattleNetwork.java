package com.cobblemon.yajatkaul.mega_showdown.networking;

import com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.UltraPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import static com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket.MEGA_EVO;
import static com.cobblemon.yajatkaul.mega_showdown.networking.packets.UltraPacket.ULTRA_TRANS;

public class BattleNetwork {
    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(MEGA_EVO, EvoPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MEGA_EVO, EvoPacket.recieve());

        PayloadTypeRegistry.playC2S().register(ULTRA_TRANS, UltraPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ULTRA_TRANS, UltraPacket.recieve());
    }
}