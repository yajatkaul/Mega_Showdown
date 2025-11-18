package com.github.yajatkaul.mega_showdown;

import com.github.yajatkaul.mega_showdown.key_mapping.MegaShowdownKeybinds;
import com.github.yajatkaul.mega_showdown.networking.packets.MegaEvo;
import com.github.yajatkaul.mega_showdown.networking.packets.UltraBurst;
import com.github.yajatkaul.mega_showdown.render.ItemRenderingLoader;
import com.github.yajatkaul.mega_showdown.render.accessories.AccessoriesRegisterRenderer;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.server.packs.PackType;

public class MegaShowdownClient {
    public static void init() {
        MegaShowdownKeybinds.register();
        AccessoriesRegisterRenderer.register();

        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            if (MegaShowdownKeybinds.MEGA_KEY.consumeClick()) {
                NetworkManager.sendToServer(new MegaEvo("mega_evo"));
            }
            if (MegaShowdownKeybinds.ULTRA_KEY.consumeClick()) {
                NetworkManager.sendToServer(new UltraBurst("ultra_burst"));
            }
        });

        ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, new ItemRenderingLoader());
    }
}
