package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public record GmaxData(String pokemon, String pokemonShowdownId,String gmaxMove, List<List<String>> blacklist_aspects, List<List<String>> required_aspects) {
    public static final Codec<GmaxData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("pokemon").forGetter(GmaxData::pokemon),
            Codec.STRING.fieldOf("pokemonShowdownId").forGetter(GmaxData::pokemonShowdownId),
            Codec.STRING.fieldOf("gmaxMove").forGetter(GmaxData::gmaxMove),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("blacklist_aspects", List.of()).forGetter(GmaxData::blacklist_aspects),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("required_aspects", List.of()).forGetter(GmaxData::required_aspects)
    ).apply(instance, GmaxData::new));
}