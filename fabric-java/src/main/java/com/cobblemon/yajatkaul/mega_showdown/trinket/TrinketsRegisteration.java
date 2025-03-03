package com.cobblemon.yajatkaul.mega_showdown.trinket;

import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.ZMoves;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;

public class TrinketsRegisteration {
    public static void register(){
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_RED_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BLUE_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_YELLOW_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_RING, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BLACK_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_GREEN_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_PINK_BRACELET, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.BRENDAN_MEGA_CUFF, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.KORRINA_GLOVE, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.LYSANDRE_RING, new RenderHandTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MAXIE_GLASSES, new RenderHeadTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.ARCHIE_ANCHOR, new RenderChestTrinkets());

        TrinketRendererRegistry.registerRenderer(ZMoves.Z_RING, new RenderOffHandTrinkets());
    }
}
