package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.RotomCatalogue;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.RotomUnit;
import net.minecraft.item.Item;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.registerItem;

public class RotomFormes {

    public static final Item FAN = registerItem("rotom_fan", new RotomUnit(ModBlocks.ROTOM_FAN, new Item.Settings(), "fan"));
    public static final Item FRIDGEUNIT = registerItem("rotom_fridge", new RotomUnit(ModBlocks.ROTOM_FRIDGE, new Item.Settings(), "frost"));
    public static final Item MOWUNIT = registerItem("rotom_mow", new RotomUnit(ModBlocks.ROTOM_MOW, new Item.Settings(), "mow"));
    public static final Item OVENUNIT = registerItem("rotom_oven", new RotomUnit(ModBlocks.ROTOM_OVEN, new Item.Settings(), "heat"));
    public static final Item WASHUNIT = registerItem("rotom_washing_machine", new RotomUnit(ModBlocks.ROTOM_WASHING_MACHINE, new Item.Settings(), "wash"));

    public static final Item ROTOM_CATALOGUE = registerItem("rotom_catalogue", new RotomCatalogue(new Item.Settings()));

    public static void registerModItem() {
    }
}
