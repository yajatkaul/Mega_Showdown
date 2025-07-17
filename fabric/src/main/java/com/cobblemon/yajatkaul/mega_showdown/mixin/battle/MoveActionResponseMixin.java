package com.cobblemon.yajatkaul.mega_showdown.mixin.battle;

import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.MoveActionResponse;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.yajatkaul.mega_showdown.utility.backporting.Validator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

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
        return Validator.isValid(activeBattlePokemon, showdownMoveSet, forceSwitch, moveName, targetPnx, gimmickID);
    }
}