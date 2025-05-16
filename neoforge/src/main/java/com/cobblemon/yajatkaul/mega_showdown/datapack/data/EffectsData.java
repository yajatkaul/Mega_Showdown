package com.cobblemon.yajatkaul.mega_showdown.datapack.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record EffectsData(String particle_apply, String particle_revert, String sound_apply, String sound_revert,
                          Integer particle_apply_amplifier, Integer particle_revert_amplifier) {

    public static final Codec<EffectsData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("particle_apply").forGetter(e -> e.particle_apply != null ? e.sound_apply : ""),
            Codec.STRING.fieldOf("particle_revert").forGetter(e -> e.particle_revert != null ? e.particle_revert : ""),
            Codec.STRING.fieldOf("sound_apply").forGetter(e -> e.sound_apply != null ? e.sound_apply : ""),
            Codec.STRING.fieldOf("sound_revert").forGetter(e -> e.sound_revert != null ? e.sound_revert : ""),
            Codec.INT.fieldOf("particle_apply_amplifier").forGetter(e -> e.particle_apply_amplifier != null ? e.particle_apply_amplifier : 1),
            Codec.INT.fieldOf("particle_revert_amplifier").forGetter(e -> e.particle_revert_amplifier != null ? e.particle_revert_amplifier : 1)
    ).apply(instance, EffectsData::new));
}