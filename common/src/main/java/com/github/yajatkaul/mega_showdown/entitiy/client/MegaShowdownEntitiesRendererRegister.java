package com.github.yajatkaul.mega_showdown.entitiy.client;

import com.github.yajatkaul.mega_showdown.entitiy.MegaShowdownEntities;
import com.github.yajatkaul.mega_showdown.entitiy.TeraHatEntity;
import com.github.yajatkaul.mega_showdown.entitiy.client.renderer.TeraHatEntityRenderer;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;

public class MegaShowdownEntitiesRendererRegister {
    public static void register() {
        EntityRendererRegistry.register(MegaShowdownEntities.TERA_HAT_ENTITY, TeraHatEntityRenderer::new);
    }
}
