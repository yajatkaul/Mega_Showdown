package com.github.yajatkaul.mega_showdown.codec;

import com.github.yajatkaul.mega_showdown.gimmick.codec.AspectSetCodec;
import com.github.yajatkaul.mega_showdown.utils.Effect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public record BattleFormChange(
        String showdownFormChangeId,
        AspectSetCodec aspects,
        Effect effect
) {
    public static final Codec<BattleFormChange> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("showdown_form_change_id").forGetter(BattleFormChange::showdownFormChangeId),
            AspectSetCodec.CODEC.fieldOf("aspects").forGetter(BattleFormChange::aspects),
            Effect.EFFECT_MAP_CODEC.optionalFieldOf("effect", Effect.empty()).forGetter(BattleFormChange::effect)
    ).apply(instance, BattleFormChange::new));
}