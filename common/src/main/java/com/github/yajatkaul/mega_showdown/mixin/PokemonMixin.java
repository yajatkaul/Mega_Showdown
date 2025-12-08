package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.pokemon.Pokemon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Pokemon.class, remap = false)
public abstract class PokemonMixin {
    @Shadow
    public abstract void validateMoveset(boolean includeLegacy);

    @Inject(method = "initialize", at = @At(value = "RETURN"))
    private void initialize(CallbackInfoReturnable<Pokemon> cir) {
        Pokemon self = (Pokemon) (Object) this;

        if (!self.isClient$common()) {
            validateMoveset(true);
        }
    }
}
