package com.cobblemon.yajatkaul.mega_showdown.instructions

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage
import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import com.cobblemon.mod.common.api.text.yellow
import com.cobblemon.mod.common.battles.dispatch.InterpreterInstruction
import com.cobblemon.mod.common.util.battleLang
import com.cobblemon.yajatkaul.mega_showdown.event.ultra.UltraEvent
import net.neoforged.neoforge.common.NeoForge


class UltraInstruction(val message: BattleMessage) : InterpreterInstruction {
    override fun invoke(battle: PokemonBattle) {
        val battlePokemon = message.battlePokemon(0, battle) ?: return
        battle.dispatchWaiting {
            val pokemonName = battlePokemon.getName()
            battle.broadcastChatMessage(battleLang("ultra", pokemonName).yellow())
            battle.minorBattleActions[battlePokemon.uuid] = message

            val pokemon = message.battlePokemon(0, battle)
            val event = UltraEvent(battle, pokemon)
            NeoForge.EVENT_BUS.post(event)
        }
    }
}