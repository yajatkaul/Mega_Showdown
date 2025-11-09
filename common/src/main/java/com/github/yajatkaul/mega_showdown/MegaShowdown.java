package com.github.yajatkaul.mega_showdown;

import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.creative.MegaShowdownTabs;
import com.github.yajatkaul.mega_showdown.event.EventRegister;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MegaShowdown {
    public static final String MOD_ID = "mega_showdown";
    public static final Logger LOGGER = LoggerFactory.getLogger("MegaShowdown");
    private static MinecraftServer server;

    public static MinecraftServer getServer() {
        return server;
    }

    public static void init() {
        MegaShowdownConfig.register();

        MegaShowdownDataComponents.register();
        MegaShowdownItems.register();
        MegaShowdownTabs.register();

        EventRegister.register();

        LifecycleEvent.SERVER_STARTED.register((minecraftServer) -> {
            server = minecraftServer;
        });
    }
}
