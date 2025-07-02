package com.cobblemon.yajatkaul.mega_showdown.instructions

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage
import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import com.cobblemon.mod.common.api.text.yellow
import com.cobblemon.mod.common.battles.dispatch.InterpreterInstruction
import com.cobblemon.mod.common.util.battleLang
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.UltraBurstEventStart


class UltraInstruction(val message: BattleMessage) : InterpreterInstruction {
    override fun invoke(battle: PokemonBattle) {
        val battlePokemon = message.battlePokemon(0, battle) ?: return
        battle.dispatchWaiting {
            val pokemonName = battlePokemon.getName()
            battle.broadcastChatMessage(battleLang("ultra", pokemonName).yellow())
            battle.minorBattleActions[battlePokemon.uuid] = message

            val pokemon = message.battlePokemon(0, battle)
            UltraBurstEventStart.EVENT.invoker().onUltra(battle, pokemon)
        }
    }
}