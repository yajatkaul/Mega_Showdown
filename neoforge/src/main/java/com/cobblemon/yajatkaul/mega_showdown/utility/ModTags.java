package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> MEGA_BRACELETS = createTag("mega_bracelets");
        public static final TagKey<Item> MEGA_STONES = createTag("mega_stones");
        public static final TagKey<Item> Z_CRYSTALS = createTag("z_crystals");
        public static final TagKey<Item> Z_RINGS = createTag("z_rings");
        public static final TagKey<Item> TERA_ITEMS = createTag("tera_items");
        public static final TagKey<Item> DYNAMAX_BAND = createTag("dynamax_bands");


        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, name));
        }
    }
}
