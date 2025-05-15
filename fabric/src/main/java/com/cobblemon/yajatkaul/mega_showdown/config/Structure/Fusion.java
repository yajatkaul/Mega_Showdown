package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import com.cobblemon.yajatkaul.mega_showdown.config.structure.abstracts.Effects;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.ArrayList;

public class Fusion {
    public String msd_id;
    public String item_id;
    public String item_name;
    public ArrayList<String> item_description;
    public Boolean tradable_form;
    public Integer custom_model_data;
    public Effects effects;
    public ArrayList<String> fusion_aspects;
    public ArrayList<String> default_aspects;
    public ArrayList<String> fusion_mon;
    public ArrayList<String> required_aspects_fusion_mon;
    public ArrayList<String> fuse_with_mon;
    public ArrayList<String> required_aspects_fuse_with_mon;

    public static final PacketCodec<RegistryByteBuf, Fusion> CODEC = PacketCodec.of(
            Fusion::writeToBuf,
            Fusion::readFromBuf
    );

    public void writeToBuf(RegistryByteBuf buf) {
        PacketCodecs.STRING.encode(buf, msd_id);
        PacketCodecs.STRING.encode(buf, item_id);
        PacketCodecs.STRING.encode(buf, item_name);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, item_description);
        PacketCodecs.INTEGER.encode(buf, custom_model_data);
        PacketCodecs.BOOL.encode(buf, tradable_form);
        Effects.CODEC.encode(buf, effects);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, fusion_aspects);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, default_aspects);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, fusion_mon);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, fuse_with_mon);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, required_aspects_fusion_mon);
        PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).encode(buf, required_aspects_fuse_with_mon);
    }

    public static Fusion readFromBuf(RegistryByteBuf buf) {
        Fusion fusion = new Fusion();
        fusion.msd_id = PacketCodecs.STRING.decode(buf);
        fusion.item_id = PacketCodecs.STRING.decode(buf);
        fusion.item_name = PacketCodecs.STRING.decode(buf);
        fusion.item_description = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        fusion.custom_model_data = PacketCodecs.INTEGER.decode(buf);
        fusion.tradable_form = PacketCodecs.BOOL.decode(buf);
        fusion.effects = Effects.CODEC.decode(buf);
        fusion.fusion_aspects = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        fusion.default_aspects = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        fusion.fusion_mon = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        fusion.fuse_with_mon = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        fusion.required_aspects_fusion_mon = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        fusion.required_aspects_fuse_with_mon = PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING).decode(buf);
        return fusion;
    }
}