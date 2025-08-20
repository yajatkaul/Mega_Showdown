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
        public static final TagKey<Item> MEGA_BRACELETS = createTag("mega_bracelet");
        public static final TagKey<Item> MEGA_STONES = createTag("mega_stone");
        public static final TagKey<Item> Z_CRYSTALS = createTag("z_crystal");
        public static final TagKey<Item> Z_RINGS = createTag("z_ring");
        public static final TagKey<Item> TERA_ORBS = createTag("tera_orb");
        public static final TagKey<Item> DYNAMAX_BAND = createTag("dynamax_band");

        public static final TagKey<Item> OMNI_RING = createTag("omni_ring");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, name));
        }
    }
}
