package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles.EffectsData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record KeyItemData(
        String msd_id,
        String item_id,
        String item_name,
        List<String> item_description,
        Boolean consume,
        List<String> pokemons,
        List<String> apply_if,
        List<String> apply_aspects,
        List<String> revert_if,
        List<String> revert_aspects,
        List<List<String>> toggle_cycle,
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
            Codec.BOOL.fieldOf("consume").forGetter(KeyItemData::consume),
            Codec.list(Codec.STRING).fieldOf("pokemons").forGetter(KeyItemData::pokemons),
            Codec.list(Codec.STRING).optionalFieldOf("apply_if", List.of()).forGetter(KeyItemData::apply_if),
            Codec.list(Codec.STRING).fieldOf("apply_aspects").forGetter(KeyItemData::apply_aspects),
            Codec.list(Codec.STRING).optionalFieldOf("revert_if", List.of()).forGetter(KeyItemData::revert_if),
            Codec.list(Codec.STRING).fieldOf("revert_aspects").forGetter(KeyItemData::revert_aspects),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("toggle_aspects", List.of()).forGetter(KeyItemData::toggle_aspects),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("toggle_cycle", List.of()).forGetter(KeyItemData::toggle_cycle),
            Codec.INT.optionalFieldOf("custom_model_data", 0).forGetter(KeyItemData::custom_model_data),
            Codec.BOOL.fieldOf("tradable_form").forGetter(KeyItemData::tradable_form),
            EffectsData.CODEC.fieldOf("effects").forGetter(KeyItemData::effects)
    ).apply(instance, KeyItemData::new));
}