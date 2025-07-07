package com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;

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
            Codec.STRING.optionalFieldOf("particle_apply").forGetter(s -> Optional.ofNullable(s.particle_apply())),
            Codec.FLOAT.optionalFieldOf("apply_after").forGetter(s -> Optional.ofNullable(s.apply_after())),
            Codec.STRING.optionalFieldOf("particle_revert").forGetter(s -> Optional.ofNullable(s.particle_revert())),
            Codec.FLOAT.optionalFieldOf("revert_after").forGetter(s -> Optional.ofNullable(s.revert_after())),
            Codec.STRING.optionalFieldOf("sound_apply").forGetter(s -> Optional.ofNullable(s.sound_apply())),
            Codec.STRING.optionalFieldOf("sound_revert").forGetter(s -> Optional.ofNullable(s.sound_revert()))
    ).apply(instance, (locatorApply, locatorRevert, particleApply, applyAfter, particleRevert, revertAfter, soundApply, soundRevert) ->
            new SnowStorm(
                    locatorApply,
                    locatorRevert,
                    particleApply.orElse(null),
                    applyAfter.orElse(null),
                    particleRevert.orElse(null),
                    revertAfter.orElse(null),
                    soundApply.orElse(null),
                    soundRevert.orElse(null)
            )
    ));
}