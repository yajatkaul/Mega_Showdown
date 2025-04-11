package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.battles.ShowdownInterpreter;
import com.cobblemon.yajatkaul.mega_showdown.instructions.UltraInstruction;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.InstructionParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = ShowdownInterpreter.class, remap = false)
public abstract class ShowdownInterpreterMixin {

    @Shadow
    private static Map<String, Object> updateInstructionParser;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void onStaticInit(CallbackInfo ci) {
        updateInstructionParser.put("-burst", (InstructionParser) (battle, instructionSet, message, messages) ->
                new UltraInstruction(message)
        );
    }
}