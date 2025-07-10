package com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public record SnowStorm(
        List<String> source_apply,
        List<String> target_apply,
        List<String> source_revert,
        List<String> target_revert,
        String particle_apply,
        Float apply_after,
        String particle_revert,
        Float revert_after,
        String sound_apply,
        String sound_revert,
        AnimationData animations
) {
    public static final Codec<SnowStorm> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Codec.STRING).optionalFieldOf("source_apply").forGetter(s -> Optional.ofNullable(s.source_apply())),
            Codec.list(Codec.STRING).optionalFieldOf("target_apply").forGetter(s -> Optional.ofNullable(s.target_apply())),
            Codec.list(Codec.STRING).optionalFieldOf("source_revert").forGetter(s -> Optional.ofNullable(s.source_revert())),
            Codec.list(Codec.STRING).optionalFieldOf("target_revert").forGetter(s -> Optional.ofNullable(s.target_revert())),
            Codec.STRING.optionalFieldOf("particle_apply").forGetter(s -> Optional.ofNullable(s.particle_apply())),
            Codec.FLOAT.optionalFieldOf("apply_after").forGetter(s -> Optional.ofNullable(s.apply_after())),
            Codec.STRING.optionalFieldOf("particle_revert").forGetter(s -> Optional.ofNullable(s.particle_revert())),
            Codec.FLOAT.optionalFieldOf("revert_after").forGetter(s -> Optional.ofNullable(s.revert_after())),
            Codec.STRING.optionalFieldOf("sound_apply").forGetter(s -> Optional.ofNullable(s.sound_apply())),
            Codec.STRING.optionalFieldOf("sound_revert").forGetter(s -> Optional.ofNullable(s.sound_revert())),
            AnimationData.CODEC.optionalFieldOf("animations").forGetter(e -> Optional.ofNullable(e.animations()))
    ).apply(instance, (sourceApply, targetApply, sourceRevert, targetRevert, particleApply, applyAfter, particleRevert, revertAfter, soundApply, soundRevert, animations) ->
            new SnowStorm(
                    sourceApply.orElse(null),
                    targetApply.orElse(null),
                    sourceRevert.orElse(null),
                    targetRevert.orElse(null),
                    particleApply.orElse(null),
                    applyAfter.orElse(null),
                    particleRevert.orElse(null),
                    revertAfter.orElse(null),
                    soundApply.orElse(null),
                    soundRevert.orElse(null),
                    animations.orElse(null)
            )
    ));
}