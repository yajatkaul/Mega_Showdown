package com.cobblemon.yajatkaul.mega_showdown.utility.backporting

import com.cobblemon.mod.common.battles.ActiveBattlePokemon
import com.cobblemon.mod.common.battles.ShowdownMoveset

object Validator{
    @JvmStatic
    fun isValid(
        activeBattlePokemon: ActiveBattlePokemon,
        showdownMoveSet: ShowdownMoveset?,
        forceSwitch: Boolean,
        moveName: String,
        targetPnx: String? = null,
        gimmickID: String? = null
    ): Boolean {
        if (forceSwitch || showdownMoveSet == null) {
            return false
        }

        val move = showdownMoveSet.moves.find { it.id == moveName } ?: return false
        val gimmickMove = move.gimmickMove
        val validGimmickMove = gimmickMove != null && !gimmickMove.disabled
        if (!validGimmickMove && !move.canBeUsed()) {
            return false
        }

        val targetList = (if (gimmickID != null && validGimmickMove) gimmickMove!!.target else move.target)
            .targetList(activeBattlePokemon)

        if (targetList.isNullOrEmpty()) {
            return targetPnx == null // Valid only if no target was provided
        }

        val pnx = targetPnx ?: return false
        val (_, targetPokemon) = activeBattlePokemon.actor.battle.getActorAndActiveSlotFromPNX(pnx)
        return targetPokemon in targetList
    }
}