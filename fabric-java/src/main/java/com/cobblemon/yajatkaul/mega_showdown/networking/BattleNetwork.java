package com.cobblemon.yajatkaul.mega_showdown.networking;

import com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacketBattle;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import static com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket.MEGA_EVO;
import static com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacketBattle.MEGA_EVO_BATTLE;

public class BattleNetwork {
    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(MEGA_EVO_BATTLE, EvoPacketBattle.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MEGA_EVO_BATTLE, EvoPacketBattle.recieve());

        PayloadTypeRegistry.playC2S().register(MEGA_EVO, EvoPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MEGA_EVO, EvoPacket.recieve());
    }
}