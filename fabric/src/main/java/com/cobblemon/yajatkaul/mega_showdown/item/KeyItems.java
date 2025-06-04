package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.LikosPendant;
import net.minecraft.item.Item;


import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.registerItem;

public class KeyItems {

    public static final Item LIKOS_PENDANT = registerItem("likos_pendant", new LikosPendant(new Item.Settings()
            .maxCount(1)
            .component(DataManage.LIKO_PENDANT_TICK, 100)));

    public static void registerModItem() {

    }
}
