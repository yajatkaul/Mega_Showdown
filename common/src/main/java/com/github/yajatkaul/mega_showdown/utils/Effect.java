package com.github.yajatkaul.mega_showdown.utils;

import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.datapack.MegaShowdownDatapackRegister;
import com.github.yajatkaul.mega_showdown.utils.particles.MinecraftParticle;
import com.github.yajatkaul.mega_showdown.utils.particles.SnowStormParticle;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
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

    public static final Codec<Effect> EFFECT_MAP_CODEC = Codec.STRING.xmap(
            id -> MegaShowdownDatapackRegister.EFFECT_REGISTRY.get(ResourceLocation.parse(id)), // decode: String -> Effect
            effect -> String.valueOf(MegaShowdownDatapackRegister.EFFECT_REGISTRY.getId(effect)) // encode: Effect -> String
    ).stable();

    public void applyEffects(PokemonEntity context, List<String> aspects, @Nullable PokemonEntity other) {
        if (this.snowStorm().isPresent() && this.minecraft().isPresent()) {
            this.minecraft.get().apply(context);
            this.snowStorm.get().apply(context, aspects, other);
        } else if (this.minecraft().isPresent()) {
            this.minecraft.get().apply(context);
            AspectUtils.applyAspects(context.getPokemon(), aspects);
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
            AspectUtils.applyAspects(context.getPokemon(), aspects);
        } else if (this.snowStorm().isPresent()) {
            this.snowStorm.get().revert(context, aspects, other);
        } else {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
        }
    }

    public void applyEffectsBattle(PokemonEntity context, List<String> aspects, @Nullable PokemonEntity other, BattlePokemon battlePokemon) {
        if (this.snowStorm().isPresent() && this.minecraft().isPresent()) {
            this.minecraft.get().apply(context);
            this.snowStorm.get().applyBattle(context, aspects, other, battlePokemon);
        } else if (this.minecraft().isPresent()) {
            this.minecraft.get().apply(context);
            AspectUtils.updatePackets(battlePokemon);
        } else if (this.snowStorm().isPresent()) {
            this.snowStorm.get().applyBattle(context, aspects, other, battlePokemon);
        } else {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
            AspectUtils.updatePackets(battlePokemon);
        }
    }

    public void revertEffectsBattle(PokemonEntity context, List<String> aspects, @Nullable PokemonEntity other, BattlePokemon battlePokemon) {
        if (this.snowStorm().isPresent() && this.minecraft().isPresent()) {
            this.minecraft.get().revert(context);
            this.snowStorm.get().revertBattle(context, aspects, other, battlePokemon);
        } else if (this.minecraft().isPresent()) {
            this.minecraft.get().revert(context);
            AspectUtils.updatePackets(battlePokemon);
        } else if (this.snowStorm().isPresent()) {
            this.snowStorm.get().revertBattle(context, aspects, other, battlePokemon);
        } else {
            AspectUtils.applyAspects(context.getPokemon(), aspects);
            AspectUtils.updatePackets(battlePokemon);
        }
    }
}
