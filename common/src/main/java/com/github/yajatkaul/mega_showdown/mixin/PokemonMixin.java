package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Pokemon.class, remap = false)
public class PokemonMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void pokemonInit(CallbackInfo ci) {
        Pokemon pokemon = (Pokemon) (Object) this;

        AspectUtils.revertPokemonsIfRequired(pokemon);
    }
}
