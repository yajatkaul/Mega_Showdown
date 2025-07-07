package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record GmaxData(String pokemon, String gmaxMove) {
    public static final Codec<GmaxData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("pokemon").forGetter(GmaxData::pokemon),
            Codec.STRING.fieldOf("gmaxMove").forGetter(GmaxData::gmaxMove)
    ).apply(instance, GmaxData::new));
}