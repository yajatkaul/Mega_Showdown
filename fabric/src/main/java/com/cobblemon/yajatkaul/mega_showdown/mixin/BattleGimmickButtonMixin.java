package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleGimmickButton;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleMoveSelection;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(value = BattleGimmickButton.Companion.class,remap = false)
public class BattleGimmickButtonMixin {
    @Inject(method = "create", at = @At("HEAD"), remap = false, cancellable = true)
    private void handleUltraBurst(ShowdownMoveset.Gimmick gimmick, BattleMoveSelection moveSelection, float x, float y, CallbackInfoReturnable<BattleGimmickButton> cir){
        if(gimmick == ShowdownMoveset.Gimmick.ULTRA_BURST) {
            cir.setReturnValue(new BattleGimmickButton(gimmick, x, y) {
                @Override
                public @NotNull List<BattleMoveSelection.MoveTile> getTiles() {
                    return moveSelection.getBaseTiles().stream().map(tile -> new GimmickTile(gimmick, moveSelection, tile.getMove(), tile.getX(), tile.getY())).collect(Collectors.toUnmodifiableList());
                }
            });
        }
    }
}