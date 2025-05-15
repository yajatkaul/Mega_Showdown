package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.ArrayList;

public class HeldItem {
    public String msd_id;
    public String showdown_id;
    public String item_id;
    public String item_name;
    public ArrayList<String> item_description;
    public Integer custom_model_data;

    public HeldItem() {
    }

    public static final PacketCodec<RegistryByteBuf, HeldItem> CODEC = PacketCodec.of(
            HeldItem::writeToBuf,
            HeldItem::readFromBuf
    );

    public void writeToBuf(RegistryByteBuf buf) {
        PacketCodecs.STRING.encode(buf, msd_id);
        PacketCodecs.STRING.encode(buf, showdown_id);
        PacketCodecs.STRING.encode(buf, item_id);
        PacketCodecs.STRING.encode(buf, item_name);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, item_description);
        PacketCodecs.INTEGER.encode(buf, custom_model_data);
    }

    public static HeldItem readFromBuf(RegistryByteBuf buf) {
        HeldItem heldItem = new HeldItem();
        heldItem.msd_id = PacketCodecs.STRING.decode(buf);
        heldItem.showdown_id = PacketCodecs.STRING.decode(buf);
        heldItem.item_id = PacketCodecs.STRING.decode(buf);
        heldItem.item_name = PacketCodecs.STRING.decode(buf);
        heldItem.item_description = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        heldItem.custom_model_data = PacketCodecs.INTEGER.decode(buf);
        return heldItem;
    }
}