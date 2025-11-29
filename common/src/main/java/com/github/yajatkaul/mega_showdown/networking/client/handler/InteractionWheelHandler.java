package com.github.yajatkaul.mega_showdown.networking.client.handler;

import com.github.yajatkaul.mega_showdown.client.WheelDataClient;
import com.github.yajatkaul.mega_showdown.networking.client.packet.InteractionWheelPacket;
import dev.architectury.networking.NetworkManager;

public class InteractionWheelHandler {
    public static void handle(InteractionWheelPacket packet, NetworkManager.PacketContext context) {
        WheelDataClient.shouldMega = packet.shouldMega();
        WheelDataClient.shouldUltra = packet.shouldUltra();

        WheelDataClient.canMega = packet.canMega();
        WheelDataClient.canUltra = packet.canUltra();
    }
}
