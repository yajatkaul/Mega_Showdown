package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.interpreter.instructions.StartInstruction;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.yajatkaul.mega_showdown.event.DynamaxEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;


@Mixin(value = StartInstruction.class, remap = false)
public class StartInstructionMixin  {
    @Inject(method = "invoke", at = @At("HEAD"), remap = false)
    private void injectBeforeInvoke(PokemonBattle battle, CallbackInfo ci) {
        BattleMessage message = ((StartInstructionAccessor) this).getMessage();

        List<String> logs = battle.getShowdownMessages();
        if (logs.isEmpty()) return; // Nothing to check

        String battleLog = logs.getLast(); // Get the last message

        String[] parts = battleLog.split("\\|");
        boolean containsDynamax = Arrays.stream(parts).anyMatch(part -> part.contains("Dynamax"));

        if (containsDynamax) {
            BattlePokemon pokemon =  message.battlePokemon(0, battle);
            DynamaxEvent event = new DynamaxEvent(battle, pokemon);
            NeoForge.EVENT_BUS.post(event);
        }
    }
}
