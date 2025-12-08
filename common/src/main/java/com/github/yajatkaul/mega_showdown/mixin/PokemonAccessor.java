package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.pokemon.Pokemon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Pokemon.class, remap = false)
public interface PokemonAccessor {
    @Invoker("setFriendship")
    void invokeSetFriendship(int value);
}