package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove.ElementalZCrystal;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove.ZCrystal;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove.ZRingItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.ITEMS;

public class ZCrystals {
    public static final DeferredItem<Item> BLANK_Z = ITEMS.register("blank-z",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.blank-z.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> BUGINIUM_Z = registerElementalZCrystal("buginium-z", ElementalTypes.INSTANCE.getBUG(), "bug");
    public static final DeferredItem<Item> DARKINIUM_Z = registerElementalZCrystal("darkinium-z", ElementalTypes.INSTANCE.getDARK(), "dark");
    public static final DeferredItem<Item> DECIDIUM_Z = registerZCrystal("decidium-z", ElementalTypes.INSTANCE.getGHOST());
    public static final DeferredItem<Item> DRAGONIUM_Z = registerElementalZCrystal("dragonium-z", ElementalTypes.INSTANCE.getDRAGON(), "dragon");
    public static final DeferredItem<Item> EEVIUM_Z = registerZCrystal("eevium-z", ElementalTypes.INSTANCE.getNORMAL());
    public static final DeferredItem<Item> ELECTRIUM_Z = registerElementalZCrystal("electrium-z", ElementalTypes.INSTANCE.getELECTRIC(), "electric");
    public static final DeferredItem<Item> FAIRIUM_Z = registerElementalZCrystal("fairium-z", ElementalTypes.INSTANCE.getFAIRY(), "fairy");
    public static final DeferredItem<Item> FIGHTINIUM_Z = registerElementalZCrystal("fightinium-z", ElementalTypes.INSTANCE.getFIGHTING(), "fighting");
    public static final DeferredItem<Item> FIRIUM_Z = registerElementalZCrystal("firium-z", ElementalTypes.INSTANCE.getFIRE(), "fire");
    public static final DeferredItem<Item> FLYINIUM_Z = registerElementalZCrystal("flyinium-z", ElementalTypes.INSTANCE.getFLYING(), "flying");
    public static final DeferredItem<Item> GHOSTIUM_Z = registerElementalZCrystal("ghostium-z", ElementalTypes.INSTANCE.getGHOST(), "ghost");
    public static final DeferredItem<Item> GRASSIUM_Z = registerElementalZCrystal("grassium-z", ElementalTypes.INSTANCE.getGRASS(), "grass");
    public static final DeferredItem<Item> GROUNDIUM_Z = registerElementalZCrystal("groundium-z", ElementalTypes.INSTANCE.getGROUND(), "ground");
    public static final DeferredItem<Item> ICIUM_Z = registerElementalZCrystal("icium-z", ElementalTypes.INSTANCE.getICE(), "ice");
    public static final DeferredItem<Item> INCINIUM_Z = registerZCrystal("incinium-z", ElementalTypes.INSTANCE.getDARK());
    public static final DeferredItem<Item> KOMMONIUM_Z = registerZCrystal("kommonium-z", ElementalTypes.INSTANCE.getDRAGON());
    public static final DeferredItem<Item> LUNALIUM_Z = registerZCrystal("lunalium-z", ElementalTypes.INSTANCE.getGHOST());
    public static final DeferredItem<Item> LYCANIUM_Z = registerZCrystal("lycanium-z", ElementalTypes.INSTANCE.getROCK());
    public static final DeferredItem<Item> MARSHADIUM_Z = registerZCrystal("marshadium-z", ElementalTypes.INSTANCE.getGHOST());
    public static final DeferredItem<Item> MEWNIUM_Z = registerZCrystal("mewnium-z", ElementalTypes.INSTANCE.getPSYCHIC());
    public static final DeferredItem<Item> MIMIKIUM_Z = registerZCrystal("mimikium-z", ElementalTypes.INSTANCE.getFAIRY());
    public static final DeferredItem<Item> NORMALIUM_Z = registerZCrystal("normalium-z", ElementalTypes.INSTANCE.getNORMAL());
    public static final DeferredItem<Item> PIKANIUM_Z = registerZCrystal("pikanium-z", ElementalTypes.INSTANCE.getELECTRIC());
    public static final DeferredItem<Item> PIKASHUNIUM_Z = registerZCrystal("pikashunium-z", ElementalTypes.INSTANCE.getELECTRIC());
    public static final DeferredItem<Item> POISONIUM_Z = registerElementalZCrystal("poisonium-z", ElementalTypes.INSTANCE.getPOISON(), "poison");
    public static final DeferredItem<Item> PRIMARIUM_Z = registerZCrystal("primarium-z", ElementalTypes.INSTANCE.getWATER()); // Assuming Primarina = Water
    public static final DeferredItem<Item> PSYCHIUM_Z = registerElementalZCrystal("psychium-z", ElementalTypes.INSTANCE.getPSYCHIC(), "psychic");
    public static final DeferredItem<Item> ROCKIUM_Z = registerElementalZCrystal("rockium-z", ElementalTypes.INSTANCE.getROCK(), "rock");
    public static final DeferredItem<Item> SNORLIUM_Z = registerZCrystal("snorlium-z", ElementalTypes.INSTANCE.getNORMAL());
    public static final DeferredItem<Item> SOLGANIUM_Z = registerZCrystal("solganium-z", ElementalTypes.INSTANCE.getSTEEL());
    public static final DeferredItem<Item> STEELIUM_Z = registerElementalZCrystal("steelium-z", ElementalTypes.INSTANCE.getSTEEL(), "steel");
    public static final DeferredItem<Item> TAPUNIUM_Z = registerZCrystal("tapunium-z", ElementalTypes.INSTANCE.getFAIRY());
    public static final DeferredItem<Item> ULTRANECROZIUM_Z = registerZCrystal("ultranecrozium-z", ElementalTypes.INSTANCE.getPSYCHIC());
    public static final DeferredItem<Item> WATERIUM_Z = registerElementalZCrystal("waterium-z", ElementalTypes.INSTANCE.getWATER(), "water");
    public static final DeferredItem<Item> ALORAICHIUM_Z = registerZCrystal("aloraichium-z", ElementalTypes.INSTANCE.getELECTRIC());


    public static final DeferredItem<Item> SPARKLING_STONE_LIGHT = ITEMS.register("sparkling_stone_light",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.sparkling_stone_light.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SPARKLING_STONE_DARK = ITEMS.register("sparkling_stone_dark",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.sparkling_stone_dark.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> Z_RING = ITEMS.register("z-ring",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Z_RING_BLACK = ITEMS.register("z-ring_black",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Z_RING_YELLOW = ITEMS.register("z-ring_yellow",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Z_RING_GREEN = ITEMS.register("z-ring_green",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Z_RING_BLUE = ITEMS.register("z-ring_blue",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Z_RING_PINK = ITEMS.register("z-ring_pink",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Z_RING_RED = ITEMS.register("z-ring_red",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> OLIVIAS_Z_RING = ITEMS.register("olivias_z-ring",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> HAPUS_Z_RING = ITEMS.register("hapus_z-ring",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> Z_RING_POWER = ITEMS.register("z-power_ring",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> OLIVIA_Z_POWER_RING = ITEMS.register("olivia_z-power_ring",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> HAPU_Z_POWER_RING = ITEMS.register("hapu_z-power_ring",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ROCKET_Z_POWER_RING = ITEMS.register("rocket_z-power_ring",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GLADION_Z_POWER_RING = ITEMS.register("gladion_z-power_ring",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> NANU_Z_POWER_RING = ITEMS.register("nanu_z-power_ring",
            () -> new ZRingItem(new Item.Properties().stacksTo(1)));

    private static DeferredItem<Item> registerZCrystal(String id, ElementalType type) {
        return ITEMS.register(id, () -> new ZCrystal(new Item.Properties(), type) {
            @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                tooltip.add(Component.translatable("tooltip.mega_showdown." + id + ".tooltip"));
                super.appendHoverText(stack, context, tooltip, flag);
            }
        });
    }

    private static DeferredItem<Item> registerElementalZCrystal(String id, ElementalType type, String typeName) {
        return ITEMS.register(id, () -> new ElementalZCrystal(new Item.Properties(), type, typeName) {
            @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                tooltip.add(Component.translatable("tooltip.mega_showdown." + id + ".tooltip"));
                super.appendHoverText(stack, context, tooltip, flag);
            }
        });
    }

    public static void register() {
    }
}
