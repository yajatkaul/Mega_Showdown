package com.cobblemon.yajatkaul.mega_showdown.mixin.accessors;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.battles.interpreter.instructions.EndInstruction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EndInstruction.class, remap = false)
public interface EndInstructionAccessor {
    @Accessor("message")
    BattleMessage getMessage();
}