package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles.EffectsData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public record BattleFormChange(
        List<String> pokemons,
        List<String> apply_aspects,
        List<String> revert_aspects,
        EffectsData effects,
        String showdown_form_id_apply,
        String showdown_form_id_revert
) {
    public static final Codec<BattleFormChange> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Codec.STRING).fieldOf("pokemons").forGetter(BattleFormChange::pokemons),
            Codec.list(Codec.STRING).fieldOf("apply_aspects").forGetter(BattleFormChange::apply_aspects),
            Codec.list(Codec.STRING).fieldOf("revert_aspects").forGetter(BattleFormChange::revert_aspects),
            EffectsData.CODEC.optionalFieldOf("effects").forGetter(b -> Optional.ofNullable(b.effects())),
            Codec.STRING.fieldOf("showdown_form_id_apply").forGetter(BattleFormChange::showdown_form_id_apply),
            Codec.STRING.fieldOf("showdown_form_id_revert").forGetter(BattleFormChange::showdown_form_id_revert)
    ).apply(instance, (pokemons, applyAspects, revertAspects, effects, showdownFormIdApply, showdownFormIdRevert) ->
            new BattleFormChange(
                    pokemons,
                    applyAspects,
                    revertAspects,
                    effects.orElse(null),
                    showdownFormIdApply,
                    showdownFormIdRevert
            )
    ));
}