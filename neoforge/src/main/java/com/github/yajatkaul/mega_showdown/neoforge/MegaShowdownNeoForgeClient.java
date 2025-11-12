package com.github.yajatkaul.mega_showdown.neoforge;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.MegaShowdownClient;
import com.github.yajatkaul.mega_showdown.screen.MegaShowdownMenuTypes;
import com.github.yajatkaul.mega_showdown.screen.custom.ZygardeCubeScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = MegaShowdown.MOD_ID, value = Dist.CLIENT)
public class MegaShowdownNeoForgeClient {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MegaShowdownMenuTypes.ZYGARDE_CUBE_MENU.get(), ZygardeCubeScreen::new);
        MegaShowdownClient.init();
    }
}
