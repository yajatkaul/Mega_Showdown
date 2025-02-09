package com.cobblemon.yajatkaul.mega_showdown.networking;

import com.cobblemon.yajatkaul.mega_showdown.networking.packets.ButtonPacket;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import static com.cobblemon.yajatkaul.mega_showdown.networking.packets.ButtonPacket.BUTTON_STATUS;
import static com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket.MEGA_EVO;

public class BattleNetwork {
    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(MEGA_EVO, EvoPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MEGA_EVO, EvoPacket.recieve());
    }

    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(BUTTON_STATUS, ButtonPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(BUTTON_STATUS, ButtonPacket.recieve());
    }
}