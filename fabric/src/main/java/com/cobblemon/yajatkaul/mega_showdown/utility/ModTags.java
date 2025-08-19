package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> MEGA_BRACELETS = createTag("mega_bracelet");
        public static final TagKey<Item> TERA_ORBS = createTag("tera_orb");
        public static final TagKey<Item> Z_RINGS = createTag("z_ring");
        public static final TagKey<Item> DYNAMAX_BAND = createTag("dynamax_band");
        public static final TagKey<Item> ARCEUS_FORM_CHANGE = createTag("arceus_form_change");


        private static TagKey<Item> createTag(String name) {
            return TagKey.of(Registries.ITEM.getKey(), Identifier.of(MegaShowdown.MOD_ID, name));
        }
    }
}