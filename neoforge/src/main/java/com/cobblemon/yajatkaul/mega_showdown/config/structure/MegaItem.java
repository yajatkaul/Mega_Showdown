package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

public class MegaItem {
    public String msd_id, showdown_id, item_id, item_name, pokemon;
    public List<String> item_description, aspects;
    public Integer custom_model_data;

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(msd_id);
        buf.writeUtf(showdown_id);
        buf.writeUtf(item_id);
        buf.writeUtf(item_name);
        buf.writeCollection(item_description, FriendlyByteBuf::writeUtf);
        buf.writeInt(custom_model_data);
        buf.writeUtf(pokemon);
        buf.writeCollection(aspects, FriendlyByteBuf::writeUtf);
    }

    public static MegaItem read(FriendlyByteBuf buf) {
        MegaItem m = new MegaItem();
        m.msd_id = buf.readUtf();
        m.showdown_id = buf.readUtf();
        m.item_id = buf.readUtf();
        m.item_name = buf.readUtf();
        m.item_description = buf.readList(FriendlyByteBuf::readUtf);
        m.custom_model_data = buf.readInt();
        m.pokemon = buf.readUtf();
        m.aspects = buf.readList(FriendlyByteBuf::readUtf);
        return m;
    }
}