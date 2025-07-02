package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange.RotomCatalogue;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange.RotomUnit;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.ITEMS;

public class RotomFormes {

    public static final DeferredItem<BlockItem> FANUNIT = ITEMS.register("rotom_fan",
            () -> new RotomUnit(ModBlocks.ROTOM_FAN.get(), new Item.Properties(), "fan"));
    public static final DeferredItem<BlockItem> FRIDGEUNIT = ITEMS.register("rotom_fridge",
            () -> new RotomUnit(ModBlocks.ROTOM_FRIDGE.get(), new Item.Properties(), "frost"));
    public static final DeferredItem<BlockItem> MOWUNIT = ITEMS.register("rotom_mow",
            () -> new RotomUnit(ModBlocks.ROTOM_MOW.get(), new Item.Properties(), "mow"));
    public static final DeferredItem<BlockItem> OVENUNIT = ITEMS.register("rotom_oven",
            () -> new RotomUnit(ModBlocks.ROTOM_OVEN.get(), new Item.Properties(), "heat"));
    public static final DeferredItem<BlockItem> WASHINGUNIT = ITEMS.register("rotom_washing_machine",
            () -> new RotomUnit(ModBlocks.ROTOM_WASHING_MACHINE.get(), new Item.Properties(), "wash"));

    public static final DeferredItem<Item> ROTOM_CATALOGUE = ITEMS.register("rotom_catalogue",
            () -> new RotomCatalogue(new Item.Properties().component(DataManage.CATALOGUE_PAGE, 1)));

    public static void register() {

    }
}
