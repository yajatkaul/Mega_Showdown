package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax.Dynamax;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.registerItem;

public class DynamaxItems {
    public static final Item DYNAMAX_BAND = registerItem("dynamax_band", new Dynamax(new Item.Settings().maxCount(1)));

    public static final Item DYNAMAX_CANDY = registerItem("dynamax_candy", new DynamaxCandy(new Item.Settings()));

    public static final Item WISHING_STAR = registerItem("wishing_star", new Item(new Item.Settings()){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.wishing_star.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MAX_HONEY = registerItem("max_honey", new MaxHoney());

    public static final Item MAX_MUSHROOM = registerItem("max_mushroom",
            new MaxMushroom(ModBlocks.MAX_MUSHROOM, new Item.Settings()));

    public static final Item MAX_SOUP = registerItem("max_soup", new MaxSoup(new Item.Settings().maxCount(1)));

    public static final Item SWEET_MAX_SOUP = registerItem("sweet_max_soup", new SweetMaxSoup(new Item.Settings().maxCount(1)));

    public static void registerModItem(){

    }
}
