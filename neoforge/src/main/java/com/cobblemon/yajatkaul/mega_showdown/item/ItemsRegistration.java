package com.cobblemon.yajatkaul.mega_showdown.item;

import net.neoforged.bus.api.IEventBus;

public class ItemsRegistration {
    public static void register(IEventBus modEventBus){
        ModItems.register(modEventBus);
        MegaStones.register();
        ZCrystals.register();
        TeraMoves.register();
        RotomFormes.register();
        CompiItems.register();
        FormeChangeItems.register();
    }
}
