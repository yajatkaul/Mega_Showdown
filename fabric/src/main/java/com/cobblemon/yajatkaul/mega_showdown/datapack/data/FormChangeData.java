package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record FormChangeData (
        String msd_id,
        String showdown_id,
        String item_id,
        String item_name,
        String form_name,
        Boolean battle_mode_only,
        Boolean tradable_form,
        List<String> item_description,
        List<String> pokemons,
        List<String> aspects,
        List<String> default_aspects,
        List<String> required_aspects,
        Integer custom_model_data,
        EffectsData effects
) {
    public static final Codec<FormChangeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(FormChangeData::msd_id),
            Codec.STRING.fieldOf("showdown_id").forGetter(FormChangeData::showdown_id),
            Codec.STRING.fieldOf("item_id").forGetter(FormChangeData::item_id),
            Codec.STRING.fieldOf("item_name").forGetter(FormChangeData::item_name),
            Codec.STRING.fieldOf("form_name").forGetter(FormChangeData::form_name),
            Codec.BOOL.fieldOf("battle_mode_only").forGetter(FormChangeData::battle_mode_only),
            Codec.BOOL.fieldOf("tradable_form").forGetter(FormChangeData::tradable_form),
            Codec.list(Codec.STRING).fieldOf("item_description").forGetter(FormChangeData::item_description),
            Codec.list(Codec.STRING).fieldOf("pokemons").forGetter(FormChangeData::pokemons),
            Codec.list(Codec.STRING).fieldOf("aspects").forGetter(FormChangeData::aspects),
            Codec.list(Codec.STRING).fieldOf("default_aspects").forGetter(FormChangeData::default_aspects),
            Codec.list(Codec.STRING).fieldOf("required_aspects").forGetter(FormChangeData::required_aspects),
            Codec.INT.fieldOf("custom_model_data").forGetter(FormChangeData::custom_model_data),
            EffectsData.CODEC.fieldOf("effects").forGetter(FormChangeData::effects)
    ).apply(instance, FormChangeData::new));
}