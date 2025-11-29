package com.github.yajatkaul.mega_showdown.neoforge;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.MegaShowdownClient;
import com.github.yajatkaul.mega_showdown.screen.MegaShowdownMenuTypes;
import com.github.yajatkaul.mega_showdown.screen.custom.ZygardeCubeScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(modid = MegaShowdown.MOD_ID, value = Dist.CLIENT)
public class MegaShowdownNeoForgeClient {
    public MegaShowdownNeoForgeClient() {
        MegaShowdownClient.init();
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MegaShowdownMenuTypes.ZYGARDE_CUBE_MENU.get(), ZygardeCubeScreen::new);
    }

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES)
            return;

        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath("mega_showdown", "resourcepacks/gyaradosjumpingmega"),
                PackType.CLIENT_RESOURCES,
                Component.translatable("message.mega_showdown.gyrados_jump_mega"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP
        );

        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath("mega_showdown", "resourcepacks/regionbiasmsd"),
                PackType.CLIENT_RESOURCES,
                Component.translatable("message.mega_showdown.region_bias_msd"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP
        );
    }
}
