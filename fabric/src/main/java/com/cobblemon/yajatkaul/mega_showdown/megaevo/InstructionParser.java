package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.dispatch.InstructionSet;
import com.cobblemon.mod.common.battles.dispatch.InterpreterInstruction;

import java.util.Iterator;

@FunctionalInterface
public interface InstructionParser {
    InterpreterInstruction apply(
            PokemonBattle battle,
            InstructionSet instructionSet,
            BattleMessage message,
            Iterator<BattleMessage> messages
    );
}