package com.github.yajatkaul.mega_showdown.mixin.battle;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.interpreter.instructions.StartInstruction;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.github.yajatkaul.mega_showdown.api.event.DynamaxStartCallback;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(value = StartInstruction.class, remap = false)
public class StartInstructionMixin {
    @Shadow
    @Final
    private BattleMessage message;

    @Inject(method = "invoke", at = @At("HEAD"), remap = false)
    private void injectBeforeInvoke(PokemonBattle pokemonBattle, CallbackInfo ci) {
        String raw = message.getRawMessage();

        String[] parts = raw.split("\\|");

        boolean containsDynamax = Arrays.stream(parts).anyMatch(part -> part.contains("Dynamax"));
        boolean containsGmax = Arrays.stream(parts).anyMatch(part -> part.contains("Gmax"));

        if (containsDynamax) {
            BattlePokemon battlePokemon = message.battlePokemon(0, pokemonBattle);
            DynamaxStartCallback.EVENT.invoker().onDynamaxStart(pokemonBattle, battlePokemon, containsGmax);
        }
    }
}
