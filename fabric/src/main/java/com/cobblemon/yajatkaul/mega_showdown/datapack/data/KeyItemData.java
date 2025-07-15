package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles.EffectsData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public record KeyItemData(
        String msd_id,
        String item_id,
        String item_name,
        List<String> item_description,
        Integer consume,
        List<String> pokemons,
        List<List<String>> blacklist_aspects,
        List<List<String>> apply_if,
        List<String> apply_aspects,
        List<List<String>> revert_if,
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
            Codec.list(Codec.STRING).optionalFieldOf("item_description").forGetter(k -> Optional.ofNullable(k.item_description())),
            Codec.INT.optionalFieldOf("consume").forGetter(k -> Optional.ofNullable(k.consume())),
            Codec.list(Codec.STRING).fieldOf("pokemons").forGetter(KeyItemData::pokemons),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("blacklist_aspects", List.of()).forGetter(KeyItemData::blacklist_aspects),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("apply_if").forGetter(k -> Optional.ofNullable(k.apply_if())),
            Codec.list(Codec.STRING).fieldOf("apply_aspects").forGetter(KeyItemData::apply_aspects),
            Codec.list(Codec.list(Codec.STRING)).fieldOf("revert_if").forGetter(KeyItemData::revert_if),
            Codec.list(Codec.STRING).fieldOf("revert_aspects").forGetter(KeyItemData::revert_aspects),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("toggle_cycle").forGetter(k -> Optional.ofNullable(k.toggle_cycle())),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("toggle_aspects").forGetter(k -> Optional.ofNullable(k.toggle_aspects())),
            Codec.INT.optionalFieldOf("custom_model_data").forGetter(k -> Optional.ofNullable(k.custom_model_data())),
            Codec.BOOL.optionalFieldOf("tradable_form").forGetter(k -> Optional.ofNullable(k.tradable_form())),
            EffectsData.CODEC.optionalFieldOf("effects").forGetter(k -> Optional.ofNullable(k.effects()))
    ).apply(instance, (msdId, itemId, itemName, itemDescription, consume, pokemons, blacklistAspects, applyIf, applyAspects, revertIf, revertAspects, toggleCycle, toggleAspects, customModelData, tradableForm, effects) ->
            new KeyItemData(
                    msdId,
                    itemId,
                    itemName,
                    itemDescription.orElse(List.of()),
                    consume.orElse(0),
                    pokemons,
                    blacklistAspects,
                    applyIf.orElse(List.of()),
                    applyAspects,
                    revertIf,
                    revertAspects,
                    toggleCycle.orElse(List.of()),
                    toggleAspects.orElse(List.of()),
                    customModelData.orElse(0),
                    tradableForm.orElse(false),
                    effects.orElse(null)
            )
    ));
}