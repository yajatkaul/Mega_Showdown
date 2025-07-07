package com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record SnowStorm(
        List<String> locator_apply,
        List<String> locator_revert,
        String particle_apply,
        Float apply_after,
        String particle_revert,
        Float revert_after,
        String sound_apply,
        String sound_revert
) {
    public static final Codec<SnowStorm> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Codec.STRING).fieldOf("locator_apply").forGetter(SnowStorm::locator_apply),
            Codec.list(Codec.STRING).fieldOf("locator_revert").forGetter(SnowStorm::locator_revert),
            Codec.STRING.optionalFieldOf("particle_apply", null).forGetter(SnowStorm::particle_apply),
            Codec.FLOAT.optionalFieldOf("apply_after", 0f).forGetter(SnowStorm::apply_after),
            Codec.STRING.optionalFieldOf("particle_revert", null).forGetter(SnowStorm::particle_revert),
            Codec.FLOAT.optionalFieldOf("revert_after", 0f).forGetter(SnowStorm::revert_after),
            Codec.STRING.optionalFieldOf("sound_apply", null).forGetter(SnowStorm::sound_apply),
            Codec.STRING.optionalFieldOf("sound_revert", null).forGetter(SnowStorm::sound_revert)
            ).apply(instance, SnowStorm::new));
}