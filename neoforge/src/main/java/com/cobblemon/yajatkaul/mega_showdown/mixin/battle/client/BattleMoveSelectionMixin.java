package com.cobblemon.yajatkaul.mega_showdown.mixin.battle.client;

import com.cobblemon.mod.common.battles.ShiftActionResponse;
import com.cobblemon.mod.common.client.battle.SingleActionRequest;
import com.cobblemon.mod.common.client.gui.battle.BattleGUI;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleBackButton;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleGimmickButton;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleMoveSelection;
import com.cobblemon.yajatkaul.mega_showdown.utility.backporting.BattleShiftButton;
import com.cobblemon.yajatkaul.mega_showdown.utility.backporting.BattleTargetSelection;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = BattleMoveSelection.class)
public class BattleMoveSelectionMixin {

    @Final
    @Shadow(remap = false)
    private BattleBackButton backButton;

    @Unique
    private BattleShiftButton cachedShiftButton = null;

    @Inject(method = "<init>", at = @At(value = "RETURN"), remap = false)
    private void onInit(BattleGUI battleGUI, SingleActionRequest request, CallbackInfo ci) {
        if (this.cachedShiftButton == null) {
            BattleMoveSelection self = (BattleMoveSelection) (Object) this;

            this.cachedShiftButton = new BattleShiftButton(
                    backButton.getX() + 22.5F,
                    Minecraft.getInstance().getWindow().getGuiScaledHeight() - 22F,
                    self.getRequest()
            );
        }
    }

    /**
     * @author Yajat
     * @reason I HATE MYSELF
     */
    @Overwrite(remap = false)
    public boolean mousePrimaryClicked(double mouseX, double mouseY) {
        BattleMoveSelection self = (BattleMoveSelection) (Object) this;

        List<BattleMoveSelection.MoveTile> moveTiles = self.getMoveTiles();

        BattleGUI battleGUI = self.getBattleGUI();

        BattleMoveSelection.MoveTile move = moveTiles.stream().filter(m -> m.isHovered(mouseX, mouseY)).findFirst().orElse(null);
        BattleGimmickButton gimmick = self.getGimmickButtons().stream().filter(g -> g.isHovered(mouseX, mouseY)).findFirst().orElse(null);

        if (move != null) {
            if (self.getRequest().getActivePokemon().getFormat().getBattleType().getPokemonPerSide() == 1) {
                move.onClick();
                return true;
            } else if (move.getSelectable()) {
                battleGUI.changeActionSelection(
                        new BattleTargetSelection(battleGUI, self.getRequest(), move.getMove(), move.getResponse().getGimmickID(), move.getMove().getGimmickMove())
                );
                self.playDownSound(Minecraft.getInstance().getSoundManager());
                return true;
            }
        } else if (self.getBackButton().isHovered(mouseX, mouseY)) {
            self.playDownSound(Minecraft.getInstance().getSoundManager());
            battleGUI.changeActionSelection(null);
        } else if (gimmick != null) {
            for (BattleGimmickButton button : self.getGimmickButtons()) {
                if (button != gimmick) {
                    button.setToggled(false);
                }
            }
            self.setMoveTiles(gimmick.toggle() ? gimmick.getTiles() : self.getBaseTiles());
        } else if (this.cachedShiftButton.isHovered(mouseX, mouseY) && this.cachedShiftButton.getEnabled()) {
            self.playDownSound(Minecraft.getInstance().getSoundManager());
            battleGUI.selectAction(self.getRequest(), new ShiftActionResponse());
        }

        return false;
    }

    @WrapWithCondition(
            method = "renderWidget",
            at = @At(value = "INVOKE", target = "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleShiftButton;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V")
    )
    private boolean onlyRenderIfAllowed(com.cobblemon.mod.common.client.gui.battle.subscreen.BattleShiftButton instance, GuiGraphics context, int mouseX, int mouseY, float delta) {
        return false;
    }

    @Inject(method = "renderWidget", at = @At(value = "HEAD"))
    private void renderButton(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        cachedShiftButton.render(context, mouseX, mouseY, delta);
    }
}
