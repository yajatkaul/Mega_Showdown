package com.cobblemon.yajatkaul.mega_showdown.mixin.battle;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.ShowdownInterpreter;
import com.cobblemon.mod.common.battles.dispatch.InstructionSet;
import com.cobblemon.mod.common.battles.dispatch.InterpreterInstruction;
import com.cobblemon.yajatkaul.mega_showdown.instructions.UltraInstruction;
import kotlin.jvm.functions.Function4;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

@Mixin(value = ShowdownInterpreter.class, remap = false)
public abstract class ShowdownInterpreterMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injectBurst(CallbackInfo ci) {
        try {
            Field field = ShowdownInterpreter.class.getDeclaredField("updateInstructionParser");
            field.setAccessible(true);

            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) field.get(null);

            Object kotlinFunction = (Function4<PokemonBattle, InstructionSet, BattleMessage, Iterator<BattleMessage>, InterpreterInstruction>) (battle, set, message, messages) -> new UltraInstruction(message);

            map.put("-burst", kotlinFunction);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject -burst", e);
        }
    }
}