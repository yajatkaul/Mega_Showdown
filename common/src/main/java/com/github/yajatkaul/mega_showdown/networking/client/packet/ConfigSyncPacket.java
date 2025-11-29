package com.github.yajatkaul.mega_showdown.networking.client.packet;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ConfigSyncPacket(boolean outSideMega, boolean outSideUltraBurst) implements CustomPacketPayload {
    public static final ResourceLocation PACKET_ID = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "config_sync");
    public static final Type<ConfigSyncPacket> TYPE = new Type<>(PACKET_ID);

    public static final StreamCodec<ByteBuf, ConfigSyncPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ConfigSyncPacket::outSideMega,

            ByteBufCodecs.BOOL,
            ConfigSyncPacket::outSideUltraBurst,

            ConfigSyncPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
