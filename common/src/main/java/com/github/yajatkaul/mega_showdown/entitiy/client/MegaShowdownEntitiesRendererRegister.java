package com.github.yajatkaul.mega_showdown.entitiy.client;

import com.github.yajatkaul.mega_showdown.entitiy.MegaShowdownEntities;
import com.github.yajatkaul.mega_showdown.entitiy.client.renderer.SteelTeraHatEntityRenderer;
import com.github.yajatkaul.mega_showdown.entitiy.client.renderer.TeraCrystalEntityRenderer;
import com.github.yajatkaul.mega_showdown.entitiy.client.renderer.WaterTeraHatEntityRenderer;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;

public class MegaShowdownEntitiesRendererRegister {
    public static void register() {
        EntityRendererRegistry.register(MegaShowdownEntities.WATER_TERA_HAT_ENTITY, WaterTeraHatEntityRenderer::new);
        EntityRendererRegistry.register(MegaShowdownEntities.STEEL_TERA_HAT_ENTITY, SteelTeraHatEntityRenderer::new);

        EntityRendererRegistry.register(MegaShowdownEntities.TERA_CRYSTAL_ENTITY, TeraCrystalEntityRenderer::new);
    }
}
