package com.cobblemon.yajatkaul.mega_showdown.datamanage;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.component.ComponentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

import java.util.HashMap;
import java.util.UUID;

public class DataManage {

    public static final AttachmentType<Boolean> MEGA_DATA = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "mega_data"),
            builder -> builder
                    .copyOnDeath()
                    .initializer(() -> Boolean.FALSE)
                    .persistent(Codec.BOOL)
                    .syncWith(
                            PacketCodecs.BOOL,
                            AttachmentSyncPredicate.all()
                    )
    );

    public static final AttachmentType<PokeHandler> MEGA_POKEMON = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "mega_pokemon"),
            builder -> builder
                    .copyOnDeath()
                    .initializer(() -> new PokeHandler(new Pokemon()))
                    .persistent(PokeHandler.CODEC)
                    .syncWith(
                            PokeHandler.S2C_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );


    public static final AttachmentType<HashMap<UUID, Pokemon>> DATA_MAP = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "data_map"),
            builder -> builder
                    .initializer(() -> new HashMap<>()) // Default value
                    .persistent(Codec.unboundedMap(Uuids.CODEC, Pokemon.getCODEC()).xmap(HashMap::new, map -> map)) // Serialization to disk
                    .copyOnDeath()
                    .syncWith(
                            PacketCodec.of(
                                    (value, buf) -> {
                                        buf.writeMap(
                                                value,
                                                PacketCodecs.codec(Uuids.CODEC), // Fallback for UUID
                                                PacketCodecs.codec(Pokemon.getCODEC())
                                        );
                                    },
                                    buf -> buf.readMap(
                                            HashMap::new,
                                            PacketCodecs.codec(Uuids.CODEC), // Fallback for UUID
                                            PacketCodecs.codec(Pokemon.getCODEC())
                                    )
                            ),
                            AttachmentSyncPredicate.all() // Sync to all players
                    )
    );

    //Data Component

    public static final ComponentType<Pokemon> N_LUNAR = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "n_lunar"),
            ComponentType.<Pokemon>builder().codec(Pokemon.getCODEC()).build()
    );

    public static final ComponentType<Pokemon> N_SOLAR = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "n_solar"),
            ComponentType.<Pokemon>builder().codec(Pokemon.getCODEC()).build()
    );

    public static final AttachmentType<PokeHandler> N_LUNAR_POKEMON = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "n_lunar_pokemon"),
            builder -> builder
                    .initializer(() -> new PokeHandler(new Pokemon()))
                    .persistent(PokeHandler.CODEC)
                    .syncWith(
                            PokeHandler.S2C_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );

    public static final AttachmentType<PokeHandler> N_SOLAR_POKEMON = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "n_solar_pokemon"),
            builder -> builder
                    .initializer(() -> new PokeHandler(new Pokemon()))
                    .persistent(PokeHandler.CODEC)
                    .syncWith(
                            PokeHandler.S2C_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );

    public static final ComponentType<Pokemon> KYUREM_DATA = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "kyurem_data"),
            ComponentType.<Pokemon>builder().codec(Pokemon.getCODEC()).build()
    );

    public static final ComponentType<Pokemon> ZYGARDE_CUBE_DATA = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "zygarde_cube_data"),
            ComponentType.<Pokemon>builder().codec(Pokemon.getCODEC()).build()
    );

    public static final AttachmentType<PokeHandler> KYUREM_FUSED_WITH = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "kyurem_fused_with"),
            builder -> builder
                    .initializer(() -> new PokeHandler(new Pokemon()))
                    .persistent(PokeHandler.CODEC)
                    .syncWith(
                            PokeHandler.S2C_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );

    public static final ComponentType<Pokemon> CALYREX_DATA = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "calyrex_data"),
            ComponentType.<Pokemon>builder().codec(Pokemon.getCODEC()).build()
    );

    public static final ComponentType<NbtCompound> ZYGARDE_CUBE_INV = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "zygarde_cube_inv"),
            ComponentType.<NbtCompound>builder().codec(NbtCompound.CODEC).build()
    );

    public static final AttachmentType<PokeHandler> CALYREX_FUSED_WITH = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "calyrex_fused_with"),
            builder -> builder
                    .initializer(() -> new PokeHandler(new Pokemon()))
                    .persistent(PokeHandler.CODEC)
                    .syncWith(
                            PokeHandler.S2C_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );

    public static final ComponentType<Integer> CATALOGUE_PAGE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "catalogue_page"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static void registerDataComponentTypes() {
        MEGA_DATA.initializer();
        MEGA_POKEMON.initializer();

        N_LUNAR_POKEMON.initializer();
        N_SOLAR_POKEMON.initializer();

        CALYREX_FUSED_WITH.initializer();
        KYUREM_FUSED_WITH.initializer();

        DATA_MAP.initializer();
    }
}
