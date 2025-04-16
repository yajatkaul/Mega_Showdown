package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.battles.InBattleMove;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleGimmickButton;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleMoveSelection;
import com.cobblemon.mod.common.util.math.SimpleMathExtensionsKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BattleGimmickButton.GimmickTile.class)
public class GimmickTileMixin {
    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void init(ShowdownMoveset.Gimmick gimmick, BattleMoveSelection moveSelection, InBattleMove move, float x, float y, CallbackInfo ci) {
        if(gimmick == ShowdownMoveset.Gimmick.TERASTALLIZATION || gimmick == ShowdownMoveset.Gimmick.MEGA_EVOLUTION) {
            BattleGimmickButton.GimmickTile tile = (BattleGimmickButton.GimmickTile) (Object) this;
            tile.setMoveTemplate(Moves.INSTANCE.getByNameOrDummy(move.id));
            tile.setRgb(SimpleMathExtensionsKt.toRGB(tile.getMoveTemplate().getEffectiveElementalType(tile.getPokemon()).getHue()));
        }
    }
}