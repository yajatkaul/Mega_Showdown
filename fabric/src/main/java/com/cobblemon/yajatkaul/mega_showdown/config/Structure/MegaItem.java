package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.ArrayList;

public class MegaItem {
    public String msd_id;
    public String showdown_id;
    public String item_id;
    public String item_name;
    public ArrayList<String> item_description;
    public Integer custom_model_data;
    public String pokemon;
    public ArrayList<String> aspects;

    public MegaItem() {
        this.aspects = new ArrayList<>();
    }

    public static final PacketCodec<RegistryByteBuf, MegaItem> CODEC = PacketCodec.of(
            MegaItem::writeToBuf,
            MegaItem::readFromBuf
    );

    public void writeToBuf(RegistryByteBuf buf) {
        PacketCodecs.STRING.encode(buf, msd_id);
        PacketCodecs.STRING.encode(buf, showdown_id);
        PacketCodecs.STRING.encode(buf, item_id);
        PacketCodecs.STRING.encode(buf, item_name);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, item_description);
        PacketCodecs.INTEGER.encode(buf, custom_model_data);
        PacketCodecs.STRING.encode(buf, pokemon);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, aspects);
    }

    public static MegaItem readFromBuf(RegistryByteBuf buf) {
        MegaItem megaItem = new MegaItem();
        megaItem.showdown_id = PacketCodecs.STRING.decode(buf);
        megaItem.msd_id = PacketCodecs.STRING.decode(buf);
        megaItem.item_id = PacketCodecs.STRING.decode(buf);
        megaItem.item_name = PacketCodecs.STRING.decode(buf);
        megaItem.item_description = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        megaItem.custom_model_data = PacketCodecs.INTEGER.decode(buf);
        megaItem.pokemon = PacketCodecs.STRING.decode(buf);
        megaItem.aspects = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        return megaItem;
    }
}