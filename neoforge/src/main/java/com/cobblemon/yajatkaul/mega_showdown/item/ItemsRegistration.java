package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.item.custom.utils.NoRenderArmorMaterial;
import net.neoforged.bus.api.IEventBus;

public class ItemsRegistration {
    public static void register(IEventBus modEventBus) {
        NoRenderArmorMaterial.register(modEventBus);

        ModItems.register(modEventBus);
        MegaStones.register();
        ZCrystals.register();
        TeraMoves.register();
        RotomFormes.register();
        CompiItems.register();
        FormeChangeItems.register();
        KeyItems.register();
        DynamaxItems.register();
        Sprites.register();
    }
}
