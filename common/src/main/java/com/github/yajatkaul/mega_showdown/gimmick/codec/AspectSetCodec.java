package com.github.yajatkaul.mega_showdown.gimmick.codec;

import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record AspectSetCodec(
        List<List<String>> required_aspects,
        List<List<String>> blacklist_aspects,
        List<String> apply_aspects,
        List<String> revert_aspects
) {
    public static final Codec<AspectSetCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("required_aspects", List.of()).forGetter(AspectSetCodec::required_aspects),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("blacklist_aspects", List.of()).forGetter(AspectSetCodec::blacklist_aspects),
            Codec.list(Codec.STRING).fieldOf("apply_aspects").forGetter(AspectSetCodec::apply_aspects),
            Codec.list(Codec.STRING).fieldOf("revert_aspects").forGetter(AspectSetCodec::revert_aspects)
    ).apply(instance, AspectSetCodec::new));
}
