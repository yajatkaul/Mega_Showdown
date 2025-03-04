package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.item.custom.TeraItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;


import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.ITEMS;

public class TeraMoves {
    public static final DeferredItem<Item> TERA_ORB = ITEMS.register("tera_orb",
            () -> new TeraItem(new Item.Properties().stacksTo(1)));

    public static void register(){
    }
}
