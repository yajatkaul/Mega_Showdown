package com.cobblemon.yajatkaul.megamons.item;

import com.cobblemon.yajatkaul.megamons.MegaMons;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MegaMons.MOD_ID);

    public static final DeferredItem<Item> ABOMASNOW = ITEMS.register("abomasite",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CHARIZARDITE_X = ITEMS.register("charizardite_x",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MEGA_BRACELET = ITEMS.register("megabracelet",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
