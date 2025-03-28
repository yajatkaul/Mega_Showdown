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

    public static final Item LEGEND_PLATE = registerItem("legend_plate", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.legend_plate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ADAMANT_ORB = registerItem("adamant_orb", new Item(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.adamant_orb.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GRISEOUS_ORB = registerItem("griseous_orb", new Item(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.griseous_orb.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LUSTROUS_ORB = registerItem("lustrous_orb", new Item(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.lustrous_orb.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item CLEAR_AMULET = registerItem("clear_amulet", new Item(new Item.Settings()){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.clear_amulet.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LAGGING_TAIL = registerItem("lagging_tail", new Item(new Item.Settings()){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.lagging_tail.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static void registerModItem(){

    }
}
