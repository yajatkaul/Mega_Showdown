package com.github.yajatkaul.mega_showdown.codec;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.gimmick.codec.AspectSetCodec;
import com.github.yajatkaul.mega_showdown.utils.Effect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record HeldItemFormChange(
        List<String> pokemons,
        AspectSetCodec aspect_conditions,
        Effect effect,
        boolean tradable
) {
    public static final Codec<HeldItemFormChange> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.listOf().fieldOf("pokemons").forGetter(HeldItemFormChange::pokemons),
            AspectSetCodec.CODEC.fieldOf("aspect_conditions").forGetter(HeldItemFormChange::aspect_conditions),
            Effect.EFFECT_MAP_CODEC.fieldOf("effect").forGetter(HeldItemFormChange::effect),
            Codec.BOOL.fieldOf("tradable").forGetter(HeldItemFormChange::tradable)
    ).apply(instance, HeldItemFormChange::new));

    public void apply(Pokemon pokemon) {
        if (pokemons.contains(pokemon.getSpecies().getName()) && aspect_conditions().validate_apply(pokemon)) {
            effect.applyEffects(pokemon.getEntity(), aspect_conditions.apply_aspects(), null);
            if (!tradable) {
                pokemon.setTradeable(false);
            }
        }
    }

    public void revert(Pokemon pokemon) {
        if (pokemons.contains(pokemon.getSpecies().getName()) && aspect_conditions().validate_revert(pokemon)) {
            effect.revertEffects(pokemon.getEntity(), aspect_conditions.revert_aspects(), null);
            if (!tradable) {
                pokemon.setTradeable(true);
            }
        }
    }
}
