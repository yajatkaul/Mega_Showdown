package com.cobblemon.yajatkaul.mega_showdown.dataAttachments;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DataManage {

    //Data Component
    public static final ComponentType<Pokemon> POKEMON_STORAGE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "pokemon_storage"),
            ComponentType.<Pokemon>builder().codec(Pokemon.getCODEC()).build()
    );

    public static final ComponentType<NbtCompound> ZYGARDE_CUBE_INV = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "zygarde_cube_inv"),
            ComponentType.<NbtCompound>builder().codec(NbtCompound.CODEC).build()
    );

    public static final ComponentType<Integer> CATALOGUE_PAGE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "catalogue_page"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> LIKO_PENDANT_TICK = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "liko_pendant"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static void registerDataComponentTypes() {

    }
}
