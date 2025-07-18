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

        val target = if (
            isGimmickValid &&
            gimmickID in listOf("zmove", "max") &&
            gimmickMove?.move != move.id
        ) gimmickMove!!.target else move.target

        val validTargets = target.targetList(activeBattlePokemon)
        val format = activeBattlePokemon.battle.format.battleType

        val noTargetRequired = target in listOf(
            MoveTarget.self,
            MoveTarget.all,
            MoveTarget.allAdjacent,
            MoveTarget.allAdjacentFoes,
            MoveTarget.allyTeam,
            MoveTarget.allySide,
            MoveTarget.foeSide,
            MoveTarget.allies,
            MoveTarget.randomNormal,
            MoveTarget.scripted
        )

        val isMultiTarget = validTargets?.size?.let { it > 1 } == true

        if (targetPnx == null) {
            if (noTargetRequired || isMultiTarget) return true
            if (format == BattleTypes.SINGLES && target == MoveTarget.adjacentFoe) return true
            return false
        }

        val (_, targetPokemon) = activeBattlePokemon.actor.battle.getActorAndActiveSlotFromPNX(targetPnx)
        return validTargets?.contains(targetPokemon) == true
    }
}
