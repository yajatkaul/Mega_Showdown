package com.cobblemon.yajatkaul.mega_showdown.item;


import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks.MEGA_STONE_CRYSTAL;

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

    public static final Item MEGA_STONE = registerItem("mega_stone", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.mega_stone.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ABOMASITE = registerItem("abomasite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.abomasite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ABSOLITE = registerItem("absolite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.absolite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item AERODACTYLITE = registerItem("aerodactylite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.aerodactylite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item AGGRONITE = registerItem("aggronite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.aggronite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ALAKAZITE = registerItem("alakazite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.alakazite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ALTARIANITE = registerItem("altarianite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.altarianite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item AMPHAROSITE = registerItem("ampharosite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.ampharosite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item AUDINITE = registerItem("audinite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.audinite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item BANETTITE = registerItem("banettite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.banettite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item BEEDRILLITE = registerItem("beedrillite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.beedrillite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item BLASTOISINITE = registerItem("blastoisinite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.blastoisinite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item BLAZIKENITE = registerItem("blazikenite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.blazikenite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item CAMERUPTITE = registerItem("cameruptite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.cameruptite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item CHARIZARDITE_Y = registerItem("charizardite_y", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.charizardite_y.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item CHARIZARDITE_X = registerItem("charizardite_x", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.charizardite_x.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DIANCITE = registerItem("diancite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.diancite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GALLADITE = registerItem("galladite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.galladite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GARCHOMPITE = registerItem("garchompite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.garchompite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GARDEVOIRITE = registerItem("gardevoirite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.gardevoirite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GENGARITE = registerItem("gengarite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.gengarite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GLALITITE = registerItem("glalitite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.glalitite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GYARADOSITE = registerItem("gyaradosite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.gyaradosite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item HERACRONITE = registerItem("heracronite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.heracronite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item HOUNDOOMINITE = registerItem("houndoominite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.houndoominite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item KANGASKHANITE = registerItem("kangaskhanite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.kangaskhanite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LATIASITE = registerItem("latiasite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.latiasite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LATIOSITE = registerItem("latiosite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.latiosite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LOPUNNITE = registerItem("lopunnite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.lopunnite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LUCARIONITE = registerItem("lucarionite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.lucarionite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MANECTITE = registerItem("manectite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.manectite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MAWILITE = registerItem("mawilite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.mawilite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MEDICHAMITE = registerItem("medichamite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.medichamite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item METAGROSSITE = registerItem("metagrossite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.metagrossite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MEWTWONITE_Y = registerItem("mewtwonite_y", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.mewtwonite_y.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MEWTWONITE_X = registerItem("mewtwonite_x", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.mewtwonite_x.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PIDGEOTITE = registerItem("pidgeotite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.pidgeotite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PINSIRITE = registerItem("pinsirite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.pinsirite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SABLENITE = registerItem("sablenite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.sablenite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SALAMENCITE = registerItem("salamencite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.salamencite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SCIZORITE = registerItem("scizorite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.scizorite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SCEPTILITE = registerItem("sceptilite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.sceptilite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SHARPEDONITE = registerItem("sharpedonite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.sharpedonite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SLOWBRONITE = registerItem("slowbronite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.slowbronite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item STEELIXITE = registerItem("steelixite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.steelixite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SWAMPERTITE = registerItem("swampertite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.swampertite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item TYRANITARITE = registerItem("tyranitarite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.tyranitarite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item VENUSAURITE = registerItem("venusaurite", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.venusaurite.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MEGA_STONE_CRYSTAL_ITEM = Registry.register(Registries.ITEM,
            Identifier.of(MegaShowdown.MOD_ID, "mega_stone_crystal"),
            new BlockItem(MEGA_STONE_CRYSTAL, new Item.Settings()));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(MegaShowdown.MOD_ID, name), item);
    }

    public static void registerModItem(){

    }


}
