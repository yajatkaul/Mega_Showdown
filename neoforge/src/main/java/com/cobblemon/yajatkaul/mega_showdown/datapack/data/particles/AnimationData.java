package com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record AnimationData(List<String> animations_apply, List<String> expressions_apply,
                            List<String> animations_revert, List<String> expressions_revert) {
    public static final Codec<AnimationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Codec.STRING).fieldOf("animations_apply").forGetter(AnimationData::animations_apply),
            Codec.list(Codec.STRING).optionalFieldOf("expressions_apply", List.of()).forGetter(AnimationData::expressions_apply),
            Codec.list(Codec.STRING).fieldOf("animations_revert").forGetter(AnimationData::animations_revert),
            Codec.list(Codec.STRING).optionalFieldOf("expressions_revert", List.of()).forGetter(AnimationData::expressions_revert)
    ).apply(instance, AnimationData::new));
}