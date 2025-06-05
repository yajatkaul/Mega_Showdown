package com.cobblemon.yajatkaul.mega_showdown.item.custom.utils;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class NoRenderArmorMaterial {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, MegaShowdown.MOD_ID);

    public static final Holder<ArmorMaterial> NO_RENDER = ARMOR_MATERIALS.register("no_render",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 0);
                        map.put(ArmorItem.Type.LEGGINGS, 0);
                        map.put(ArmorItem.Type.CHESTPLATE, 1);
                        map.put(ArmorItem.Type.HELMET, 0);
                    }),
                    0,
                    SoundEvents.ARMOR_EQUIP_GENERIC,
                    () -> Ingredient.EMPTY,
                    List.of(),
                    0.0f,
                    0.0f
            )
    );

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}