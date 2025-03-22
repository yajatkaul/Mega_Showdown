package com.cobblemon.yajatkaul.mega_showdown.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.ITEMS;

public class CompiItems {
    public static final DeferredItem<Item> BOOSTER_ENERGY = ITEMS.register("booster_energy",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.booster_energy.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static void register(){
    }
}
