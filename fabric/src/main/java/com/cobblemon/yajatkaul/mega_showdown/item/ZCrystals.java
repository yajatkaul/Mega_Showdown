package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove.ElementalZCrystal;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove.ZCrystal;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove.ZRingItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.registerItem;

public class ZCrystals {
    public static final Item BLANK_Z = registerItem("blank-z", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.blank-z.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item BUGINIUM_Z = registerElementalZCrystal("buginium-z", ElementalTypes.INSTANCE.getBUG(), "bug");
    public static final Item DARKINIUM_Z = registerElementalZCrystal("darkinium-z", ElementalTypes.INSTANCE.getDARK(), "dark");
    public static final Item DECIDIUM_Z = registerZCrystal("decidium-z", ElementalTypes.INSTANCE.getGHOST());
    public static final Item DRAGONIUM_Z = registerElementalZCrystal("dragonium-z", ElementalTypes.INSTANCE.getDRAGON(), "dragon");
    public static final Item EEVIVIUM_Z = registerZCrystal("eevium-z", ElementalTypes.INSTANCE.getNORMAL());
    public static final Item ELECTRIUM_Z = registerElementalZCrystal("electrium-z", ElementalTypes.INSTANCE.getELECTRIC(), "electric");
    public static final Item FAIRIUM_Z = registerElementalZCrystal("fairium-z", ElementalTypes.INSTANCE.getFAIRY(), "fairy");
    public static final Item FIGHTINIUM_Z = registerElementalZCrystal("fightinium-z", ElementalTypes.INSTANCE.getFIGHTING(), "fighting");
    public static final Item FIRIUM_Z = registerElementalZCrystal("firium-z", ElementalTypes.INSTANCE.getFIRE(), "fire");
    public static final Item FLYINIUM_Z = registerElementalZCrystal("flyinium-z", ElementalTypes.INSTANCE.getFLYING(), "flying");
    public static final Item GHOSTIUM_Z = registerElementalZCrystal("ghostium-z", ElementalTypes.INSTANCE.getGHOST(), "ghost");
    public static final Item GRASSIUM_Z = registerElementalZCrystal("grassium-z", ElementalTypes.INSTANCE.getGRASS(), "grass");
    public static final Item GROUNDIUM_Z = registerElementalZCrystal("groundium-z", ElementalTypes.INSTANCE.getGROUND(), "ground");
    public static final Item ICIUM_Z = registerElementalZCrystal("icium-z", ElementalTypes.INSTANCE.getICE(), "ice");
    public static final Item INCINIUM_Z = registerZCrystal("incinium-z", ElementalTypes.INSTANCE.getDARK());
    public static final Item KOMMONIUM_Z = registerZCrystal("kommonium-z", ElementalTypes.INSTANCE.getDRAGON());
    public static final Item LUNALIUM_Z = registerZCrystal("lunalium-z", ElementalTypes.INSTANCE.getGHOST());
    public static final Item LYCANIUM_Z = registerZCrystal("lycanium-z", ElementalTypes.INSTANCE.getROCK());
    public static final Item MARSHADIUM_Z = registerZCrystal("marshadium-z", ElementalTypes.INSTANCE.getGHOST());
    public static final Item MEWNIUM_Z = registerZCrystal("mewnium-z", ElementalTypes.INSTANCE.getPSYCHIC());
    public static final Item MIMIKIUM_Z = registerZCrystal("mimikium-z", ElementalTypes.INSTANCE.getFAIRY());
    public static final Item NORMALIUM_Z = registerZCrystal("normalium-z", ElementalTypes.INSTANCE.getNORMAL());
    public static final Item PIKANIUM_Z = registerZCrystal("pikanium-z", ElementalTypes.INSTANCE.getELECTRIC());
    public static final Item PIKASHUNIUM_Z = registerZCrystal("pikashunium-z", ElementalTypes.INSTANCE.getELECTRIC());
    public static final Item POISONIUM_Z = registerElementalZCrystal("poisonium-z", ElementalTypes.INSTANCE.getPOISON(), "poison");
    public static final Item PRIMARIUM_Z = registerZCrystal("primarium-z", ElementalTypes.INSTANCE.getWATER()); // Assuming Primarina = Water
    public static final Item PSYCHIUM_Z = registerElementalZCrystal("psychium-z", ElementalTypes.INSTANCE.getPSYCHIC(), "psychic");
    public static final Item ROCKIUM_Z = registerElementalZCrystal("rockium-z", ElementalTypes.INSTANCE.getROCK(), "rock");
    public static final Item SNORLIUM_Z = registerZCrystal("snorlium-z", ElementalTypes.INSTANCE.getNORMAL());
    public static final Item SOLGANIUM_Z = registerZCrystal("solganium-z", ElementalTypes.INSTANCE.getSTEEL());
    public static final Item STEELIUM_Z = registerElementalZCrystal("steelium-z", ElementalTypes.INSTANCE.getSTEEL(), "steel");
    public static final Item TAPUNIUM_Z = registerZCrystal("tapunium-z", ElementalTypes.INSTANCE.getFAIRY());
    public static final Item ULTRANECROZIUM_Z = registerZCrystal("ultranecrozium-z", ElementalTypes.INSTANCE.getPSYCHIC());
    public static final Item WATERIUM_Z = registerElementalZCrystal("waterium-z", ElementalTypes.INSTANCE.getWATER(), "water");
    public static final Item ALORAICHIUM_Z = registerZCrystal("aloraichium-z", ElementalTypes.INSTANCE.getELECTRIC());

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

    private static Item registerZCrystal(String id, ElementalType type) {
        return registerItem(id, new ZCrystal(new Item.Settings(), type) {
            @Override
            public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType tooltipType) {
                tooltip.add(Text.translatable("tooltip.mega_showdown." + id + ".tooltip"));
                super.appendTooltip(stack, context, tooltip, tooltipType);
            }
        });
    }

    private static Item registerElementalZCrystal(String id, ElementalType type, String typeName) {
        return registerItem(id, new ElementalZCrystal(new Item.Settings(), type, typeName) {
            @Override
            public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType tooltipType) {
                tooltip.add(Text.translatable("tooltip.mega_showdown." + id + ".tooltip"));
                super.appendTooltip(stack, context, tooltip, tooltipType);
            }
        });
    }

    public static void registerModItem() {

    }
}
