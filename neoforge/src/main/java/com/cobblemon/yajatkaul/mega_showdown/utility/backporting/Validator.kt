package com.cobblemon.yajatkaul.mega_showdown.utility.backporting

import com.cobblemon.mod.common.battles.ActiveBattlePokemon
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
        if (forceSwitch || showdownMoveSet == null) {
            return false
        }

        val move = showdownMoveSet.moves.find { it.id == moveName } ?: return false
        val gimmickMove = move.gimmickMove
        val validGimmickMove = gimmickMove != null && !gimmickMove.disabled
        if (!validGimmickMove && !move.canBeUsed()) {
            return false
        }
        val availableTargets =
            (if (gimmickID != null && validGimmickMove && gimmickID != "terastal" && gimmickID != "mega") gimmickMove!!.target else move.target)
                .targetList(activeBattlePokemon)?.takeIf { it.isNotEmpty() } ?: return true

        val pnx = targetPnx ?: return false // If the targets list is non-null then they need to have specified a target
        val (_, targetPokemon) = activeBattlePokemon.actor.battle.getActorAndActiveSlotFromPNX(pnx)
        if (targetPokemon !in availableTargets) {
            return false // It's not a possible target.
        }

        return true
    }
}
