package com.github.yajatkaul.mega_showdown.fabric;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.MegaShowdownClient;
import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlockEntities;
import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlocks;
import com.github.yajatkaul.mega_showdown.block.block_entity.renderer.PedestalBlockEntityRenderer;
import com.github.yajatkaul.mega_showdown.screen.MegaShowdownMenuTypes;
import com.github.yajatkaul.mega_showdown.screen.custom.ZygardeCubeScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class MegaShowdownFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(MegaShowdownMenuTypes.ZYGARDE_CUBE_MENU.get(), ZygardeCubeScreen::new);
        MegaShowdownClient.init();

        BlockRenderLayerMap.INSTANCE.putBlock(MegaShowdownBlocks.GRACIDEA_FLOWER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MegaShowdownBlocks.POTTED_GRACIDEA.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MegaShowdownBlocks.REASSEMBLY_UNIT.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MegaShowdownBlocks.WISHING_STAR_CRYSTAL.get(), RenderType.translucent());

        ResourceManagerHelper.registerBuiltinResourcePack(
                ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "gyaradosjumpingmega"),
                FabricLoader.getInstance().getModContainer(MegaShowdown.MOD_ID).orElseThrow(),
                Component.translatable("message.mega_showdown.gyrados_jump_mega"),
                ResourcePackActivationType.NORMAL
        );

        ResourceManagerHelper.registerBuiltinResourcePack(
                ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "regionbiasmsd"),
                FabricLoader.getInstance().getModContainer(MegaShowdown.MOD_ID).orElseThrow(),
                Component.translatable("message.mega_showdown.region_bias_msd"),
                ResourcePackActivationType.NORMAL
        );
    }
}
