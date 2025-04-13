package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.ITEMS;


public class DynamaxItems {

    public static final DeferredItem<Item> DYNAMAX_BAND = ITEMS.register("dynamax_band",
            () -> new Dynamax(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> DYNAMAX_CANDY = ITEMS.register("dynamax_candy",
            () -> new DynamaxCandy(new Item.Properties()));

    public static final DeferredItem<Item> WISHING_STAR = ITEMS.register("wishing_star",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack arg, TooltipContext arg2, List<Component> tooltipComponents, TooltipFlag arg3) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.wishing_star.tooltip"));
                    super.appendHoverText(arg, arg2, tooltipComponents, arg3);
                }
            });

    public static final DeferredItem<Item> MAX_HONEY = ITEMS.register("max_honey",
            MaxHoney::new);

    public static final DeferredItem<Item> MAX_MUSHROOM = ITEMS.register("max_mushroom",
            MaxMushroom::new);

    public static final DeferredItem<Item> MAX_SOUP = ITEMS.register("max_soup",
            () -> new MaxSoup(new Item.Properties()));

    public static final DeferredItem<Item> SWEET_MAX_SOUP = ITEMS.register("sweet_max_soup",
            () -> new SweetMaxSoup(new Item.Properties()));

    public static void register(){

    }
}
