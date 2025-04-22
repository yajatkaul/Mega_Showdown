package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.entity.ModBlockEntities;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.entity.renderer.PedestalBlockEntityRenderer;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.Controls;
import com.cobblemon.yajatkaul.mega_showdown.screen.ModScreenHandlers;
import com.cobblemon.yajatkaul.mega_showdown.screen.custom.ZygardeCubeScreen;
import com.cobblemon.yajatkaul.mega_showdown.trinket.TrinketsRegisteration;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class MegaShowdownClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRACIDEA_FLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_GRACIDEA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.REASSEMBLY_UNIT, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WISHING_STAR_CRYSTAL, RenderLayer.getTranslucent());

        Controls.register();

        TrinketsRegisteration.register();

        BlockEntityRendererFactories.register(ModBlockEntities.PEDESTAL_BE, PedestalBlockEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.ZYGARDE_CUBE_SCREEN_HANDLER_TYPE, ZygardeCubeScreen::new);
    }
}
