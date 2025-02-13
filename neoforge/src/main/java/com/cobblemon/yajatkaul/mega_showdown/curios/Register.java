package com.cobblemon.yajatkaul.mega_showdown.curios;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(modid = MegaShowdown.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Register {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(ModItems.MEGA_BRACELET.get(), CurioRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_RED_BRACELET.get(), CurioRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_BLUE_BRACELET.get(), CurioRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_YELLOW_BRACELET.get(), CurioRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_RING.get(), CurioRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_BLACK_BRACELET.get(), CurioRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_GREEN_BRACELET.get(), CurioRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_PINK_BRACELET.get(), CurioRenderer::new);
    }
}
