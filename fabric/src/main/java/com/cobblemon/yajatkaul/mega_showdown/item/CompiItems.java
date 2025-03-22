package com.cobblemon.yajatkaul.mega_showdown.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.registerItem;

public class CompiItems {
    public static final Item BOOSTER_ENERGY = registerItem("booster_energy", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.booster_energy.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static void registerModItem(){

    }
}
