package com.github.yajatkaul.mega_showdown.components;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;

public record PokemonStorge(
        CompoundTag tag
) {
    public static final Codec<PokemonStorge> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CompoundTag.CODEC.fieldOf("tag").forGetter(PokemonStorge::tag)
    ).apply(instance, PokemonStorge::new));

    public static PokemonStorge defaultStorage() {
        return new PokemonStorge(new CompoundTag());
    }

    public Pokemon getPokemon(RegistryAccess registryAccess) {
        if (tag.isEmpty()) {
            return null;
        }
        return new Pokemon().loadFromNBT(registryAccess, tag);
    }

    public PokemonStorge save(RegistryAccess registryAccess, Pokemon pokemon) {
        CompoundTag storgeTag = pokemon.saveToNBT(registryAccess, new CompoundTag());
        return new PokemonStorge(storgeTag);
    }
}
