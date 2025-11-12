package com.github.yajatkaul.mega_showdown;

import com.github.yajatkaul.mega_showdown.key_mapping.MegaShowdownKeybinds;
import com.github.yajatkaul.mega_showdown.networking.packets.MegaEvo;
import com.github.yajatkaul.mega_showdown.networking.packets.UltraBurst;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.networking.NetworkManager;

public class MegaShowdownClient {
    public static void init() {
        MegaShowdownKeybinds.register();

        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            if (MegaShowdownKeybinds.MEGA_KEY.consumeClick()) {
                NetworkManager.sendToServer(new MegaEvo("mega_evo"));
            }
            if (MegaShowdownKeybinds.ULTRA_KEY.consumeClick()) {
                NetworkManager.sendToServer(new UltraBurst("ultra_burst"));
            }
        });
    }
}
