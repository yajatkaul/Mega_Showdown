package com.cobblemon.yajatkaul.mega_showdown.datapack.data.heldItem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record BattleModeOnly (String showdown_item_id,
                              List<String> apply_aspects,
                              List<String> revert_aspects,
                              String showdown_form_id,
                              List<String> required_aspects){
    public static final Codec<BattleModeOnly> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("showdown_item_id", "").forGetter(BattleModeOnly::showdown_item_id),
            Codec.list(Codec.STRING).fieldOf("apply_aspects").forGetter(BattleModeOnly::apply_aspects),
            Codec.list(Codec.STRING).fieldOf("revert_aspects").forGetter(BattleModeOnly::revert_aspects),
            Codec.STRING.fieldOf("showdown_form_id").forGetter(BattleModeOnly::showdown_form_id),
            Codec.list(Codec.STRING).fieldOf("required_aspects").forGetter(BattleModeOnly::required_aspects)
    ).apply(instance, BattleModeOnly::new));
}
