package com.cobblemon.yajatkaul.mega_showdown.config.structure;


import com.cobblemon.yajatkaul.mega_showdown.config.structure.abstracts.Effects;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public class FormeChange {
    public String msd_id, showdown_id, item_id, item_name, form_name;
    public Boolean battle_mode_only, tradable_form;
    public List<String> item_description, pokemons, aspects, default_aspects, required_aspects;
    public Integer custom_model_data;
    public Effects effects;

    public FormeChange() {
        pokemons = new ArrayList<>();
        aspects = new ArrayList<>();
        default_aspects = new ArrayList<>();
        required_aspects = new ArrayList<>();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(msd_id);
        buf.writeUtf(item_id);
        buf.writeUtf(item_name);
        buf.writeCollection(item_description, FriendlyByteBuf::writeUtf);
        buf.writeInt(custom_model_data);
        effects.write(buf);
        buf.writeCollection(pokemons, FriendlyByteBuf::writeUtf);
        buf.writeCollection(aspects, FriendlyByteBuf::writeUtf);
        buf.writeCollection(default_aspects, FriendlyByteBuf::writeUtf);
        buf.writeCollection(required_aspects, FriendlyByteBuf::writeUtf);
        buf.writeBoolean(battle_mode_only);
        buf.writeBoolean(tradable_form);
        buf.writeUtf(showdown_id);
        buf.writeUtf(form_name);
    }

    public static FormeChange read(FriendlyByteBuf buf) {
        FormeChange f = new FormeChange();
        f.msd_id = buf.readUtf();
        f.item_id = buf.readUtf();
        f.item_name = buf.readUtf();
        f.item_description = buf.readList(FriendlyByteBuf::readUtf);
        f.custom_model_data = buf.readInt();
        f.effects = Effects.read(buf);
        f.pokemons = buf.readList(FriendlyByteBuf::readUtf);
        f.aspects = buf.readList(FriendlyByteBuf::readUtf);
        f.default_aspects = buf.readList(FriendlyByteBuf::readUtf);
        f.required_aspects = buf.readList(FriendlyByteBuf::readUtf);
        f.battle_mode_only = buf.readBoolean();
        f.tradable_form = buf.readBoolean();
        f.showdown_id = buf.readUtf();
        f.form_name = buf.readUtf();
        return f;
    }
}