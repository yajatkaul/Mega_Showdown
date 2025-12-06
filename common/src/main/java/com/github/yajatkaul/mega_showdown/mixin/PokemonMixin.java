package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.api.moves.BenchedMove;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.MoveSet;
import com.cobblemon.mod.common.api.pokemon.moves.LearnsetQuery;
import com.cobblemon.mod.common.pokemon.Pokemon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Pokemon.class, remap = false)
public class PokemonMixin {
    @Inject(method = "validateMoveset", at = @At(value = "HEAD"), cancellable = true)
    private void inject(boolean includeLegacy, CallbackInfo ci) {
        Pokemon self = (Pokemon) (Object) this;
        LearnsetQuery query = LearnsetQuery.Companion.getANY();

        for (int i = 0; i < MoveSet.MOVE_COUNT; i++) {
            Move moveSet = self.getMoveSet().get(i);
            if (moveSet != null && !query.canLearn(moveSet.getTemplate(), self.getForm().getMoves())) {
                self.getMoveSet().setMove(i, null);
            }
        }
        var benchedIterator = self.getBenchedMoves().iterator();
        while (benchedIterator.hasNext()) {
            BenchedMove benchedMove = benchedIterator.next();
            if (!query.canLearn(benchedMove.getMoveTemplate(), self.getForm().getMoves())) {
                benchedIterator.remove();
            }
        }
        ci.cancel();
    }
}
