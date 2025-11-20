package com.github.yajatkaul.mega_showdown.components;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class MegaShowdownDataComponents {
    private static final DeferredRegister<DataComponentType<?>> REGISTRAR =
            DeferredRegister.create(MegaShowdown.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final Supplier<DataComponentType<CompoundTag>> NBT_POKEMON = REGISTRAR.register(
            "nbt_pokemon",
            () -> DataComponentType.<CompoundTag>builder()
                    .persistent(CompoundTag.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<CompoundTag>> NBT_INV = REGISTRAR.register(
            "nbt_inv",
            () -> DataComponentType.<CompoundTag>builder()
                    .persistent(CompoundTag.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> LIKO_PENDANT_TICK_COMPONENT = REGISTRAR.register(
            "liko_pendant_tick_component",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<String>> REGISTRY_TYPE_COMPONENT = REGISTRAR.register(
            "registry_type_component",
            () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> RESOURCE_LOCATION_COMPONENT = REGISTRAR.register(
            "registry_location_component",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .build()
    );

    public static void register() {
        REGISTRAR.register();
    }
}
