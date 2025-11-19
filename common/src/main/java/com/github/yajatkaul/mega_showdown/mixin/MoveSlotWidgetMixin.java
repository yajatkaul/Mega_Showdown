package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.client.gui.summary.widgets.screens.moves.MoveSlotWidget;
import com.cobblemon.mod.common.client.gui.summary.widgets.screens.moves.MovesWidget;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.networking.packets.SecretSwordMoveSwapPacket;
import dev.architectury.networking.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MoveSlotWidget.class, remap = false)
public class MoveSlotWidgetMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void inject(int pX, int pY, Move move, MovesWidget movesWidget, Pokemon pokemon, CallbackInfo ci) {
        if (move != null) {
            if (pokemon.getSpecies().getName().equals("Keldeo")) {
                NetworkManager.sendToServer(new SecretSwordMoveSwapPacket("MoveSwapped"));
            }
        }
    }
}
