package com.github.yajatkaul.mega_showdown.utils;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.utils.particles.MinecraftParticle;
import com.github.yajatkaul.mega_showdown.utils.particles.SnowStormParticle;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public record Effect(
        Optional<MinecraftParticle> minecraft,
        Optional<SnowStormParticle> snowStorm
) {
    public static final Codec<Effect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MinecraftParticle.CODEC.optionalFieldOf("minecraft").forGetter(Effect::minecraft),
            SnowStormParticle.CODEC.optionalFieldOf("snowstorm").forGetter(Effect::snowStorm)
    ).apply(instance, Effect::new));

    public void applyEffects(PokemonEntity context, List<String> aspects, @Nullable PokemonEntity other) {
        if (this.snowStorm().isPresent() && this.minecraft().isPresent()) {
            this.minecraft.get().apply(context);
            this.snowStorm.get().apply(context, aspects, other);
        } else if (this.minecraft().isPresent()) {
            this.minecraft.get().apply(context);
        } else if (this.snowStorm().isPresent()) {
            this.snowStorm.get().apply(context, aspects, other);
        } else {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
        }
    }

    public void revertEffects(PokemonEntity context, List<String> aspects, @Nullable PokemonEntity other) {
        if (this.snowStorm().isPresent() && this.minecraft().isPresent()) {
            this.minecraft.get().revert(context);
            this.snowStorm.get().revert(context, aspects, other);
        } else if (this.minecraft().isPresent()) {
            this.minecraft.get().revert(context);
        } else if (this.snowStorm().isPresent()) {
            this.snowStorm.get().revert(context, aspects, other);
        } else {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
        }
    }
}
