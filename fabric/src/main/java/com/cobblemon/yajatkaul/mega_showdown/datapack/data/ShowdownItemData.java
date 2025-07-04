package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record ShowdownItemData(
        String msd_id,
        String item_id,
        String item_name,
        Integer custom_model_data,
        List<String> item_description,
        String showdown_item_id
) {
    public static final Codec<ShowdownItemData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(ShowdownItemData::msd_id),
            Codec.STRING.fieldOf("item_id").forGetter(ShowdownItemData::item_id),
            Codec.STRING.fieldOf("item_name").forGetter(ShowdownItemData::item_name),
            Codec.INT.fieldOf("custom_model_data").forGetter(ShowdownItemData::custom_model_data),
            Codec.list(Codec.STRING).optionalFieldOf("item_description", List.of()).forGetter(ShowdownItemData::item_description),
            Codec.STRING.fieldOf("showdown_item_id").forGetter(ShowdownItemData::showdown_item_id)
    ).apply(instance, ShowdownItemData::new));
}