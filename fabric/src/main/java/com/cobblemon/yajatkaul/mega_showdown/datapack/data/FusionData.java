package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record FusionData(
        String msd_id,
        String item_id,
        String item_name,
        List<String> item_description,
        boolean tradable_form,
        int custom_model_data,
        EffectsData effects,
        List<String> fusion_aspects,
        List<String> default_aspects,
        List<String> fusion_mon,
        List<String> required_aspects_fusion_mon,
        List<String> fuse_with_mon,
        List<String> required_aspects_fuse_with_mon
) {
    public static final Codec<FusionData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(FusionData::msd_id),
            Codec.STRING.fieldOf("item_id").forGetter(FusionData::item_id),
            Codec.STRING.fieldOf("item_name").forGetter(FusionData::item_name),
            Codec.list(Codec.STRING).fieldOf("item_description").forGetter(FusionData::item_description),
            Codec.BOOL.fieldOf("tradable_form").forGetter(FusionData::tradable_form),
            Codec.INT.fieldOf("custom_model_data").forGetter(FusionData::custom_model_data),
            EffectsData.CODEC.fieldOf("effects").forGetter(FusionData::effects),
            Codec.list(Codec.STRING).fieldOf("fusion_aspects").forGetter(FusionData::fusion_aspects),
            Codec.list(Codec.STRING).fieldOf("default_aspects").forGetter(FusionData::default_aspects),
            Codec.list(Codec.STRING).fieldOf("fusion_mon").forGetter(FusionData::fusion_mon),
            Codec.list(Codec.STRING).fieldOf("required_aspects_fusion_mon").forGetter(FusionData::required_aspects_fusion_mon),
            Codec.list(Codec.STRING).fieldOf("fuse_with_mon").forGetter(FusionData::fuse_with_mon),
            Codec.list(Codec.STRING).fieldOf("required_aspects_fuse_with_mon").forGetter(FusionData::required_aspects_fuse_with_mon)
    ).apply(instance, FusionData::new));
}
