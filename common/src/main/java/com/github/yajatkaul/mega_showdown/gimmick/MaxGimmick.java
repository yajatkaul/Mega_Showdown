package com.github.yajatkaul.mega_showdown.gimmick;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record MaxGimmick(
        String pokemon,
        String pokemonShowdownId,
        String gmaxMove,
        List<List<String>> blacklist_aspects,
        List<List<String>> required_aspects
) {
    public static final Codec<MaxGimmick> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("pokemon").forGetter(MaxGimmick::pokemon),
            Codec.STRING.fieldOf("pokemonShowdownId").forGetter(MaxGimmick::pokemonShowdownId),
            Codec.STRING.fieldOf("gmaxMove").forGetter(MaxGimmick::gmaxMove),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("blacklist_aspects", List.of()).forGetter(MaxGimmick::blacklist_aspects),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("required_aspects", List.of()).forGetter(MaxGimmick::required_aspects)
    ).apply(instance, MaxGimmick::new));

}