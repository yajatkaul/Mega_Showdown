package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.yajatkaul.mega_showdown.battle.ButtonLogic;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.Controls;
import com.cobblemon.yajatkaul.mega_showdown.trinket.RenderHandTrinkets;
import com.cobblemon.yajatkaul.mega_showdown.trinket.TrinketsRegisteration;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.render.RenderLayer;

public class MegaShowdownClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MegaOres.MEGA_STONE_CRYSTAL, RenderLayer.getCutout());

        ScreenEvents.AFTER_INIT.register(ButtonLogic::megaEvoButton);

        Controls.register();

        TrinketsRegisteration.register();
    }
}
