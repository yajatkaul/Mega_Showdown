package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import com.cobblemon.yajatkaul.mega_showdown.config.structure.abstracts.Effects;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.ArrayList;

public class KeyItems {
    public String msd_id;
    public String item_id;
    public String item_name;
    public ArrayList<String> item_description;
    public Integer custom_model_data;
    public ArrayList<String> pokemons;
    public ArrayList<String> aspects;
    public ArrayList<String> default_aspects;
    public ArrayList<ArrayList<String>> toggle_aspects;
    public ArrayList<String> required_aspects;
    public Boolean tradable_form;
    public Effects effects;

    public KeyItems() {
        this.pokemons = new ArrayList<>();
        this.aspects = new ArrayList<>();
        this.default_aspects = new ArrayList<>();
        this.toggle_aspects = new ArrayList<>();
        this.required_aspects = new ArrayList<>();
    }

    public static final PacketCodec<RegistryByteBuf, KeyItems> CODEC = PacketCodec.of(
            KeyItems::writeToBuf,
            KeyItems::readFromBuf
    );

    public void writeToBuf(RegistryByteBuf buf) {
        PacketCodecs.STRING.encode(buf, msd_id);
        PacketCodecs.STRING.encode(buf, item_id);
        PacketCodecs.STRING.encode(buf, item_name);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, item_description);
        PacketCodecs.INTEGER.encode(buf, custom_model_data);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, pokemons);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, aspects);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, default_aspects);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING)).encode(buf, toggle_aspects);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, required_aspects);
        PacketCodecs.BOOL.encode(buf, tradable_form);
        Effects.CODEC.encode(buf, effects);
    }

    public static KeyItems readFromBuf(RegistryByteBuf buf) {
        KeyItems keyItems = new KeyItems();
        keyItems.msd_id = PacketCodecs.STRING.decode(buf);
        keyItems.item_id = PacketCodecs.STRING.decode(buf);
        keyItems.item_name = PacketCodecs.STRING.decode(buf);
        keyItems.item_description = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        keyItems.custom_model_data = PacketCodecs.INTEGER.decode(buf);
        keyItems.pokemons = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        keyItems.aspects = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        keyItems.default_aspects = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        keyItems.toggle_aspects = PacketCodecs.collection(ArrayList::new, PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING)).decode(buf);
        keyItems.required_aspects = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        keyItems.tradable_form = PacketCodecs.BOOL.decode(buf);
        keyItems.effects = Effects.CODEC.decode(buf);
        return keyItems;
    }
}