package com.github.yajatkaul.mega_showdown;

import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlocks;
import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.creative.MegaShowdownTabs;
import com.github.yajatkaul.mega_showdown.event.EventRegister;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import com.github.yajatkaul.mega_showdown.key_mapping.MegaShowdownKeybinds;
import com.github.yajatkaul.mega_showdown.networking.MegaShowdownNetworkHandler;
import com.github.yajatkaul.mega_showdown.screen.MegaShowdownMenuTypes;
import com.github.yajatkaul.mega_showdown.status.MegaShowdownStatusEffects;
import dev.architectury.event.events.client.ClientTickEvent;
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
        MegaShowdownBlocks.register();
        MegaShowdownItems.register();
        MegaShowdownTabs.register();
        MegaShowdownStatusEffects.register();

        MegaShowdownNetworkHandler.register();

        MegaShowdownMenuTypes.register();

        EventRegister.register();

        LifecycleEvent.SERVER_STARTED.register((minecraftServer) -> {
            server = minecraftServer;
        });
    }
}
