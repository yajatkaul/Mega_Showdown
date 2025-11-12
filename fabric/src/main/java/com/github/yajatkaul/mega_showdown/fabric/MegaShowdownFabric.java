package com.github.yajatkaul.mega_showdown.fabric;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import net.fabricmc.api.ModInitializer;

public final class MegaShowdownFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MegaShowdown.init();
    }
}
