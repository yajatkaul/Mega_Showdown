package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class Dynamax extends Item {
    public Dynamax(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.dynamax_band.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
