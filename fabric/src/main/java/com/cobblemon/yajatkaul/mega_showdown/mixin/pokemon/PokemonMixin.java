package com.cobblemon.yajatkaul.mega_showdown.mixin.pokemon;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.features.DynamaxLevelHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Pokemon.class, remap = false)
public class PokemonMixin {
    @Inject(method = "setDmaxLevel", at = @At("TAIL"))
    private void applyFeature(int value, CallbackInfo ci) {
        DynamaxLevelHandler.update((Pokemon) (Object) this);
    }
}
