package com.cobblemon.yajatkaul.mega_showdown.datapack.data.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record EffectsData(Minecraft minecraft, SnowStorm snowStorm) {
    public static final Codec<EffectsData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Minecraft.CODEC.fieldOf("minecraft").forGetter(EffectsData::minecraft),
            SnowStorm.CODEC.fieldOf("snowstorm").forGetter(EffectsData::snowStorm)
    ).apply(instance, EffectsData::new));
}