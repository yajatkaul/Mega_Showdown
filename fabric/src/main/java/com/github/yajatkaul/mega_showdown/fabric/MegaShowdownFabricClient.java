package com.github.yajatkaul.mega_showdown.fabric;

import com.github.yajatkaul.mega_showdown.MegaShowdownClient;
import com.github.yajatkaul.mega_showdown.screen.MegaShowdownMenuTypes;
import com.github.yajatkaul.mega_showdown.screen.custom.ZygardeCubeScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public final class MegaShowdownFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(MegaShowdownMenuTypes.ZYGARDE_CUBE_MENU.get(), ZygardeCubeScreen::new);
        MegaShowdownClient.init();
    }
}
