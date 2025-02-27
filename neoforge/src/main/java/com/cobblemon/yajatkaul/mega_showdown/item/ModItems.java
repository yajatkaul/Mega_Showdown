package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MegaShowdown.MOD_ID);

    public static final DeferredItem<Item> MEGA_BRACELET = ITEMS.register("megabracelet",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_RED_BRACELET = ITEMS.register("megabracelet_red",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_YELLOW_BRACELET = ITEMS.register("megabracelet_yellow",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_PINK_BRACELET = ITEMS.register("megabracelet_pink",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_GREEN_BRACELET = ITEMS.register("megabracelet_green",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_BLUE_BRACELET = ITEMS.register("megabracelet_blue",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_BLACK_BRACELET = ITEMS.register("megabracelet_black",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_RING = ITEMS.register("megaring",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> LYSANDRE_RING = ITEMS.register("lysandre_ring",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> BRENDAN_MEGA_CUFF = ITEMS.register("brendan_mega_cuff",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> KORRINA_GLOVE = ITEMS.register("korrina_glove",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MAXIE_GLASSES = ITEMS.register("maxie_glasses",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ARCHIE_ANCHOR = ITEMS.register("archie_anchor",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
