package com.cobblemon.yajatkaul.mega_showdown.datamanage;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class PokemonRef {
    private final Pokemon pokemon;

    public PokemonRef(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    // Minimal equals/hashCode to satisfy NeoForge (object identity)
    @Override
    public boolean equals(Object o) {
        return this == o; // Compare by reference, not contents
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this); // Use object identity
    }

    public static final Codec<PokemonRef> CODEC = Pokemon.getCODEC().xmap(PokemonRef::new, PokemonRef::getPokemon);
    public static final StreamCodec<RegistryFriendlyByteBuf, PokemonRef> S2C_CODEC = Pokemon.getS2C_CODEC().map(PokemonRef::new, PokemonRef::getPokemon);
}