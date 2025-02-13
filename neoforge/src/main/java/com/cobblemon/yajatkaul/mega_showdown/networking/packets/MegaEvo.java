package com.cobblemon.yajatkaul.mega_showdown.networking.packets;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MegaEvo(String name) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<MegaEvo> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega_evo"));

    public static final StreamCodec<ByteBuf, MegaEvo> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            MegaEvo::name,
            MegaEvo::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
