package com.github.yajatkaul.mega_showdown.gimmick.codec;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record AspectSetCodec(
        List<List<String>> required_aspects_apply,
        List<List<String>> blacklist_aspects_apply,
        List<List<String>> required_aspects_revert,
        List<List<String>> blacklist_aspects_revert,
        List<String> apply_aspects,
        List<String> revert_aspects
) {
    public static final Codec<AspectSetCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("required_aspects_apply", List.of()).forGetter(AspectSetCodec::required_aspects_apply),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("blacklist_aspects_apply", List.of()).forGetter(AspectSetCodec::blacklist_aspects_apply),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("required_aspects_revert", List.of()).forGetter(AspectSetCodec::required_aspects_revert),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("blacklist_aspects_revert", List.of()).forGetter(AspectSetCodec::blacklist_aspects_revert),
            Codec.list(Codec.STRING).optionalFieldOf("apply_aspects", List.of()).forGetter(AspectSetCodec::apply_aspects),
            Codec.list(Codec.STRING).optionalFieldOf("revert_aspects", List.of()).forGetter(AspectSetCodec::revert_aspects)
    ).apply(instance, AspectSetCodec::new));

    public boolean validate_apply(Pokemon pokemon) {
        if (this.blacklist_aspects_apply
                .stream()
                .flatMap(List::stream)
                .anyMatch(pokemon.getAspects()::contains)) {
            return false;
        }

        return this.required_aspects_apply
                .stream()
                .flatMap(List::stream)
                .allMatch(pokemon.getAspects()::contains);
    }

    public boolean validate_revert(Pokemon pokemon) {
        if (this.blacklist_aspects_revert
                .stream()
                .flatMap(List::stream)
                .anyMatch(pokemon.getAspects()::contains)) {
            return false;
        }

        return this.required_aspects_revert
                .stream()
                .flatMap(List::stream)
                .allMatch(pokemon.getAspects()::contains);
    }
}
