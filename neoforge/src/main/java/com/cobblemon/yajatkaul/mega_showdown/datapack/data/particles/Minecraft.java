package com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public record Minecraft(String particle_apply,
                        String particle_revert,
                        String sound_apply,
                        String sound_revert,
                        Integer particle_apply_amplifier,
                        Integer particle_revert_amplifier) {
    public static final Codec<Minecraft> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("particle_apply").forGetter(m -> Optional.ofNullable(m.particle_apply())),
            Codec.STRING.optionalFieldOf("particle_revert").forGetter(m -> Optional.ofNullable(m.particle_revert())),
            Codec.STRING.optionalFieldOf("sound_apply").forGetter(m -> Optional.ofNullable(m.sound_apply())),
            Codec.STRING.optionalFieldOf("sound_revert").forGetter(m -> Optional.ofNullable(m.sound_revert())),
            Codec.INT.optionalFieldOf("particle_apply_amplifier").forGetter(m -> Optional.ofNullable(m.particle_apply_amplifier())),
            Codec.INT.optionalFieldOf("particle_revert_amplifier").forGetter(m -> Optional.ofNullable(m.particle_revert_amplifier()))
    ).apply(instance, (particleApply, particleRevert, soundApply, soundRevert, particleApplyAmplifier, particleRevertAmplifier) ->
            new Minecraft(
                    particleApply.orElse(null),
                    particleRevert.orElse(null),
                    soundApply.orElse(null),
                    soundRevert.orElse(null),
                    particleApplyAmplifier.orElse(null),
                    particleRevertAmplifier.orElse(null)
            )
    ));
}