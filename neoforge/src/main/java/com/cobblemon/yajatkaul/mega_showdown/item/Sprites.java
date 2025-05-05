package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.item.custom.TeraShard;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.ITEMS;

public class Sprites {
    public static final DeferredItem<Item> MEGA_MODE_SPRITE = ITEMS.register("mega_mode_sprite",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BATTLE_BOND_SPRITE = ITEMS.register("battle_bond_sprite",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DYNAMAX_SPRITE = ITEMS.register("dynamax_sprite",
            () -> new Item(new Item.Properties()));

    public static void register(){
    }
}
