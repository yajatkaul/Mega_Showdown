package com.cobblemon.yajatkaul.megamons.item;

import com.cobblemon.yajatkaul.megamons.MegaShowdown;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MegaShowdown.MOD_ID);

    public static final DeferredItem<Item> ABOMASNOW = ITEMS.register("abomasite",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHARIZARDITE_X = ITEMS.register("charizardite-x",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MEGA_BRACELET = ITEMS.register("megabracelet",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ABSOLITE = ITEMS.register("absolite",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ADAMANTORB = ITEMS.register("adamantorb",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ABSOLITE = ITEMS.register("absolite",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ABSOLITE = ITEMS.register("absolite",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
