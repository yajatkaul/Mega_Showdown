package com.github.yajatkaul.mega_showdown.cobblemon.battle.instruction;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.dispatch.InterpreterInstruction;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.util.LocalizationUtilsKt;
import com.github.yajatkaul.mega_showdown.event.custom.UltraBurstEvents;
import kotlin.Unit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public record UltraInstruction(
        BattleMessage message
) implements InterpreterInstruction {
    @Override
    public void invoke(@NotNull PokemonBattle pokemonBattle) {
        BattlePokemon battlePokemon = message.battlePokemon(0, pokemonBattle);
        if (battlePokemon == null) return;

        pokemonBattle.dispatchWaiting(1f, () -> {
            MutableComponent pokemonName = battlePokemon.getName();
            pokemonBattle.broadcastChatMessage(LocalizationUtilsKt.battleLang("ultra", pokemonName).withStyle(ChatFormatting.YELLOW));
            pokemonBattle.getMinorBattleActions().put(battlePokemon.getUuid(), message);
            UltraBurstEvents.ULTRA_BURST.invoker().onUltraBurst(pokemonBattle, battlePokemon);
            return Unit.INSTANCE;
        });
    }
}
