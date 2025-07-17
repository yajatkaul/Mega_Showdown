package com.cobblemon.yajatkaul.mega_showdown.utility.backporting

import com.cobblemon.mod.common.battles.ActiveBattlePokemon
import com.cobblemon.mod.common.battles.BattleTypes
import com.cobblemon.mod.common.battles.MoveTarget
import com.cobblemon.mod.common.battles.ShowdownMoveset


object Validator {
    @JvmStatic
    fun isValid(
        activeBattlePokemon: ActiveBattlePokemon,
        showdownMoveSet: ShowdownMoveset?,
        forceSwitch: Boolean,
        moveName: String,
        targetPnx: String? = null,
        gimmickID: String? = null
    ): Boolean {
        if (forceSwitch || showdownMoveSet == null) return false

        val move = showdownMoveSet.moves.find { it.id == moveName } ?: return false
        val gimmickMove = move.gimmickMove
        val isGimmickValid = gimmickMove != null && !gimmickMove.disabled

        if (!isGimmickValid && !move.canBeUsed()) return false

        val target = if (gimmickID == "dynamax" && isGimmickValid) gimmickMove!!.target else move.target
        val validTargets = target.targetList(activeBattlePokemon)

        val format = activeBattlePokemon.battle.format.battleType

        if (validTargets.isNullOrEmpty()) {
            return targetPnx == null
        }

        val isGimmickAOEInSingles = format == BattleTypes.SINGLES &&
                gimmickID == "dynamax" &&
                move.target in listOf(MoveTarget.allAdjacent, MoveTarget.allAdjacentFoes)

        if (targetPnx == null) {
            return when {
                isGimmickAOEInSingles -> true
                format != BattleTypes.SINGLES && move.target == MoveTarget.adjacentFoe -> true
                else -> false
            }
        }

        val (_, targetPokemon) = activeBattlePokemon.actor.battle.getActorAndActiveSlotFromPNX(targetPnx)

        return targetPokemon in validTargets
    }
}