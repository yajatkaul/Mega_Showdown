package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(value = PokemonEntity.class, remap = false)
public abstract class PokemonEntityMixin {
    @Inject(
            method = "recallWithAnimation",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cancelRecallDuringEvolution(CallbackInfoReturnable<CompletableFuture<Pokemon>> cir) {
        PokemonEntity self = (PokemonEntity) (Object) this;

        boolean form_changing = self.getPokemon().getPersistentData().getBoolean("form_changing");
        if (form_changing) {
            CompletableFuture<Pokemon> future = new CompletableFuture<>();
            future.complete(self.getPokemon());
            cir.setReturnValue(future);
        }
    }
}