package com.cobblemon.yajatkaul.mega_showdown.mixin.battle.client;

import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.client.battle.ClientBattle;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleGimmickButton;
import com.cobblemon.yajatkaul.mega_showdown.utility.backporting.interfaces.ClientBattleDuck;
import com.cobblemon.yajatkaul.mega_showdown.utility.backporting.interfaces.SelectableDuck;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BattleGimmickButton.class, remap = false)
public class BattleGimmickButtonMixin implements SelectableDuck {

    @Shadow
    private boolean toggled;

    @Unique
    private ShowdownMoveset.Gimmick gimmick;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(ShowdownMoveset.Gimmick gimmick, float x, float y, CallbackInfo ci) {
        this.gimmick = gimmick;
    }

    @Override
    public boolean isSelectable() {
        ClientBattle battle = CobblemonClient.INSTANCE.getBattle();
        if (battle == null) return false;

        ShowdownMoveset.Gimmick gimmick = this.gimmick;
        return ((ClientBattleDuck) (Object) battle).pendingGimmick(gimmick);
    }


    @Inject(method = "toggle", at = @At("HEAD"), remap = false)
    private void onToggle(CallbackInfoReturnable<Boolean> cir) {
        if (!((SelectableDuck) (Object) this).isSelectable()) {
            toggled = !toggled;
        }
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE"
            , target = "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGimmickButton;isHovered(DD)Z"))
    private boolean isHovered(boolean original) {
        return original && isSelectable();
    }

    @WrapOperation(
            method = "render",
            remap = true,
            at = @At(value = "INVOKE", target = "Lcom/cobblemon/mod/common/api/gui/GuiUtilsKt;blitk$default(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Identifier;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;ZFILjava/lang/Object;)V")
    )
    private void changeAlpha(MatrixStack matrixStack, Identifier identifier,
                             Number number, Number number1, Number number2, Number number3,
                             Number number4, Number number5, Number number6, Number number7, Number number8,
                             Number number9, Number number10, Number number11, Number number12, boolean b, float v,
                             int i, Object o, Operation<Void> original) {
        if (isSelectable()) {
            number12 = 1.0F;
        } else {
            number12 = 0.5F;
        }
        i &= ~16384;
        original.call(matrixStack, identifier, number, number1, number2, number3,
                number4, number5, number6, number7, number8, number9,
                number10, number11, number12, b, v, i, o);
    }
}
