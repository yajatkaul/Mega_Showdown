package com.cobblemon.yajatkaul.mega_showdown.trinket;

import com.cobblemon.yajatkaul.mega_showdown.item.DynamaxItems;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;

public class TrinketsRegisteration {
    public static void register() {
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BRACELET, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_RED_BRACELET, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BLUE_BRACELET, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_YELLOW_BRACELET, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_RING, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BLACK_BRACELET, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_GREEN_BRACELET, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_PINK_BRACELET, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MAY_BRACELET, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.BRENDAN_MEGA_CUFF, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.KORRINA_GLOVE, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.LYSANDRE_RING, new RenderMegaOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MAXIE_GLASSES, new RenderHeadTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.ARCHIE_ANCHOR, new RenderChestTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.LISIA_MEGA_TIARA, new RenderHeadTrinkets());

        TrinketRendererRegistry.registerRenderer(ZCrystals.Z_RING, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.Z_RING_BLACK, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.Z_RING_BLUE, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.Z_RING_GREEN, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.Z_RING_YELLOW, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.Z_RING_PINK, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.Z_RING_RED, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.OLIVIAS_Z_RING, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.HAPUS_Z_RING, new RenderZRingOffHandTrinkets());

        TrinketRendererRegistry.registerRenderer(ZCrystals.Z_RING_POWER, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.OLIVIA_Z_POWER_RING, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.HAPU_Z_POWER_RING, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.ROCKET_Z_POWER_RING, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.GLADION_Z_POWER_RING, new RenderZRingOffHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.NANU_Z_POWER_RING, new RenderZRingOffHandTrinkets());

        TrinketRendererRegistry.registerRenderer(TeraMoves.TERA_ORB, new RenderBeltTrinkets());

        TrinketRendererRegistry.registerRenderer(DynamaxItems.DYNAMAX_BAND, new RenderHandTrinkets());

    }
}
