package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles.EffectsData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public record FusionData(
        String msd_id,
        String item_id,
        String item_name,
        List<String> item_description,
        Boolean tradable_form,
        Integer custom_model_data,
        EffectsData effects,
        List<List<String>> fusion_blacklist_aspects,
        List<List<String>> fuse_if,
        List<String> fusion_aspects,
        List<List<String>> revert_if,
        List<String> revert_aspects,
        List<String> fusion_mons,
        List<List<String>> fuser_blacklist_aspects,
        List<List<String>> fuser_fuse_if,
        List<String> fuser_mons
) {
    public static final Codec<FusionData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(FusionData::msd_id),
            Codec.STRING.fieldOf("item_id").forGetter(FusionData::item_id),
            Codec.STRING.fieldOf("item_name").forGetter(FusionData::item_name),
            Codec.list(Codec.STRING).optionalFieldOf("item_description").forGetter(f -> Optional.ofNullable(f.item_description())),
            Codec.BOOL.optionalFieldOf("tradable_form").forGetter(f -> Optional.ofNullable(f.tradable_form())),
            Codec.INT.optionalFieldOf("custom_model_data").forGetter(f -> Optional.ofNullable(f.custom_model_data())),
            EffectsData.CODEC.optionalFieldOf("effects").forGetter(f -> Optional.ofNullable(f.effects())),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("fusion_blacklist_aspects", List.of()).forGetter(FusionData::fusion_blacklist_aspects),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("fuse_if").forGetter(f -> Optional.ofNullable(f.fuse_if())),
            Codec.list(Codec.STRING).fieldOf("fusion_aspects").forGetter(FusionData::fusion_aspects),
            Codec.list(Codec.list(Codec.STRING)).fieldOf("revert_if").forGetter(FusionData::revert_if),
            Codec.list(Codec.STRING).fieldOf("revert_aspects").forGetter(FusionData::revert_aspects),
            Codec.list(Codec.STRING).fieldOf("fusion_mons").forGetter(FusionData::fusion_mons),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("fuser_blacklist_aspects", List.of()).forGetter(FusionData::fuser_blacklist_aspects),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("fuser_fuse_if").forGetter(f -> Optional.ofNullable(f.fuser_fuse_if())),
            Codec.list(Codec.STRING).fieldOf("fuser_mons").forGetter(FusionData::fuser_mons)
    ).apply(instance, (msdId, itemId, itemName, itemDescription, tradableForm, customModelData, effects, fusionBlackListAspects, fuseIf, fusionAspects, revertIf, revertAspects, fusionMons, fuserBlackListAspects, fuserFuseIf, fuserMons) ->
            new FusionData(
                    msdId,
                    itemId,
                    itemName,
                    itemDescription.orElse(List.of()),
                    tradableForm.orElse(false),
                    customModelData.orElse(0),
                    effects.orElse(null),
                    fusionBlackListAspects,
                    fuseIf.orElse(List.of()),
                    fusionAspects,
                    revertIf,
                    revertAspects,
                    fusionMons,
                    fuserBlackListAspects,
                    fuserFuseIf.orElse(List.of()),
                    fuserMons
            )
    ));
}