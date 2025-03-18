package com.cobblemon.yajatkaul.mega_showdown.item;

import net.neoforged.bus.api.IEventBus;

public class ItemsRegistration {
    public static void register(IEventBus modEventBus){
        ModItems.register(modEventBus);
        MegaStones.register();
        ZMoves.register();
        TeraMoves.register();
        RotomFormes.register();
    }
}
