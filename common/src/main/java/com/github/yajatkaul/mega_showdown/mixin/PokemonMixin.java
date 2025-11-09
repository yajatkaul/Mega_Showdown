package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = Pokemon.class, remap = false)
public class PokemonMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void pokemonInit(CallbackInfo ci) {
        Pokemon pokemon = (Pokemon) (Object) this;

        if (pokemon.getPersistentData().contains("apply_aspects")) {
            List<String> aspects =
                    AspectUtils.decodeNbt(pokemon.getPersistentData().getList("apply_aspects", 8));

            AspectUtils.applyAspects(pokemon, aspects);
        }
    }
}
