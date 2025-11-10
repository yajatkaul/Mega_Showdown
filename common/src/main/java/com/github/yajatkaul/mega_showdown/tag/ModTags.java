package com.github.yajatkaul.mega_showdown.tag;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> POWER_SPOT = createTag("power_spot");

        private static TagKey<Block> createTag(String string) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID ,string));
        }
    }

    public static class Items {
        public static final TagKey<Item> MEGA_BRACELETS = createTag("mega_bracelet");
        public static final TagKey<Item> MEGA_STONES = createTag("mega_stone");
        public static final TagKey<Item> Z_CRYSTALS = createTag("z_crystal");
        public static final TagKey<Item> Z_RINGS = createTag("z_ring");
        public static final TagKey<Item> TERA_ORBS = createTag("tera_orb");
        public static final TagKey<Item> DYNAMAX_BANDS = createTag("dynamax_bands");

        public static final TagKey<Item> OMNI_RING = createTag("omni_ring");

        private static TagKey<Item> createTag(String string) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID ,string));
        }
    }
}
