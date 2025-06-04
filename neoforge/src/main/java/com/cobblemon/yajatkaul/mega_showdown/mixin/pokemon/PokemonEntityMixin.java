package com.cobblemon.yajatkaul.mega_showdown.mixin.pokemon;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

        if (self.getEntityData().get(PokemonEntity.getEVOLUTION_STARTED())) {
            CompletableFuture<Pokemon> future = new CompletableFuture<>();
            future.complete(self.getPokemon());
            cir.setReturnValue(future);
        }
    }

    // ONLY FOR FURFROU
    @Inject(
            method = "tick",
            at = @At("HEAD"),
            remap = true
    )
    private void furfrouTick(CallbackInfo ci){
        Pokemon pokemon = ((PokemonEntity) (Object) this).getPokemon();

        if(pokemon.getPersistentData().getBoolean("trimmed")){
            pokemon.getPersistentData().putInt("trimmedTick", pokemon.getPersistentData().getInt("trimmedTick") + 1);
            if(pokemon.getPersistentData().getInt("trimmedTick") >= 120000){
                new StringSpeciesFeature("poodle_trim", "natural").apply(pokemon);
                pokemon.getPersistentData().putBoolean("trimmed", false);
            }
        }
    }
}