package com.cobblemon.yajatkaul.mega_showdown.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.registerItem;


public class KeyItems {

    public static final Item RED_CHAIN = registerItem("red_chain", new Item(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.red_chain.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item AZURE_FLUTE = registerItem("azure_flute", new Item(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.azure_flute.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static void registerModItem(){

    }
}
