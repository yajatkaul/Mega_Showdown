package com.cobblemon.yajatkaul.mega_showdown.item.custom.utils;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;

import java.util.List;
import java.util.Map;

public class NoRenderArmorMaterial {
    public static final RegistryEntry<ArmorMaterial> NO_RENDER = RegistryEntry.of(new ArmorMaterial(
            // Defense values for each armor type
            Map.of(
                    ArmorItem.Type.BOOTS, 0,
                    ArmorItem.Type.LEGGINGS, 0,
                    ArmorItem.Type.CHESTPLATE, 1,
                    ArmorItem.Type.HELMET, 0
            ),
            0, // enchantability
            RegistryEntry.of(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC.value()), // equip sound as RegistryEntry
            () -> Ingredient.EMPTY, // repair ingredient supplier
            List.of(), // layers (empty list for no rendering)
            0.0f, // toughness
            0.0f  // knockback resistance
    ));
}