package com.github.yajatkaul.mega_showdown.item.custom;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ToolTipItem extends Item {
    public ToolTipItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        tooltipComponents.add(Component.translatable("tooltip." + id.getNamespace() + "." + id.getPath() + ".tooltip"));
        super.appendHoverText(itemStack, tooltipContext, tooltipComponents, tooltipFlag);
    }
}
