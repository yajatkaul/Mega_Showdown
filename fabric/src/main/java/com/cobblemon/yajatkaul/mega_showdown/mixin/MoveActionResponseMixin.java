package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.battles.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(value = MoveActionResponse.class, remap = false)
public abstract class MoveActionResponseMixin {

    @Shadow public String moveName;
    @Shadow @Nullable public String targetPnx;
    @Shadow @Nullable public String gimmickID;

    /**
     * @author YajatKaul
     * @reason Fixing z moves, tera spread attack bug
     */
    @Overwrite
    public boolean isValid(ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet, boolean forceSwitch) {
        if (forceSwitch || showdownMoveSet == null) {
            return false;
        }

        InBattleMove move = showdownMoveSet.getMoves().stream()
                .filter(m -> m.getId().equals(moveName))
                .findFirst()
                .orElse(null);
        if (move == null) return false;

        InBattleGimmickMove gimmickMove = move.getGimmickMove();
        boolean validGimmickMove = gimmickMove != null && !gimmickMove.getDisabled();
        if (!validGimmickMove && !move.canBeUsed()) {
            return false;
        }

        MoveTarget moveTarget = (gimmickID != null && gimmickMove != null) ? gimmickMove.getTarget() : move.getTarget();
        if (moveTarget == null) moveTarget = move.getTarget();

        List<?> availableTargets = moveTarget.getTargetList().invoke(activeBattlePokemon);
        if (availableTargets == null || availableTargets.isEmpty()) return true;

        if (targetPnx == null) return false;
        var pair = activeBattlePokemon.getActor().getBattle().getActorAndActiveSlotFromPNX(targetPnx);
        var targetPokemon = pair.getSecond();

        if (!availableTargets.contains(targetPokemon)) return false;

        if (
                ("terastal".equals(gimmickID) || "dynamax".equals(gimmickID)) &&
                        (moveTarget == MoveTarget.allAdjacent || moveTarget == MoveTarget.allAdjacentFoes || moveTarget == MoveTarget.adjacentFoe) &&
                        activeBattlePokemon.getBattle().getFormat().getBattleType() == BattleTypes.INSTANCE.getSINGLES()
        ) {
            this.targetPnx = null;
            return true;
        }

        return true;
    }
}