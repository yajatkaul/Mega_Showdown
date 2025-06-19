package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record MegaData(
        String msd_id,
        String showdown_id,
        String item_id,
        String item_name,
        String pokemon,
        List<String> required_aspects,
        List<String> item_description,
        List<String> aspects,
        Integer custom_model_data
) {
    public static final Codec<MegaData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(MegaData::msd_id),
            Codec.STRING.fieldOf("showdown_id").forGetter(MegaData::showdown_id),
            Codec.STRING.fieldOf("item_id").forGetter(MegaData::item_id),
            Codec.STRING.fieldOf("item_name").forGetter(MegaData::item_name),
            Codec.STRING.fieldOf("pokemon").forGetter(MegaData::pokemon),
            Codec.list(Codec.STRING).fieldOf("required_aspects").forGetter(MegaData::item_description),
            Codec.list(Codec.STRING).fieldOf("item_description").forGetter(MegaData::item_description),
            Codec.list(Codec.STRING).fieldOf("aspects").forGetter(MegaData::aspects),
            Codec.INT.fieldOf("custom_model_data").forGetter(MegaData::custom_model_data)
    ).apply(instance, MegaData::new));
}
