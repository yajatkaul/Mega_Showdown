package com.github.yajatkaul.mega_showdown.networking.server.packet;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record UltraBurstPacket(UUID pokemonId) implements CustomPacketPayload {
    public static final ResourceLocation PACKET_ID = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "ultra_burst");
    public static final Type<UltraBurstPacket> TYPE = new Type<>(PACKET_ID);

    public static final StreamCodec<ByteBuf, UltraBurstPacket> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            UltraBurstPacket::pokemonId,
            UltraBurstPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
