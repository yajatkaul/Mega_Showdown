package com.github.yajatkaul.mega_showdown.gimmick;

import com.github.yajatkaul.mega_showdown.gimmick.codec.AspectSetCodec;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record MaxGimmick(
        String pokemonShowdownId,
        String gmaxMove,
        AspectSetCodec aspectSetCodec
) {
    public static final Codec<MaxGimmick> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("pokemonShowdownId").forGetter(MaxGimmick::pokemonShowdownId),
            Codec.STRING.fieldOf("gmaxMove").forGetter(MaxGimmick::gmaxMove),
            AspectSetCodec.CODEC.fieldOf("aspect_conditions").forGetter(MaxGimmick::aspectSetCodec)
    ).apply(instance, MaxGimmick::new));
}