package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import com.cobblemon.yajatkaul.mega_showdown.config.structure.abstracts.Effects;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.ArrayList;


public class FormeChange {
    public String msd_id;
    public String showdown_id;
    public String item_id;
    public Boolean battle_mode_only;
    public Boolean tradable_form;
    public String item_name;
    public ArrayList<String> item_description;
    public Integer custom_model_data;
    public Effects effects;
    public ArrayList<String> pokemons;
    public ArrayList<String> aspects;
    public ArrayList<String> default_aspects;
    public ArrayList<String> required_aspects;
    public String form_name;

    public FormeChange() {
        this.pokemons = new ArrayList<>();
        this.aspects = new ArrayList<>();
        this.default_aspects = new ArrayList<>();
        this.required_aspects = new ArrayList<>();
    }

    public static final PacketCodec<RegistryByteBuf, FormeChange> CODEC = PacketCodec.of(
            FormeChange::writeToBuf,
            FormeChange::readFromBuf
    );

    public void writeToBuf(RegistryByteBuf buf) {
        PacketCodecs.STRING.encode(buf, msd_id);
        PacketCodecs.STRING.encode(buf, item_id);
        PacketCodecs.STRING.encode(buf, item_name);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, item_description);
        PacketCodecs.INTEGER.encode(buf, custom_model_data);
        Effects.CODEC.encode(buf, effects);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, pokemons);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, aspects);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, default_aspects);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, required_aspects);
        PacketCodecs.BOOL.encode(buf, battle_mode_only);
        PacketCodecs.BOOL.encode(buf, tradable_form);
        PacketCodecs.STRING.encode(buf, showdown_id);
        PacketCodecs.STRING.encode(buf, form_name);
    }

    public static FormeChange readFromBuf(RegistryByteBuf buf) {
        FormeChange formeChange = new FormeChange();
        formeChange.msd_id = PacketCodecs.STRING.decode(buf);
        formeChange.item_id = PacketCodecs.STRING.decode(buf);
        formeChange.item_name = PacketCodecs.STRING.decode(buf);
        formeChange.item_description = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        formeChange.custom_model_data = PacketCodecs.INTEGER.decode(buf);
        formeChange.effects = Effects.CODEC.decode(buf);
        formeChange.pokemons = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        formeChange.aspects = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        formeChange.default_aspects = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        formeChange.required_aspects = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        formeChange.battle_mode_only = PacketCodecs.BOOL.decode(buf);
        formeChange.tradable_form = PacketCodecs.BOOL.decode(buf);
        formeChange.showdown_id = PacketCodecs.STRING.decode(buf);
        formeChange.form_name = PacketCodecs.STRING.decode(buf);
        return formeChange;
    }
}