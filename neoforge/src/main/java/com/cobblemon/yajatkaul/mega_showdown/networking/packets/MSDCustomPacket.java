package com.cobblemon.yajatkaul.mega_showdown.networking.packets;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.config.structure.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public record MSDCustomPacket(
        List<Fusion> fusionItems,
        List<FormeChange> formeChanges,
        List<HeldItem> heldItems,
        List<MegaItem> megaItems,
        List<Gmax> gmaxItems,
        List<KeyItems> keyItems
) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "sync_custom_config");
    public static final CustomPacketPayload.Type<MSDCustomPacket> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, MSDCustomPacket> STREAM_CODEC = StreamCodec.of(
            MSDCustomPacket::write,
            MSDCustomPacket::read
    );

    private static void write(FriendlyByteBuf buf, MSDCustomPacket packet) {
        buf.writeCollection(packet.fusionItems, (b, f) -> f.write(b));
        buf.writeCollection(packet.formeChanges, (b, f) -> f.write(b));
        buf.writeCollection(packet.heldItems, (b, h) -> h.write(b));
        buf.writeCollection(packet.megaItems, (b, m) -> m.write(b));
        buf.writeCollection(packet.gmaxItems, (b, g) -> g.write(b));
        buf.writeCollection(packet.keyItems, (b, k) -> k.write(b));
    }

    private static MSDCustomPacket read(FriendlyByteBuf buf) {
        List<Fusion> fusionItems = buf.readList(Fusion::read);
        List<FormeChange> formeChanges = buf.readList(FormeChange::read);
        List<HeldItem> heldItems = buf.readList(HeldItem::read);
        List<MegaItem> megaItems = buf.readList(MegaItem::read);
        List<Gmax> gmaxItems = buf.readList(Gmax::read);
        List<KeyItems> keyItems = buf.readList(KeyItems::read);
        return new MSDCustomPacket(fusionItems, formeChanges, heldItems, megaItems, gmaxItems, keyItems);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}