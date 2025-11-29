package com.github.yajatkaul.mega_showdown.networking.client.packet;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record InteractionWheelPacket(boolean shouldMega, boolean shouldUltra, boolean canMega,
                                     boolean canUltra) implements CustomPacketPayload {
    public static final ResourceLocation PACKET_ID = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "interaction_wheel_packet");
    public static final Type<InteractionWheelPacket> TYPE = new Type<>(PACKET_ID);

    public static final StreamCodec<ByteBuf, InteractionWheelPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            InteractionWheelPacket::shouldMega,

            ByteBufCodecs.BOOL,
            InteractionWheelPacket::shouldUltra,

            ByteBufCodecs.BOOL,
            InteractionWheelPacket::canMega,

            ByteBufCodecs.BOOL,
            InteractionWheelPacket::canUltra,

            InteractionWheelPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
