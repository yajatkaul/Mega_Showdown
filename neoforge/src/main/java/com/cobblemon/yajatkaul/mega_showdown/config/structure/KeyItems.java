package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import com.cobblemon.yajatkaul.mega_showdown.config.structure.abstracts.Effects;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

public class KeyItems {
    public String msd_id, item_id, item_name;
    public List<String> item_description, pokemons, aspects, default_aspects, required_aspects;
    public List<List<String>> toggle_aspects;
    public Integer custom_model_data;
    public Boolean tradable_form;
    public Effects effects;

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(msd_id);
        buf.writeUtf(item_id);
        buf.writeUtf(item_name);
        buf.writeCollection(item_description, FriendlyByteBuf::writeUtf);
        buf.writeInt(custom_model_data);
        buf.writeCollection(pokemons, FriendlyByteBuf::writeUtf);
        buf.writeCollection(aspects, FriendlyByteBuf::writeUtf);
        buf.writeCollection(default_aspects, FriendlyByteBuf::writeUtf);
        buf.writeCollection(toggle_aspects, (b, list) -> b.writeCollection(list, FriendlyByteBuf::writeUtf));
        buf.writeCollection(required_aspects, FriendlyByteBuf::writeUtf);
        buf.writeBoolean(tradable_form);
        effects.write(buf);
    }

    public static KeyItems read(FriendlyByteBuf buf) {
        KeyItems k = new KeyItems();
        k.msd_id = buf.readUtf();
        k.item_id = buf.readUtf();
        k.item_name = buf.readUtf();
        k.item_description = buf.readList(FriendlyByteBuf::readUtf);
        k.custom_model_data = buf.readInt();
        k.pokemons = buf.readList(FriendlyByteBuf::readUtf);
        k.aspects = buf.readList(FriendlyByteBuf::readUtf);
        k.default_aspects = buf.readList(FriendlyByteBuf::readUtf);
        k.toggle_aspects = buf.readList(b -> b.readList(FriendlyByteBuf::readUtf));
        k.required_aspects = buf.readList(FriendlyByteBuf::readUtf);
        k.tradable_form = buf.readBoolean();
        k.effects = Effects.read(buf);
        return k;
    }
}