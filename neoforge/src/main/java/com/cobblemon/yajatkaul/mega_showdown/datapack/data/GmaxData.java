package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record GmaxData(String pokemon) {
    public static final Codec<GmaxData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("pokemon").forGetter(GmaxData::pokemon)
    ).apply(instance, GmaxData::new));
}