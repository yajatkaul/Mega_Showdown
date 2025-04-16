package com.cobblemon.yajatkaul.mega_showdown.datamanage;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

public class PokemonRef {
    private final Pokemon pokemon;

    public PokemonRef(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    public static final Codec<PokemonRef> CODEC =
            Pokemon.getCODEC().xmap(PokemonRef::new, PokemonRef::getPokemon);

    public static final PacketCodec<RegistryByteBuf, PokemonRef> S2C_CODEC =
            Pokemon.getS2C_CODEC().xmap(PokemonRef::new, PokemonRef::getPokemon);
}