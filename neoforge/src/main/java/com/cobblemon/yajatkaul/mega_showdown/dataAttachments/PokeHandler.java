package com.cobblemon.yajatkaul.mega_showdown.dataAttachments;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class PokeHandler {
    public static final Codec<PokeHandler> CODEC = Pokemon.getCODEC().xmap(PokeHandler::new, PokeHandler::getPokemon);
    public static final StreamCodec<RegistryFriendlyByteBuf, PokeHandler> S2C_CODEC =
            Pokemon.getS2C_CODEC().map(PokeHandler::new, PokeHandler::getPokemon);
    private final Pokemon pokemon;

    public PokeHandler(Pokemon pokemon) {
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
}