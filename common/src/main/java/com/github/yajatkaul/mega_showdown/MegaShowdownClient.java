package com.github.yajatkaul.mega_showdown;

import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlockEntities;
import com.github.yajatkaul.mega_showdown.block.block_entity.renderer.PedestalBlockEntityRenderer;
import com.github.yajatkaul.mega_showdown.client.CobbleClientEvents;
import com.github.yajatkaul.mega_showdown.entitiy.MegaShowdownEntities;
import com.github.yajatkaul.mega_showdown.entitiy.client.MegaShowdownEntitiesRendererRegister;
import com.github.yajatkaul.mega_showdown.entitiy.client.MegaShowdownLayers;
import com.github.yajatkaul.mega_showdown.entitiy.custom.TeraCrystalEntity;
import com.github.yajatkaul.mega_showdown.render.ItemRenderingLoader;
import com.github.yajatkaul.mega_showdown.render.accessories.AccessoriesRegisterRenderer;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.server.packs.PackType;

public class MegaShowdownClient {
    public static void init() {
        MegaShowdownEntitiesRendererRegister.register();
        MegaShowdownLayers.register();
        AccessoriesRegisterRenderer.register();

        CobbleClientEvents.register();
        ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, new ItemRenderingLoader());

        BlockEntityRenderers.register(MegaShowdownBlockEntities.PEDESTAL_BLOCK_ENTITY.get(), PedestalBlockEntityRenderer::new);
    }
}
