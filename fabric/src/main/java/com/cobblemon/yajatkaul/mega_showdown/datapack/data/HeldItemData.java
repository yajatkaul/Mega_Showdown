package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles.EffectsData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public record HeldItemData(
        String msd_id,
        String item_id,
        String item_name,
        Boolean tradable_form,
        List<String> item_description,
        List<String> pokemons,
        List<List<String>> apply_if,
        List<String> apply_aspects,
        List<List<String>> revert_if,
        List<String> revert_aspects,
        Integer custom_model_data,
        EffectsData effects
) {
    public static final Codec<HeldItemData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(HeldItemData::msd_id),
            Codec.STRING.fieldOf("item_id").forGetter(HeldItemData::item_id),
            Codec.STRING.fieldOf("item_name").forGetter(HeldItemData::item_name),
            Codec.BOOL.optionalFieldOf("tradable_form").forGetter(h -> Optional.ofNullable(h.tradable_form())),
            Codec.list(Codec.STRING).optionalFieldOf("item_description").forGetter(h -> Optional.ofNullable(h.item_description())),
            Codec.list(Codec.STRING).fieldOf("pokemons").forGetter(HeldItemData::pokemons),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("apply_if").forGetter(h -> Optional.ofNullable(h.apply_if())),
            Codec.list(Codec.STRING).fieldOf("apply_aspects").forGetter(HeldItemData::apply_aspects),
            Codec.list(Codec.list(Codec.STRING)).fieldOf("revert_if").forGetter(HeldItemData::revert_if),
            Codec.list(Codec.STRING).fieldOf("revert_aspects").forGetter(HeldItemData::revert_aspects),
            Codec.INT.optionalFieldOf("custom_model_data").forGetter(h -> Optional.ofNullable(h.custom_model_data())),
            EffectsData.CODEC.optionalFieldOf("effects").forGetter(h -> Optional.ofNullable(h.effects()))
    ).apply(instance, (msdId, itemId, itemName, tradableForm, itemDescription, pokemons, applyIf, applyAspects, revertIf, revertAspects, customModelData, effects) ->
            new HeldItemData(
                    msdId,
                    itemId,
                    itemName,
                    tradableForm.orElse(false),
                    itemDescription.orElse(List.of()),
                    pokemons,
                    applyIf.orElse(List.of()),
                    applyAspects,
                    revertIf,
                    revertAspects,
                    customModelData.orElse(0),
                    effects.orElse(null)
            )
    ));
}