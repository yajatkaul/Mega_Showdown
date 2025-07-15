package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;


public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MegaShowdown.MOD_ID);

    public static final DeferredItem<Item> MEGA_BRACELET = ITEMS.register("megabracelet",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_RED_BRACELET = ITEMS.register("megabracelet_red",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_YELLOW_BRACELET = ITEMS.register("megabracelet_yellow",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_PINK_BRACELET = ITEMS.register("megabracelet_pink",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_GREEN_BRACELET = ITEMS.register("megabracelet_green",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_BLUE_BRACELET = ITEMS.register("megabracelet_blue",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_BLACK_BRACELET = ITEMS.register("megabracelet_black",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MAY_BRACELET = ITEMS.register("may_bracelet",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_RING = ITEMS.register("megaring",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> LYSANDRE_RING = ITEMS.register("lysandre_ring",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> BRENDAN_MEGA_CUFF = ITEMS.register("brendan_mega_cuff",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> KORRINA_GLOVE = ITEMS.register("korrina_glove",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MAXIE_GLASSES = ITEMS.register("maxie_glasses",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ARCHIE_ANCHOR = ITEMS.register("archie_anchor",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> LISIA_MEGA_TIARA = ITEMS.register("lisia_mega_tiara",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> DEBUG_STICK = ITEMS.register("debug_stick",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public InteractionResult interactLivingEntity(ItemStack arg, Player player, LivingEntity arg3, InteractionHand arg4) {
                    if(arg3 instanceof PokemonEntity pk){
                        player.displayClientMessage(Component.literal(String.valueOf(pk.getAspects()))
                                .withStyle(ChatFormatting.GREEN), true);
                    }

                    return InteractionResult.SUCCESS;
                }
            });

    public static final DeferredItem<Item> SCROLL_OF_DARKNESS = ITEMS.register("scroll_of_darkness",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.scroll_of_darkness.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SCROLL_OF_WATERS = ITEMS.register("scroll_of_waters",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.scroll_of_waters.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}