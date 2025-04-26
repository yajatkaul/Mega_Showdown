package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.battles.InBattleMove;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleGimmickButton;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleMoveSelection;
import com.cobblemon.mod.common.util.math.SimpleMathExtensionsKt;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BattleGimmickButton.GimmickTile.class, remap = false)
public class GimmickTileMixin {
    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void init(ShowdownMoveset.Gimmick gimmick, BattleMoveSelection moveSelection, InBattleMove move, float x, float y, CallbackInfo ci) {
        if(gimmick == ShowdownMoveset.Gimmick.TERASTALLIZATION || gimmick == ShowdownMoveset.Gimmick.MEGA_EVOLUTION) {
            BattleGimmickButton.GimmickTile tile = (BattleGimmickButton.GimmickTile) (Object) this;
            tile.setMoveTemplate(Moves.INSTANCE.getByNameOrDummy(move.id));
            tile.setRgb(SimpleMathExtensionsKt.toRGB(tile.getMoveTemplate().getEffectiveElementalType(tile.getPokemon()).getHue()));
        }
    }

    @Shadow
    private ShowdownMoveset.Gimmick gimmick;

    @ModifyReturnValue(method = "getSelectable", at = @At("RETURN"), remap = false)
    private boolean modifySelectable(boolean originalSelectable) {
        BattleGimmickButton.GimmickTile tile = (BattleGimmickButton.GimmickTile) (Object) this;

        if (gimmick == ShowdownMoveset.Gimmick.MEGA_EVOLUTION) {
            if (tile.getPokemon().getSpecies().getName().equalsIgnoreCase("rayquaza")) {
                // Allow only dragonascent
                if (!tile.getMove().getId().equalsIgnoreCase("dragonascent")) {
                    return false; // Disable other moves
                }
            }
        }
        return originalSelectable; // Keep original behavior otherwise
    }
}