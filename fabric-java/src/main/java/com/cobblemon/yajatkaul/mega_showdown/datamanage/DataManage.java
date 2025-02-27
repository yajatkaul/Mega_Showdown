package com.cobblemon.yajatkaul.mega_showdown.datamanage;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.serialization.Codec;
import kotlin.uuid.Uuid;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.util.UUID;

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

    public static final AttachmentType<UUID> BATTLE_ID = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "battle_id"),
            builder -> builder // Using a builder chain to configure the attachment data type
                    .copyOnDeath()
                    .initializer(() -> NIL_UUID) // A default value to provide if none is supplied
                    .persistent(Uuids.CODEC) // How to save and load the data
                    .syncWith(
                            Uuids.PACKET_CODEC,  // How to turn the data into a packet to send to players
                            AttachmentSyncPredicate.all() // Who to send the data to
                    )
    );

    public static void registerDataComponentTypes() {
        MEGA_DATA.initializer();
        MEGA_POKEMON.initializer();
        BATTLE_ID.initializer();

        PRIMAL_DATA.initializer();
        PRIMAL_POKEMON.initializer();
    }
}
