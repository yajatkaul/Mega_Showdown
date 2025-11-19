package com.github.yajatkaul.mega_showdown.utils;

import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
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

    public static Effect empty() {
        return new Effect(Optional.empty(), Optional.empty());
    }

    public void applyEffects(Pokemon context, List<String> aspects, @Nullable PokemonEntity other) {
        if (context.getEntity() == null) {
            AspectUtils.applyAspects(context, aspects);
            return;
        }
        if (this.snowStorm().isPresent() && this.minecraft().isPresent()) {
            this.minecraft.get().apply(context.getEntity());
            this.snowStorm.get().apply(context.getEntity(), aspects, other);
        } else if (this.minecraft().isPresent()) {
            this.minecraft.get().apply(context.getEntity());
            AspectUtils.applyAspects(context, aspects);
        } else if (this.snowStorm().isPresent()) {
            this.snowStorm.get().apply(context.getEntity(), aspects, other);
        } else {
            AspectUtils.applyAspects(context, aspects);
        }
    }

    public void revertEffects(Pokemon context, List<String> aspects, @Nullable PokemonEntity other) {
        if (context.getEntity() == null) {
            AspectUtils.applyAspects(context, aspects);
            return;
        }
        if (this.snowStorm().isPresent() && this.minecraft().isPresent()) {
            this.minecraft.get().revert(context.getEntity());
            this.snowStorm.get().revert(context.getEntity(), aspects, other);
        } else if (this.minecraft().isPresent()) {
            this.minecraft.get().revert(context.getEntity());
            AspectUtils.applyAspects(context, aspects);
        } else if (this.snowStorm().isPresent()) {
            this.snowStorm.get().revert(context.getEntity(), aspects, other);
        } else {
            AspectUtils.applyAspects(context, aspects);
        }
    }

    public void applyEffectsBattle(Pokemon context, List<String> aspects, @Nullable PokemonEntity other, BattlePokemon battlePokemon) {
        if (context.getEntity() == null) {
            AspectUtils.applyAspects(context, aspects);
            AspectUtils.updatePackets(battlePokemon);
            return;
        }
        if (this.snowStorm().isPresent() && this.minecraft().isPresent()) {
            this.minecraft.get().apply(context.getEntity());
            this.snowStorm.get().applyBattle(context.getEntity(), aspects, other, battlePokemon);
        } else if (this.minecraft().isPresent()) {
            this.minecraft.get().apply(context.getEntity());
            AspectUtils.applyAspects(context, aspects);
            AspectUtils.updatePackets(battlePokemon);
        } else if (this.snowStorm().isPresent()) {
            this.snowStorm.get().applyBattle(context.getEntity(), aspects, other, battlePokemon);
        } else {
            AspectUtils.applyAspects(context, aspects);
            AspectUtils.updatePackets(battlePokemon);
        }
    }
}
