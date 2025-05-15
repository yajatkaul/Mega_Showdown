package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record HeldItemData (
        String msd_id,
        String showdown_id,
        String item_id,
        String item_name,
        List<String> item_description,
        Integer custom_model_data
) {
    public static final Codec<HeldItemData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(HeldItemData::msd_id),
            Codec.STRING.fieldOf("showdown_id").forGetter(HeldItemData::showdown_id),
            Codec.STRING.fieldOf("item_id").forGetter(HeldItemData::item_id),
            Codec.STRING.fieldOf("item_name").forGetter(HeldItemData::item_name),
            Codec.list(Codec.STRING).fieldOf("item_description").forGetter(HeldItemData::item_description),
            Codec.INT.fieldOf("custom_model_data").forGetter(HeldItemData::custom_model_data)
    ).apply(instance, HeldItemData::new));
}