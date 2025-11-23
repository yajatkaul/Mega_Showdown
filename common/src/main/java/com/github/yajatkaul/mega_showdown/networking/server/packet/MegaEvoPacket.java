package com.github.yajatkaul.mega_showdown.networking.server.packet;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record MegaEvoPacket(UUID pokemonId) implements CustomPacketPayload {
    public static final ResourceLocation PACKET_ID = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega_evo");
    public static final Type<MegaEvoPacket> TYPE = new Type<>(PACKET_ID);

    public static final StreamCodec<ByteBuf, MegaEvoPacket> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            MegaEvoPacket::pokemonId,
            MegaEvoPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
