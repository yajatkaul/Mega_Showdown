package com.github.yajatkaul.mega_showdown.entitiy.client;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.entitiy.TeraHatModel;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class MegaShowdownLayers {
    public static final ModelLayerLocation TERA_HAT_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MegaShowdown.MOD_ID, "tera_hat"), "main");

    public static void register () {
        EntityModelLayerRegistry.register(TERA_HAT_LAYER_LOCATION, TeraHatModel::createBodyLayer);
    }
}
