package com.cobblemon.yajatkaul.mega_showdown.datapack;

import com.cobblemon.yajatkaul.mega_showdown.config.structure.abstracts.Effects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record KeyItemData(
        String msd_id,
        String item_id,
        String item_name,
        List<String> item_description,
        List<String> pokemons,
        List<String> aspects,
        List<String> default_aspects,
        List<String> required_aspects,
        List<List<String>> toggle_aspects,
        Integer custom_model_data,
        Boolean tradable_form,
        EffectsData effects
) {
    public static final Codec<KeyItemData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(KeyItemData::msd_id),
            Codec.STRING.fieldOf("item_id").forGetter(KeyItemData::item_id),
            Codec.STRING.fieldOf("item_name").forGetter(KeyItemData::item_name),
            Codec.list(Codec.STRING).fieldOf("item_description").forGetter(KeyItemData::item_description),
            Codec.list(Codec.STRING).fieldOf("pokemons").forGetter(KeyItemData::pokemons),
            Codec.list(Codec.STRING).fieldOf("aspects").forGetter(KeyItemData::aspects),
            Codec.list(Codec.STRING).fieldOf("default_aspects").forGetter(KeyItemData::default_aspects),
            Codec.list(Codec.STRING).fieldOf("required_aspects").forGetter(KeyItemData::required_aspects),
            Codec.list(Codec.list(Codec.STRING)).fieldOf("toggle_aspects").forGetter(KeyItemData::toggle_aspects),
            Codec.INT.fieldOf("custom_model_data").forGetter(KeyItemData::custom_model_data),
            Codec.BOOL.fieldOf("tradable_form").forGetter(KeyItemData::tradable_form),
            EffectsData.CODEC.fieldOf("effects").forGetter(KeyItemData::effects)
    ).apply(instance, KeyItemData::new));
}