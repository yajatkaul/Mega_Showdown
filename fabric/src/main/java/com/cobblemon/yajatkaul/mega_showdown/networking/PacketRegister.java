package com.cobblemon.yajatkaul.mega_showdown.networking;

import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownCustomsConfig;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MSDCustomPacket;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.UltraPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;

import static com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket.MEGA_EVO;
import static com.cobblemon.yajatkaul.mega_showdown.networking.packets.UltraPacket.ULTRA_TRANS;

public class PacketRegister {
    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(MEGA_EVO, EvoPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MEGA_EVO, EvoPacket.recieve());

        PayloadTypeRegistry.playC2S().register(ULTRA_TRANS, UltraPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ULTRA_TRANS, UltraPacket.recieve());

        PayloadTypeRegistry.playS2C().register(MSDCustomPacket.ID, MSDCustomPacket.CODEC);
    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(MSDCustomPacket.ID, MSDCustomPacket::execute);
    }
}