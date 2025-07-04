package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles.EffectsData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record HeldItemData(
        String msd_id,
        String item_id,
        String item_name,
        Boolean tradable_form,
        List<String> item_description,
        List<String> pokemons,
        List<String> apply_if,
        List<String> apply_aspects,
        List<String> revert_if,
        List<String> revert_aspects,
        List<String> blacklist_aspects,
        Integer custom_model_data,
        EffectsData effects
) {
    public static final Codec<HeldItemData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(HeldItemData::msd_id),
            Codec.STRING.fieldOf("item_id").forGetter(HeldItemData::item_id),
            Codec.STRING.fieldOf("item_name").forGetter(HeldItemData::item_name),
            Codec.BOOL.fieldOf("tradable_form").forGetter(HeldItemData::tradable_form),
            Codec.list(Codec.STRING).fieldOf("item_description").forGetter(HeldItemData::item_description),
            Codec.list(Codec.STRING).fieldOf("pokemons").forGetter(HeldItemData::pokemons),
            Codec.list(Codec.STRING).optionalFieldOf("apply_if", List.of()).forGetter(HeldItemData::apply_if),
            Codec.list(Codec.STRING).fieldOf("apply_aspects").forGetter(HeldItemData::apply_aspects),
            Codec.list(Codec.STRING).optionalFieldOf("revert_if", List.of()).forGetter(HeldItemData::revert_if),
            Codec.list(Codec.STRING).fieldOf("revert_aspects").forGetter(HeldItemData::revert_aspects),
            Codec.list(Codec.STRING).optionalFieldOf("blacklist_aspects", List.of()).forGetter(HeldItemData::blacklist_aspects),
            Codec.INT.optionalFieldOf("custom_model_data", 0).forGetter(HeldItemData::custom_model_data),
            EffectsData.CODEC.fieldOf("effects").forGetter(HeldItemData::effects)
    ).apply(instance, HeldItemData::new));
}