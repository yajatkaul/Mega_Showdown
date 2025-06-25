package com.cobblemon.yajatkaul.mega_showdown.item;


import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.tera.LikosPendant;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.ITEMS;

public class KeyItems {
    public static final DeferredItem<Item> LIKOS_PENDANT = ITEMS.register("likos_pendant",
            () -> new LikosPendant(new Item.Properties().stacksTo(1).component(DataManage.LIKO_PENDANT_TICK, 72000)));

    public static void register() {
    }
}
