package com.cobblemon.yajatkaul.mega_showdown.mixin.instructions;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.interpreter.instructions.StartInstruction;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.yajatkaul.mega_showdown.event.dynamax.DynamaxEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(value = StartInstruction.class, remap = false)
public class StartInstructionMixin {
    @Inject(method = "invoke", at = @At("HEAD"), remap = false)
    private void injectBeforeInvoke(PokemonBattle battle, CallbackInfo ci) {
        BattleMessage message = ((StartInstructionAccessor) this).getMessage();
        String raw = message.getRawMessage();

        String[] parts = raw.split("\\|");

        boolean containsDynamax = Arrays.stream(parts).anyMatch(part -> part.contains("Dynamax"));
        boolean containsGmax = Arrays.stream(parts).anyMatch(part -> part.contains("Gmax"));

        if (containsDynamax) {
            BattlePokemon pokemon = message.battlePokemon(0, battle);
            DynamaxEvent.EVENT.invoker().onDynamax(battle, pokemon, containsGmax);
        }
    }
}
