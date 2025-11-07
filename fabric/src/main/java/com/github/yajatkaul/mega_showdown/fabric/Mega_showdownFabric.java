package com.github.yajatkaul.mega_showdown.fabric;

import com.github.yajatkaul.mega_showdown.Mega_showdown;
import net.fabricmc.api.ModInitializer;

public final class Mega_showdownFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Mega_showdown.init();
    }
}
