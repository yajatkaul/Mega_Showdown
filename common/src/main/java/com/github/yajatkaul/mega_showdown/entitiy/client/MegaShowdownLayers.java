package com.github.yajatkaul.mega_showdown.entitiy.client;

import com.github.yajatkaul.mega_showdown.entitiy.model.SteelTeraHatModel;
import com.github.yajatkaul.mega_showdown.entitiy.model.TeraCrystalModel;
import com.github.yajatkaul.mega_showdown.entitiy.model.WaterTeraHatModel;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;

public class MegaShowdownLayers {
    public static void register () {
        EntityModelLayerRegistry.register(WaterTeraHatModel.LAYER_LOCATION, WaterTeraHatModel::createBodyLayer);
        EntityModelLayerRegistry.register(SteelTeraHatModel.LAYER_LOCATION, SteelTeraHatModel::createBodyLayer);

        EntityModelLayerRegistry.register(TeraCrystalModel.LAYER_LOCATION, TeraCrystalModel::createBodyLayer);
    }
}
