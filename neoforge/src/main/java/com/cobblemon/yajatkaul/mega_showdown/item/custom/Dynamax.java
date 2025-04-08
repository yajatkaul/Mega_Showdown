package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class Dynamax extends Item {
    public Dynamax(Properties arg) {
        super(arg);
    }

    @Override
    public void appendHoverText(ItemStack arg, TooltipContext arg2, List<Component> tooltipComponents, TooltipFlag arg3) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dynamax_band.tooltip"));
        super.appendHoverText(arg, arg2, tooltipComponents, arg3);
    }
}
