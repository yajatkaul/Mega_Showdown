package com.cobblemon.yajatkaul.megamons.datamanage;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.megamons.MegaShowdown;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

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

    public static final AttachmentType<Pokemon> MEGA_POKEMON = AttachmentRegistry.create(
            Identifier.of(MegaShowdown.MOD_ID, "mega_pokemon"),
            builder -> builder // Using a builder chain to configure the attachment data type
                    .copyOnDeath()
                    .initializer(() -> null) // A default value to provide if none is supplied
                    .persistent(Pokemon.getCODEC()) // How to save and load the data
                    .syncWith(
                            Pokemon.getS2C_CODEC(),  // How to turn the data into a packet to send to players
                            AttachmentSyncPredicate.all() // Who to send the data to
                    )
    );

    public static void registerDataComponentTypes() {

    }
}
