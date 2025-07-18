package com.cobblemon.yajatkaul.mega_showdown.mixin.pokemon;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag;
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
    private void furfrouTick(CallbackInfo ci) {
        Pokemon pokemon = ((PokemonEntity) (Object) this).getPokemon();
        CompoundTag persistentData = pokemon.getPersistentData();

        if (persistentData.getBoolean("trimmed")) {
            persistentData.putInt("trimmedTick", persistentData.getInt("trimmedTick") + 1);
            if (persistentData.getInt("trimmedTick") >= 120000) {
                persistentData.putInt("trimmedTick", 0);
                new StringSpeciesFeature("poodle_trim", "natural").apply(pokemon);
                persistentData.putBoolean("trimmed", false);
            }
        }
    }
}