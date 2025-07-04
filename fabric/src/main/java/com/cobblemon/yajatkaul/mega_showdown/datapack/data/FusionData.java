package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles.EffectsData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record FusionData(
        String msd_id,
        String item_id,
        String item_name,
        List<String> item_description,
        boolean tradable_form,
        Integer custom_model_data,
        EffectsData effects,
        List<String> fuse_if,
        List<String> fusion_aspects,
        List<String> revert_if,
        List<String> revert_aspects,
        List<String> fusion_mons,
        List<String> fuser_fuse_if,
        List<String> fuser_mons
) {
    public static final Codec<FusionData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(FusionData::msd_id),
            Codec.STRING.fieldOf("item_id").forGetter(FusionData::item_id),
            Codec.STRING.fieldOf("item_name").forGetter(FusionData::item_name),
            Codec.list(Codec.STRING).fieldOf("item_description").forGetter(FusionData::item_description),
            Codec.BOOL.fieldOf("tradable_form").forGetter(FusionData::tradable_form),
            Codec.INT.optionalFieldOf("custom_model_data", 0).forGetter(FusionData::custom_model_data),
            EffectsData.CODEC.optionalFieldOf("effects", null).forGetter(FusionData::effects),
            Codec.list(Codec.STRING).optionalFieldOf("fuse_if", List.of()).forGetter(FusionData::fuse_if),
            Codec.list(Codec.STRING).fieldOf("fusion_aspects").forGetter(FusionData::fusion_aspects),
            Codec.list(Codec.STRING).fieldOf("revert_if").forGetter(FusionData::revert_if),
            Codec.list(Codec.STRING).fieldOf("revert_aspects").forGetter(FusionData::revert_aspects),
            Codec.list(Codec.STRING).fieldOf("fusion_mons").forGetter(FusionData::fusion_mons),
            Codec.list(Codec.STRING).optionalFieldOf("fuser_fuse_if", List.of()).forGetter(FusionData::fuser_fuse_if),
            Codec.list(Codec.STRING).fieldOf("fuser_mons").forGetter(FusionData::fuser_mons)
    ).apply(instance, FusionData::new));
}
