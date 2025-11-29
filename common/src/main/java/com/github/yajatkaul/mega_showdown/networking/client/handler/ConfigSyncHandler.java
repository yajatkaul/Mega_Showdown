package com.github.yajatkaul.mega_showdown.networking.client.handler;

import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.networking.client.packet.ConfigSyncPacket;
import dev.architectury.networking.NetworkManager;

public class ConfigSyncHandler {
    public static void handle(ConfigSyncPacket packet, NetworkManager.PacketContext context) {
        MegaShowdownConfig.outSideMega = packet.outSideMega();
        MegaShowdownConfig.outSideUltraBurst = packet.outSideUltraBurst();
    }
}
