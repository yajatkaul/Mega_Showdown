package com.cobblemon.yajatkaul.mega_showdown.curios;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.DynamaxItems;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(modid = MegaShowdown.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Register {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(ModItems.MEGA_BRACELET.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_RED_BRACELET.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_BLUE_BRACELET.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_YELLOW_BRACELET.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_RING.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_BLACK_BRACELET.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_GREEN_BRACELET.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.MEGA_PINK_BRACELET.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.MAY_BRACELET.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.BRENDAN_MEGA_CUFF.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.KORRINA_GLOVE.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.LYSANDRE_RING.get(), CurioMegaOffHandRenderer::new);
        CuriosRendererRegistry.register(ModItems.MAXIE_GLASSES.get(), CurioHeadRenderer::new);
        CuriosRendererRegistry.register(ModItems.ARCHIE_ANCHOR.get(), CurioChestRenderer::new);
        CuriosRendererRegistry.register(ModItems.LISIA_MEGA_TIARA.get(), CurioHeadRenderer::new);

        CuriosRendererRegistry.register(ZCrystals.Z_RING.get(), CurioZRingOffHandRenderer::new);
        CuriosRendererRegistry.register(ZCrystals.Z_RING_BLACK.get(), CurioZRingOffHandRenderer::new);
        CuriosRendererRegistry.register(ZCrystals.Z_RING_GREEN.get(), CurioZRingOffHandRenderer::new);
        CuriosRendererRegistry.register(ZCrystals.Z_RING_BLUE.get(), CurioZRingOffHandRenderer::new);
        CuriosRendererRegistry.register(ZCrystals.Z_RING_PINK.get(), CurioZRingOffHandRenderer::new);
        CuriosRendererRegistry.register(ZCrystals.Z_RING_YELLOW.get(), CurioZRingOffHandRenderer::new);
        CuriosRendererRegistry.register(ZCrystals.Z_RING_POWER.get(), CurioZRingOffHandRenderer::new);

        CuriosRendererRegistry.register(TeraMoves.TERA_ORB.get(), CurioBeltRenderer::new);

        CuriosRendererRegistry.register(DynamaxItems.DYNAMAX_BAND.get(), CurioHandRenderer::new);
    }
}
