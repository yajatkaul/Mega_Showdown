package com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public record EffectsData(Minecraft minecraft, SnowStorm snowStorm) {
    public static final Codec<EffectsData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Minecraft.CODEC.optionalFieldOf("minecraft").forGetter(e -> Optional.ofNullable(e.minecraft())),
            SnowStorm.CODEC.optionalFieldOf("snowstorm").forGetter(e -> Optional.ofNullable(e.snowStorm()))
    ).apply(instance, (minecraft, snowStorm) ->
            new EffectsData(
                    minecraft.orElse(null),
                    snowStorm.orElse(null)
            )
    ));
}