package com.github.yajatkaul.mega_showdown.mixin.battle;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.interpreter.instructions.EndInstruction;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.github.yajatkaul.mega_showdown.api.event.DynamaxEndCallback;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(value = EndInstruction.class, remap = false)
public class EndInstructionMixin {
    @Shadow
    @Final
    private BattleMessage message;

    @Inject(method = "invoke", at = @At("HEAD"), remap = false)
    private void injectBeforeInvoke(PokemonBattle pokemonBattle, CallbackInfo ci) {
        String raw = message.getRawMessage();

        String[] parts = raw.split("\\|");
        boolean containsDynamax = Arrays.stream(parts).anyMatch(part -> part.contains("Dynamax"));

        if (containsDynamax) {
            BattlePokemon battlePokemon = message.battlePokemon(0, pokemonBattle);
            DynamaxEndCallback.EVENT.invoker().onDynamaxEnd(pokemonBattle, battlePokemon);
        }
    }
}
