package com.cobblemon.yajatkaul.mega_showdown.instructions

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage
import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import com.cobblemon.mod.common.api.text.yellow
import com.cobblemon.mod.common.battles.dispatch.InstructionSet
import com.cobblemon.mod.common.battles.dispatch.InterpreterInstruction
import com.cobblemon.mod.common.battles.interpreter.instructions.MoveInstruction
import com.cobblemon.mod.common.util.battleLang
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.UltraBurstEventStart


class SuperEffectiveInstruction(val message: BattleMessage,val instructionSet: InstructionSet): InterpreterInstruction {
    override fun invoke(battle: PokemonBattle) {
        val lastCauser = instructionSet.getMostRecentCauser(comparedTo = this)
        battle.dispatchGo {
            val pokemon = message.battlePokemon(0, battle) ?: return@dispatchGo
            if (lastCauser is MoveInstruction && lastCauser.spreadTargets.isNotEmpty()) {
                val pokemonName = pokemon.getName()
                battle.broadcastChatMessage(battleLang("extremelyEffective_spread", pokemonName))
            } else {
                battle.broadcastChatMessage(battleLang("extremelyEffective"))
            }
            battle.minorBattleActions[pokemon.uuid] = message
        }
    }
}