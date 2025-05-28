package com.cobblemon.yajatkaul.mega_showdown.trinket;

import com.cobblemon.yajatkaul.mega_showdown.item.DynamaxItems;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;

public class TrinketsRegisteration {
    public static void register() {
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_RED_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BLUE_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_YELLOW_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_RING, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BLACK_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_GREEN_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_PINK_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MAY_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.BRENDAN_MEGA_CUFF, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.KORRINA_GLOVE, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.LYSANDRE_RING, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MAXIE_GLASSES, new RenderHeadTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.ARCHIE_ANCHOR, new RenderChestTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.LISIA_MEGA_TIARA, new RenderHeadTrinkets());

        TrinketRendererRegistry.registerRenderer(ZCrystals.Z_RING, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.Z_RING_BLACK, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ZCrystals.Z_RING_POWER, new RenderHandTrinkets());

        TrinketRendererRegistry.registerRenderer(TeraMoves.TERA_ORB, new RenderBeltTrinkets());

        TrinketRendererRegistry.registerRenderer(DynamaxItems.DYNAMAX_BAND, new RenderOffHandTrinkets());

    }
}
