package com.github.yajatkaul.mega_showdown.components;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.codec.*;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.mojang.serialization.Codec;
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

    public static final Supplier<DataComponentType<ShowdownItem>> SHOWDOWN_ITEM_COMPONENT = REGISTRAR.register(
            "showdown_item_component",
            () -> DataComponentType.<ShowdownItem>builder()
                    .persistent(ShowdownItem.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<DuFusion>> DU_FUSION_COMPONENT = REGISTRAR.register(
            "du_fusion_component",
            () -> DataComponentType.<DuFusion>builder()
                    .persistent(DuFusion.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<SoloFusion>> SOLO_FUSION_COMPONENT = REGISTRAR.register(
            "solo_fusion_component",
            () -> DataComponentType.<SoloFusion>builder()
                    .persistent(SoloFusion.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<HeldItemFormChange>> HELD_ITEM_FORM_CHANGE_COMPONENT = REGISTRAR.register(
            "held_item_form_change_component",
            () -> DataComponentType.<HeldItemFormChange>builder()
                    .persistent(HeldItemFormChange.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<FormChangeToggleInteractItem>> FORM_CHANGE_TOGGLE_INTERACT_COMPONENT = REGISTRAR.register(
            "form_change_toggle_interact_component",
            () -> DataComponentType.<FormChangeToggleInteractItem>builder()
                    .persistent(FormChangeToggleInteractItem.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<FormChangeInteractItem>> FORM_CHANGE_INTERACT_COMPONENT = REGISTRAR.register(
            "form_change_interact_component",
            () -> DataComponentType.<FormChangeInteractItem>builder()
                    .persistent(FormChangeInteractItem.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> LIKO_PENDANT_TICK_COMPONENT = REGISTRAR.register(
            "liko_pendant_tick_component",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static void register() {
        REGISTRAR.register();
    }
}
