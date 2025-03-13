package com.cobblemon.yajatkaul.mega_showdown.datamanage;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.item.components.PokemonItemComponent;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.serialization.Codec;
import kotlin.uuid.Uuid;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.component.ComponentType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static net.minecraft.util.Util.NIL_UUID;

public class DataManage {

    public static final AttachmentType<Boolean> MEGA_DATA = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "mega_data"),
            builder -> builder // Using a builder chain to configure the attachment data type
                    .copyOnDeath()
                    .initializer(() -> Boolean.FALSE) // A default value to provide if none is supplied
                    .persistent(Codec.BOOL) // How to save and load the data
                    .syncWith(
                            PacketCodecs.BOOL,  // How to turn the data into a packet to send to players
                            AttachmentSyncPredicate.all() // Who to send the data to
                    )
    );

    public static final AttachmentType<Boolean> PRIMAL_DATA = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "primal_data"),
            builder -> builder // Using a builder chain to configure the attachment data type
                    .copyOnDeath()
                    .initializer(() -> Boolean.FALSE) // A default value to provide if none is supplied
                    .persistent(Codec.BOOL) // How to save and load the data
                    .syncWith(
                            PacketCodecs.BOOL,  // How to turn the data into a packet to send to players
                            AttachmentSyncPredicate.all() // Who to send the data to
                    )
    );

    public static final AttachmentType<Pokemon> MEGA_POKEMON = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "mega_pokemon"),
            builder -> builder // Using a builder chain to configure the attachment data type
                    .copyOnDeath()
                    .initializer(() -> new Pokemon()) // A default value to provide if none is supplied
                    .persistent(Pokemon.getCODEC()) // How to save and load the data
                    .syncWith(
                            Pokemon.getS2C_CODEC(),  // How to turn the data into a packet to send to players
                            AttachmentSyncPredicate.all() // Who to send the data to
                    )
    );

    public static final AttachmentType<Pokemon> PRIMAL_POKEMON = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "primal_pokemon"),
            builder -> builder // Using a builder chain to configure the attachment data type
                    .copyOnDeath()
                    .initializer(() -> new Pokemon()) // A default value to provide if none is supplied
                    .persistent(Pokemon.getCODEC()) // How to save and load the data
                    .syncWith(
                            Pokemon.getS2C_CODEC(),  // How to turn the data into a packet to send to players
                            AttachmentSyncPredicate.all() // Who to send the data to
                    )
    );

    // Define the Codec for HashMap<UUID, Pokemon>
    public static final Codec<HashMap<UUID, Pokemon>> DATA_MAP_CODEC =
            Codec.unboundedMap(
                    Uuids.CODEC,         // UUID Codec (Fabric uses `Uuids.CODEC`)
                    Pokemon.getCODEC()   // Codec for Pokemon (Ensure this exists)
            ).xmap(HashMap::new, map -> map);


    public static final AttachmentType<HashMap<UUID, Pokemon>> DATA_MAP = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "data_map"),
            builder -> builder
                    .initializer(() -> new HashMap<>()) // Default value
                    .persistent(Codec.unboundedMap(Uuids.CODEC, Pokemon.getCODEC()).xmap(HashMap::new, map -> map)) // Serialization to disk
                    .copyOnDeath() // Copy data on entity death
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

    public static final AttachmentType<Pokemon> N_LUNAR_POKEMON = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "n_lunar_pokemon"),
            builder -> builder // Using a builder chain to configure the attachment data type
                    .copyOnDeath()
                    .initializer(() -> new Pokemon()) // A default value to provide if none is supplied
                    .persistent(Pokemon.getCODEC()) // How to save and load the data
                    .syncWith(
                            Pokemon.getS2C_CODEC(),  // How to turn the data into a packet to send to players
                            AttachmentSyncPredicate.all() // Who to send the data to
                    )
    );

    public static final AttachmentType<Pokemon> N_SOLAR_POKEMON = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "n_solar_pokemon"),
            builder -> builder // Using a builder chain to configure the attachment data type
                    .copyOnDeath()
                    .initializer(() -> new Pokemon()) // A default value to provide if none is supplied
                    .persistent(Pokemon.getCODEC()) // How to save and load the data
                    .syncWith(
                            Pokemon.getS2C_CODEC(),  // How to turn the data into a packet to send to players
                            AttachmentSyncPredicate.all() // Who to send the data to
                    )
    );

    public static final ComponentType<Pokemon> KYUREM_DATA = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "kyurem_data"),
            ComponentType.<Pokemon>builder().codec(Pokemon.getCODEC()).build()
    );

    public static final AttachmentType<Pokemon> KYUREM_FUSED_WITH = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "kyurem_fused_with"),
            builder -> builder
                    .copyOnDeath()
                    .initializer(() -> new Pokemon())
                    .persistent(Pokemon.getCODEC())
                    .syncWith(
                            Pokemon.getS2C_CODEC(),
                            AttachmentSyncPredicate.all()
                    )
    );

    public static final ComponentType<Pokemon> CALYREX_DATA = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MegaShowdown.MOD_ID, "calyrex_data"),
            ComponentType.<Pokemon>builder().codec(Pokemon.getCODEC()).build()
    );

    public static final AttachmentType<Pokemon> CALYREX_FUSED_WITH = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "calyrex_fused_with"),
            builder -> builder
                    .copyOnDeath()
                    .initializer(() -> new Pokemon())
                    .persistent(Pokemon.getCODEC())
                    .syncWith(
                            Pokemon.getS2C_CODEC(),
                            AttachmentSyncPredicate.all()
                    )
    );

    public static void registerDataComponentTypes() {
        MEGA_DATA.initializer();
        MEGA_POKEMON.initializer();

        PRIMAL_DATA.initializer();
        PRIMAL_POKEMON.initializer();

        N_LUNAR_POKEMON.initializer();
        N_SOLAR_POKEMON.initializer();

        CALYREX_FUSED_WITH.initializer();
        KYUREM_FUSED_WITH.initializer();

        DATA_MAP.initializer();
    }
}
