package com.github.yajatkaul.mega_showdown.networking.packets;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SecretSwordMoveSwapPacket(String name) implements CustomPacketPayload {
    public static final ResourceLocation PACKET_ID = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "secret_sword_move_swap");
    public static final Type<SecretSwordMoveSwapPacket> TYPE = new Type<>(PACKET_ID);

    public static final StreamCodec<ByteBuf, SecretSwordMoveSwapPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SecretSwordMoveSwapPacket::name,
            SecretSwordMoveSwapPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
