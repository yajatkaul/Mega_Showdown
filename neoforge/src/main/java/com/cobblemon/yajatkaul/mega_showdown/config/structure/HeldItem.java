package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public class HeldItem {
    public String msd_id, showdown_id, item_id, item_name;
    public List<String> item_description;
    public Integer custom_model_data;

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(msd_id);
        buf.writeUtf(showdown_id);
        buf.writeUtf(item_id);
        buf.writeUtf(item_name);
        buf.writeCollection(item_description, FriendlyByteBuf::writeUtf);
        buf.writeInt(custom_model_data);
    }

    public static HeldItem read(FriendlyByteBuf buf) {
        HeldItem h = new HeldItem();
        h.msd_id = buf.readUtf();
        h.showdown_id = buf.readUtf();
        h.item_id = buf.readUtf();
        h.item_name = buf.readUtf();
        h.item_description = buf.readList(FriendlyByteBuf::readUtf);
        h.custom_model_data = buf.readInt();
        return h;
    }
}