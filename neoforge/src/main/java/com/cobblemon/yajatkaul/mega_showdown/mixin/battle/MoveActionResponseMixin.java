package com.cobblemon.yajatkaul.mega_showdown.mixin.battle;

import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.battles.*;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(value = MoveActionResponse.class, remap = false)
public abstract class MoveActionResponseMixin {

    @Shadow
    private String moveName;
    @Shadow
    private String targetPnx;
    @Shadow
    private String gimmickID;

    /**
     * @author YajatKaul
     * @reason TargetSelection
     */
    @Overwrite
    public boolean isValid(ActiveBattlePokemon activeBattlePokemon, ShowdownMoveset showdownMoveSet, boolean forceSwitch) {
        if (forceSwitch || showdownMoveSet == null) {
            return false;
        }

        InBattleMove move = null;
        for (InBattleMove m : showdownMoveSet.getMoves()) {
            if (m.getId().equals(moveName)) {
                move = m;
                break;
            }
        }

        if (move == null) {
            return false;
        }

        InBattleGimmickMove gimmickMove = move.getGimmickMove();
        boolean validGimmickMove = gimmickMove != null && !gimmickMove.getDisabled();

        if (!validGimmickMove && !move.canBeUsed()) {
            return false;
        }

        MoveTarget target = (gimmickID != null && validGimmickMove)
                ? gimmickMove.getTarget()
                : move.getTarget();

        Function1<Targetable, List<Targetable>> targetFunction = target.getTargetList();
        List<Targetable> rawTargets = targetFunction != null
                ? targetFunction.invoke((Targetable) activeBattlePokemon)
                : null;

        List<ActiveBattlePokemon> availableTargets = null;

        if (rawTargets == null || rawTargets.isEmpty()) {
            return true;
        } else {
            availableTargets = rawTargets.stream()
                    .filter(p -> p instanceof ActiveBattlePokemon)
                    .map(p -> (ActiveBattlePokemon) p)
                    .collect(Collectors.toList());
        }

        if (targetPnx == null) {
            return false;
        }

        Pair<BattleActor, ActiveBattlePokemon> result =
                activeBattlePokemon.getActor().getBattle().getActorAndActiveSlotFromPNX(targetPnx);

        ActiveBattlePokemon targetPokemon = result.getSecond();

        return availableTargets.contains(targetPokemon);
    }
}