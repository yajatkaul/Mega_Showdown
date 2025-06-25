package com.cobblemon.yajatkaul.mega_showdown.trinket.renderers;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;

import java.util.List;
import java.util.Map;

public class NoRenderArmorMaterial {
    public static final RegistryEntry<ArmorMaterial> NO_RENDER = RegistryEntry.of(new ArmorMaterial(
            Map.of(
                    ArmorItem.Type.BOOTS, 0,
                    ArmorItem.Type.LEGGINGS, 0,
                    ArmorItem.Type.CHESTPLATE, 1,
                    ArmorItem.Type.HELMET, 0
            ),
            0,
            RegistryEntry.of(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC.value()),
            () -> Ingredient.EMPTY,
            List.of(),
            0.0f,
            0.0f
    ));
}