package com.cobblemon.yajatkaul.mega_showdown.networking.packets;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record UltraTrans(String name) implements CustomPacketPayload{
    public static final Type<UltraTrans> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "ultra_trans"));

    public static final StreamCodec<ByteBuf, UltraTrans> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            UltraTrans::name,
            UltraTrans::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
