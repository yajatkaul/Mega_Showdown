package com.github.yajatkaul.mega_showdown.cobblemon.battle.instruction;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.dispatch.CauserInstruction;
import com.cobblemon.mod.common.battles.dispatch.InstructionSet;
import com.cobblemon.mod.common.battles.dispatch.InterpreterInstruction;
import com.cobblemon.mod.common.battles.interpreter.instructions.MoveInstruction;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.util.LocalizationUtilsKt;
import kotlin.Unit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public record SuperEffectiveInstruction(
        BattleMessage message,
        InstructionSet instructionSet
) implements InterpreterInstruction {
    @Override
    public void invoke(@NotNull PokemonBattle pokemonBattle) {
        BattlePokemon battlePokemon = message.battlePokemon(0, pokemonBattle);
        if (battlePokemon == null) return;

        pokemonBattle.dispatchGo(() -> {
            CauserInstruction lastCauser = instructionSet.getMostRecentCauser(this);
            if (lastCauser instanceof MoveInstruction && !((MoveInstruction) lastCauser).getSpreadTargets().isEmpty()) {
                MutableComponent pokemonName = battlePokemon.getName();
                pokemonBattle.broadcastChatMessage(LocalizationUtilsKt.battleLang("extremelyEffective_spread", pokemonName).withStyle(ChatFormatting.YELLOW));
            } else {
                pokemonBattle.broadcastChatMessage(LocalizationUtilsKt.battleLang("extremelyEffective").withStyle(ChatFormatting.YELLOW));
            }
            pokemonBattle.getMinorBattleActions().put(battlePokemon.getUuid(), message);
            return Unit.INSTANCE;
        });
    }
}
