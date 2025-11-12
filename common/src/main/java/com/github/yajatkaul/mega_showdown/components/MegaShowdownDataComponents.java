package com.github.yajatkaul.mega_showdown.components;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Supplier;

public class MegaShowdownDataComponents {
    private static final DeferredRegister<DataComponentType<?>> REGISTRAR =
            DeferredRegister.create(MegaShowdown.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final Supplier<DataComponentType<MegaGimmick>> MEGA_STONE_COMPONENT = REGISTRAR.register(
            "mega_stone_component",
            () -> DataComponentType.<MegaGimmick>builder()
                    .persistent(MegaGimmick.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<CompoundTag>> NBT_COMPONENT = REGISTRAR.register(
            "nbt_component",
            () -> DataComponentType.<CompoundTag>builder()
                    .persistent(CompoundTag.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<CompoundTag>> NBT_2_COMPONENT = REGISTRAR.register(
            "nbt_2_component",
            () -> DataComponentType.<CompoundTag>builder()
                    .persistent(CompoundTag.CODEC)
                    .build()
    );

    public static void register() {
        REGISTRAR.register();
    }
}
