package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.yajatkaul.mega_showdown.battle.BattleHandling;
import com.cobblemon.yajatkaul.mega_showdown.battle.ButtonLogic;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.Controls;
import com.cobblemon.yajatkaul.mega_showdown.trinket.RenderTrinkets;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.render.RenderLayer;

public class MegaShowdownClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEGA_STONE_CRYSTAL, RenderLayer.getCutout());

        ScreenEvents.AFTER_INIT.register(ButtonLogic::megaEvoButton);

        Controls.register();

        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BRACELET, new RenderTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_RED_BRACELET, new RenderTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BLUE_BRACELET, new RenderTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_YELLOW_BRACELET, new RenderTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_RING, new RenderTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_BLACK_BRACELET, new RenderTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_GREEN_BRACELET, new RenderTrinkets());
        TrinketRendererRegistry.registerRenderer(ModItems.MEGA_PINK_BRACELET, new RenderTrinkets());
    }
}
