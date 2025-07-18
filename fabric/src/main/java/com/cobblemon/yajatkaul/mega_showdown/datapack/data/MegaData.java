package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public record MegaData(
        String msd_id,
        String showdown_id,
        String item_id,
        String item_name,
        String pokemon,
        List<List<String>> required_aspects,
        List<List<String>> blacklist_aspects,
        List<String> item_description,
        String apply_aspect,
        Integer custom_model_data
) {
    public static final Codec<MegaData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(MegaData::msd_id),
            Codec.STRING.fieldOf("showdown_id").forGetter(MegaData::showdown_id),
            Codec.STRING.fieldOf("item_id").forGetter(MegaData::item_id),
            Codec.STRING.fieldOf("item_name").forGetter(MegaData::item_name),
            Codec.STRING.fieldOf("pokemon").forGetter(MegaData::pokemon),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("required_aspects").forGetter(m -> Optional.ofNullable(m.required_aspects())),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("blacklist_aspects").forGetter(m -> Optional.ofNullable(m.blacklist_aspects())),
            Codec.list(Codec.STRING).optionalFieldOf("item_description").forGetter(m -> Optional.ofNullable(m.item_description())),
            Codec.STRING.fieldOf("apply_aspects").forGetter(MegaData::apply_aspect),
            Codec.INT.optionalFieldOf("custom_model_data").forGetter(m -> Optional.ofNullable(m.custom_model_data()))
    ).apply(instance, (msdId, showdownId, itemId, itemName, pokemon, requiredAspects, blacklistAspects, itemDescription, applyAspect, customModelData) ->
            new MegaData(
                    msdId,
                    showdownId,
                    itemId,
                    itemName,
                    pokemon,
                    requiredAspects.orElse(List.of()),
                    blacklistAspects.orElse(List.of()),
                    itemDescription.orElse(List.of()),
                    applyAspect,
                    customModelData.orElse(0)
            )
    ));
}