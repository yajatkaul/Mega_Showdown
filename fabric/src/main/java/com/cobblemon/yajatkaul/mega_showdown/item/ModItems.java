package com.cobblemon.yajatkaul.mega_showdown.item;


import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItems {
    public static final Item KEYSTONE = registerItem("keystone", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.keystone.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MEGA_BRACELET = registerItem("megabracelet", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_RED_BRACELET = registerItem("megabracelet_red", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_YELLOW_BRACELET = registerItem("megabracelet_yellow", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_PINK_BRACELET = registerItem("megabracelet_pink", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_GREEN_BRACELET = registerItem("megabracelet_green", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_BLUE_BRACELET = registerItem("megabracelet_blue", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_BLACK_BRACELET = registerItem("megabracelet_black", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_RING = registerItem("megaring", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item BRENDAN_MEGA_CUFF = registerItem("brendan_mega_cuff", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item LYSANDRE_RING = registerItem("lysandre_ring", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item KORRINA_GLOVE = registerItem("korrina_glove", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MAXIE_GLASSES = registerItem("maxie_glasses", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item ARCHIE_ANCHOR = registerItem("archie_anchor", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MAY_BRACELET = registerItem("may_bracelet", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item LISIA_MEGA_TIARA = registerItem("lisia_mega_tiara", new MegaBraceletItem(new Item.Settings().maxCount(1)));

    public static final Item SCROLL_OF_DARKNESS = registerItem("scroll_of_darkness", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.scroll_of_darkness.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SCROLL_OF_WATERS = registerItem("scroll_of_waters", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.scroll_of_waters.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MegaShowdown.MOD_ID, name), item);
    }

    public static void registerModItem() {

    }
}
