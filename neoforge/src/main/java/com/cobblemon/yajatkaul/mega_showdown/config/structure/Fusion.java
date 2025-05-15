package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import com.cobblemon.yajatkaul.mega_showdown.config.structure.abstracts.Effects;
import net.minecraft.network.FriendlyByteBuf;
import java.util.List;

public class Fusion {
    public String msd_id;
    public String item_id;
    public String item_name;
    public List<String> item_description;
    public boolean tradable_form;
    public int custom_model_data;
    public Effects effects;
    public List<String> fusion_aspects;
    public List<String> default_aspects;
    public List<String> fusion_mon;
    public List<String> required_aspects_fusion_mon;
    public List<String> fuse_with_mon;
    public List<String> required_aspects_fuse_with_mon;

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(msd_id);
        buf.writeUtf(item_id);
        buf.writeUtf(item_name);
        buf.writeCollection(item_description, FriendlyByteBuf::writeUtf);
        buf.writeInt(custom_model_data);
        buf.writeBoolean(tradable_form);
        effects.write(buf); // You need to implement this in Effects
        buf.writeCollection(fusion_aspects, FriendlyByteBuf::writeUtf);
        buf.writeCollection(default_aspects, FriendlyByteBuf::writeUtf);
        buf.writeCollection(fusion_mon, FriendlyByteBuf::writeUtf);
        buf.writeCollection(required_aspects_fusion_mon, FriendlyByteBuf::writeUtf);
        buf.writeCollection(fuse_with_mon, FriendlyByteBuf::writeUtf);
        buf.writeCollection(required_aspects_fuse_with_mon, FriendlyByteBuf::writeUtf);
    }

    public static Fusion read(FriendlyByteBuf buf) {
        Fusion fusion = new Fusion();
        fusion.msd_id = buf.readUtf();
        fusion.item_id = buf.readUtf();
        fusion.item_name = buf.readUtf();
        fusion.item_description = buf.readList(FriendlyByteBuf::readUtf);
        fusion.custom_model_data = buf.readInt();
        fusion.tradable_form = buf.readBoolean();
        fusion.effects = Effects.read(buf); // You need to implement this in Effects
        fusion.fusion_aspects = buf.readList(FriendlyByteBuf::readUtf);
        fusion.default_aspects = buf.readList(FriendlyByteBuf::readUtf);
        fusion.fusion_mon = buf.readList(FriendlyByteBuf::readUtf);
        fusion.required_aspects_fusion_mon = buf.readList(FriendlyByteBuf::readUtf);
        fusion.fuse_with_mon = buf.readList(FriendlyByteBuf::readUtf);
        fusion.required_aspects_fuse_with_mon = buf.readList(FriendlyByteBuf::readUtf);
        return fusion;
    }
}
