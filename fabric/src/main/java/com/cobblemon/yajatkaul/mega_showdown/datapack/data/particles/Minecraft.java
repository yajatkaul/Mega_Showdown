package com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Minecraft(String particle_apply,
                        String particle_revert,
                        String sound_apply,
                        String sound_revert,
                        Integer particle_apply_amplifier,
                        Integer particle_revert_amplifier) {
    public static final Codec<Minecraft> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("particle_apply", "").forGetter(Minecraft::particle_apply),
            Codec.STRING.optionalFieldOf("particle_revert", "").forGetter(Minecraft::particle_revert),
            Codec.STRING.optionalFieldOf("sound_apply", "").forGetter(Minecraft::sound_apply),
            Codec.STRING.optionalFieldOf("sound_revert", "").forGetter(Minecraft::sound_revert),
            Codec.INT.optionalFieldOf("particle_apply_amplifier", 1).forGetter(Minecraft::particle_apply_amplifier),
            Codec.INT.optionalFieldOf("particle_revert_amplifier", 1).forGetter(Minecraft::particle_revert_amplifier)
    ).apply(instance, Minecraft::new));
}