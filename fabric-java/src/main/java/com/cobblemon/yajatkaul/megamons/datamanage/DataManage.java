package com.cobblemon.yajatkaul.megamons.datamanage;

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
                    .initializer(() -> false) // A default value to provide if none is supplied
                    .persistent(Codec.BOOL) // How to save and load the data
                    .syncWith(
                            PacketCodecs.BOOL,  // How to turn the data into a packet to send to players
                            AttachmentSyncPredicate.all() // Who to send the data to
                    )
    );

    public static void registerDataComponentTypes() {

    }
}
