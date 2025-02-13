package com.cobblemon.yajatkaul.mega_showdown.networking.packets;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MegaEvoBattle(String name, int age) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MegaEvoBattle> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega_evo_battle"));

    public static final StreamCodec<ByteBuf, MegaEvoBattle> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            MegaEvoBattle::name,
            ByteBufCodecs.VAR_INT,
            MegaEvoBattle::age,
            MegaEvoBattle::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


