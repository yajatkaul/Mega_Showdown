package com.cobblemon.yajatkaul.mega_showdown.networking.packets;

import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownCustomsConfig;
import com.cobblemon.yajatkaul.mega_showdown.config.structure.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;


public record MSDCustomPacket(
        List<Fusion> fusionItems,
        List<FormeChange> formeChanges,
        List<HeldItem> heldItems,
        List<MegaItem> megaItems,
        List<Gmax> gmaxItems,
        List<KeyItems> keyItems
) implements CustomPayload {

    public static final Identifier PACKET_ID = Identifier.of("megashowdown", "msd_custom_packet");
    public static final Id<MSDCustomPacket> ID = new Id<>(PACKET_ID);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, MSDCustomPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.collection(ArrayList::new, Fusion.CODEC), MSDCustomPacket::fusionItems,
            PacketCodecs.collection(ArrayList::new, FormeChange.CODEC), MSDCustomPacket::formeChanges,
            PacketCodecs.collection(ArrayList::new, HeldItem.CODEC), MSDCustomPacket::heldItems,
            PacketCodecs.collection(ArrayList::new, MegaItem.CODEC), MSDCustomPacket::megaItems,
            PacketCodecs.collection(ArrayList::new, Gmax.CODEC), MSDCustomPacket::gmaxItems,
            PacketCodecs.collection(ArrayList::new, KeyItems.CODEC), MSDCustomPacket::keyItems,
            MSDCustomPacket::new
    );

    public static void execute(MSDCustomPacket payload, ClientPlayNetworking.Context context){
        context.client().execute(() -> {
            ShowdownCustomsConfig.fusionItems = payload.fusionItems();
            ShowdownCustomsConfig.formeChange = payload.formeChanges();
            ShowdownCustomsConfig.heldItems = payload.heldItems();
            ShowdownCustomsConfig.megaItems = payload.megaItems();
            ShowdownCustomsConfig.gmax = payload.gmaxItems();
            ShowdownCustomsConfig.keyItems = payload.keyItems();
        });
    }

    public static void sendToClient(ServerPlayerEntity player, MSDCustomPacket packet) {
        ServerPlayNetworking.send(player, packet);
    }
}