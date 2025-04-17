package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> MEGA_BRACELETS = createTag("mega_bracelets");
        public static final TagKey<Item> Z_RINGS = createTag("z_rings");
        public static final TagKey<Item> DYNAMAX_BAND = createTag("dynamax_bands");


        private static TagKey<Item> createTag(String name) {
            return TagKey.of(Registries.ITEM.getKey(), Identifier.of(MegaShowdown.MOD_ID, name));
        }
    }
}