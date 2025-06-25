package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.item.custom.tera.TeraOrb;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.tera.TeraShard;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.ITEMS;

public class TeraMoves {
    public static final DeferredItem<Item> TERA_ORB = ITEMS.register("tera_orb",
            () -> new TeraOrb(new Item.Properties().stacksTo(1).durability(100)));

    public static final DeferredItem<Item> BUG_TERA_SHARD = ITEMS.register("bug_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> DARK_TERA_SHARD = ITEMS.register("dark_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> DRAGON_TERA_SHARD = ITEMS.register("dragon_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> ELECTRIC_TERA_SHARD = ITEMS.register("electric_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> FAIRY_TERA_SHARD = ITEMS.register("fairy_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> FIGHTING_TERA_SHARD = ITEMS.register("fighting_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> FIRE_TERA_SHARD = ITEMS.register("fire_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> FLYING_TERA_SHARD = ITEMS.register("flying_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> GHOST_TERA_SHARD = ITEMS.register("ghost_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> GRASS_TERA_SHARD = ITEMS.register("grass_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> GROUND_TERA_SHARD = ITEMS.register("ground_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> ICE_TERA_SHARD = ITEMS.register("ice_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> NORMAL_TERA_SHARD = ITEMS.register("normal_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> POISON_TERA_SHARD = ITEMS.register("poison_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> PSYCHIC_TERA_SHARD = ITEMS.register("psychic_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> ROCK_TERA_SHARD = ITEMS.register("rock_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> STEEL_TERA_SHARD = ITEMS.register("steel_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> STELLAR_TERA_SHARD = ITEMS.register("stellar_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static final DeferredItem<Item> WATER_TERA_SHARD = ITEMS.register("water_tera_shard",
            () -> new TeraShard(new Item.Properties().stacksTo(50)));

    public static void register() {
    }
}
