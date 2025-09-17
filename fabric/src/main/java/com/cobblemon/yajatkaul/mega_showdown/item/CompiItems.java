package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ConvertItem;
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

    public static final Item ADAMANT_ORB = registerItem("adamant_orb", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.adamant_orb.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GRISEOUS_ORB = registerItem("griseous_orb", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.griseous_orb.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LUSTROUS_ORB = registerItem("lustrous_orb", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.lustrous_orb.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item CLEAR_AMULET = registerItem("clear_amulet", new ConvertItem(new Item.Settings(), CobblemonItems.CLEAR_AMULET) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.clear_amulet.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LAGGING_TAIL = registerItem("lagging_tail", new ConvertItem(new Item.Settings(), CobblemonItems.LAGGING_TAIL) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.lagging_tail.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ADRENALINEORB = registerItem("adrenalineorb", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.adrenalineorb.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SOULDEW = registerItem("souldew", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.souldew.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GRIPCLAW = registerItem("gripclaw", new ConvertItem(new Item.Settings(), CobblemonItems.GRIP_CLAW) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.gripclaw.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LUMINOUS_MOSS = registerItem("luminous_moss", new ConvertItem(new Item.Settings(), CobblemonItems.LUMINOUS_MOSS) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.luminous_moss.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static void registerModItem() {

    }
}
