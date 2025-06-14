package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.item.custom.ArceusType;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ZRingItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.registerItem;

public class ZCrystals {
    public static final Item ALORAICHIUM_Z = registerItem("aloraichium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.aloraichium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item BLANK_Z = registerItem("blank-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.blank-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item BUGINIUM_Z = registerItem("buginium-z", new ArceusType(new Item.Settings(), "bug") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.buginium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DARKINIUM_Z = registerItem("darkinium-z", new ArceusType(new Item.Settings(), "dark") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.darkinium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DECIDIUM_Z = registerItem("decidium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.decidium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DRAGONIUM_Z = registerItem("dragonium-z", new ArceusType(new Item.Settings(), "dragon") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.dragonium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item EEVIVIUM_Z = registerItem("eevium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.eevium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ELECTRIUM_Z = registerItem("electrium-z", new ArceusType(new Item.Settings(), "electric") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.electrium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FAIRIUM_Z = registerItem("fairium-z", new ArceusType(new Item.Settings(), "fairy") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.fairium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FIGHTINIUM_Z = registerItem("fightinium-z", new ArceusType(new Item.Settings(), "fighting") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.fightinium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FIRIUM_Z = registerItem("firium-z", new ArceusType(new Item.Settings(), "fire") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.firium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FLYINIUM_Z = registerItem("flyinium-z", new ArceusType(new Item.Settings(), "flying") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.flyinium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GHOSTIUM_Z = registerItem("ghostium-z", new ArceusType(new Item.Settings(), "ghost") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.ghostium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GRASSIUM_Z = registerItem("grassium-z", new ArceusType(new Item.Settings(), "grass") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.grassium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GROUNDIUM_Z = registerItem("groundium-z", new ArceusType(new Item.Settings(), "ground") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.groundium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ICIUM_Z = registerItem("icium-z", new ArceusType(new Item.Settings(), "ice") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.icium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item INCINIUM_Z = registerItem("incinium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.incinium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item KOMMONIUM_Z = registerItem("kommonium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.kommonium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LUNALIUM_Z = registerItem("lunalium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.lunalium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LYCANIUM_Z = registerItem("lycanium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.lycanium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MARSHADIUM_Z = registerItem("marshadium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.marshadium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MEWNIUM_Z = registerItem("mewnium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.mewnium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MIMIKIUM_Z = registerItem("mimikium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.mimikium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item NORMALIUM_Z = registerItem("normalium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.normalium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PIKANIUM_Z = registerItem("pikanium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.pikanium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PIKASHUNIUM_Z = registerItem("pikashunium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.pikashunium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item POISONIUM_Z = registerItem("poisonium-z", new ArceusType(new Item.Settings(), "poison") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.poisonium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PRIMARIUM_Z = registerItem("primarium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.primarium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PSYCHIUM_Z = registerItem("psychium-z", new ArceusType(new Item.Settings(), "physic") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.psychium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ROCKIUM_Z = registerItem("rockium-z", new ArceusType(new Item.Settings(), "rock") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.rockium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SNORLIUM_Z = registerItem("snorlium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.snorlium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SOLGANIUM_Z = registerItem("solganium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.solganium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item STEELIUM_Z = registerItem("steelium-z", new ArceusType(new Item.Settings(), "steel") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.steelium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item TAPUNIUM_Z = registerItem("tapunium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.tapunium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ULTRANECROZIUM_Z = registerItem("ultranecrozium-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.ultranecrozium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item WATERIUM_Z = registerItem("waterium-z", new ArceusType(new Item.Settings(), "water") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.waterium-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SPARKLING_STONE_LIGHT = registerItem("sparkling_stone_light", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.sparkling_stone_light.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SPARKLING_STONE_DARK = registerItem("sparkling_stone_dark", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.sparkling_stone_dark.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item Z_RING = registerItem("z-ring", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item Z_RING_BLACK = registerItem("z-ring_black", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item Z_RING_YELLOW = registerItem("z-ring_yellow", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item Z_RING_GREEN = registerItem("z-ring_green", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item Z_RING_BLUE = registerItem("z-ring_blue", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item Z_RING_PINK = registerItem("z-ring_pink", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item Z_RING_RED = registerItem("z-ring_red", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item OLIVIAS_Z_RING = registerItem("olivias_z-ring", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item HAPUS_Z_RING = registerItem("hapus_z-ring", new ZRingItem(new Item.Settings().maxCount(1)));

    public static final Item Z_RING_POWER = registerItem("z-power_ring", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item OLIVIA_Z_POWER_RING = registerItem("olivia_z-power_ring", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item HAPU_Z_POWER_RING = registerItem("hapu_z-power_ring", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item ROCKET_Z_POWER_RING = registerItem("rocket_z-power_ring", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item GLADION_Z_POWER_RING = registerItem("gladion_z-power_ring", new ZRingItem(new Item.Settings().maxCount(1)));
    public static final Item NANU_Z_POWER_RING = registerItem("nanu_z-power_ring", new ZRingItem(new Item.Settings().maxCount(1)));

    public static void registerModItem() {

    }
}
