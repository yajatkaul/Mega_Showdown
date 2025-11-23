package com.github.yajatkaul.mega_showdown.mixin.client.ui;

import com.cobblemon.mod.common.client.gui.battle.BattleOverlay;
import com.github.yajatkaul.mega_showdown.screen.BattleOverlayScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BattleOverlay.class)
public class BattleOverlayMixin {
    @Inject(
            method = "render",
            at = @At("RETURN")
    )
    private void render(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        PoseStack pose = context.pose();
        pose.pushPose();
        pose.translate(0.0, 0.0, 500.0);
        BattleOverlayScreen.INSTANCE.render(context);
        pose.popPose();
    }
}
