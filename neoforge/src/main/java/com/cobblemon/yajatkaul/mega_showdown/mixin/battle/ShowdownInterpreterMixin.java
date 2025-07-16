package com.cobblemon.yajatkaul.mega_showdown.mixin.battle;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.battles.ShowdownInterpreter;
import com.cobblemon.mod.common.battles.dispatch.InstructionSet;
import com.cobblemon.mod.common.battles.dispatch.InterpreterInstruction;
import com.cobblemon.mod.common.battles.interpreter.instructions.IgnoredInstruction;
import com.cobblemon.yajatkaul.mega_showdown.instructions.CanDynamaxInstruction;
import com.cobblemon.yajatkaul.mega_showdown.instructions.UltraInstruction;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function6;
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
            Field splitField = ShowdownInterpreter.class.getDeclaredField("splitInstructionParser");
            splitField.setAccessible(true);

            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) field.get(null);
            @SuppressWarnings("unchecked")
            Map<String, Object> splitMap = (Map<String, Object>) splitField.get(null);

            map.put("split", (Function4<PokemonBattle, InstructionSet, BattleMessage, Iterator<BattleMessage>, InterpreterInstruction>) (battle, instructionSet, message, messages) -> {
                BattleMessage privateMessage = messages.next();
                BattleMessage publicMessage = messages.next();

                BattleActor targetActor = battle.getActor(message.argumentAt(0));
                if (targetActor == null) return new IgnoredInstruction();

                String raw = publicMessage.getRawMessage();
                String type = (raw != null && raw.contains("|")) ? raw.split("\\|")[1] : "ignored";

                @SuppressWarnings("unchecked")
                Function6<PokemonBattle, BattleActor, InstructionSet, BattleMessage, BattleMessage, Iterator<BattleMessage>, InterpreterInstruction> splitHandler =
                        (Function6<PokemonBattle, BattleActor, InstructionSet, BattleMessage, BattleMessage, Iterator<BattleMessage>, InterpreterInstruction>) splitMap.get(type);

                if (splitHandler != null) {
                    return splitHandler.invoke(battle, targetActor, instructionSet, publicMessage, privateMessage, messages);
                }

                return new IgnoredInstruction();
            });

            Object ultraBurstFunction = (Function4<PokemonBattle, InstructionSet, BattleMessage, Iterator<BattleMessage>, InterpreterInstruction>) (battle, set, message, messages) -> new UltraInstruction(message);

            map.put("-burst", ultraBurstFunction);

            Object canDynamaxFunction = (Function4<PokemonBattle, InstructionSet, BattleMessage, Iterator<BattleMessage>, InterpreterInstruction>) (battle, set, message, messages) -> new CanDynamaxInstruction(message);

            map.put("-candynamax", canDynamaxFunction);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject showdownInterpretations", e);
        }
    }
}