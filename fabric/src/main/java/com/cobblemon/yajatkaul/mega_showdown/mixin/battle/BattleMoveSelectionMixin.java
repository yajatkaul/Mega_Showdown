package com.cobblemon.yajatkaul.mega_showdown.mixin.battle;

import com.cobblemon.mod.common.battles.ShiftActionResponse;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.mod.common.client.gui.battle.BattleGUI;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleBackButton;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleMoveSelection;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleShiftButton;
import com.cobblemon.yajatkaul.mega_showdown.utility.backporting.BattleGimmickButton;
import com.cobblemon.yajatkaul.mega_showdown.utility.backporting.BattleTargetSelection;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = BattleMoveSelection.class, remap = false)
public class BattleMoveSelectionMixin {
    @Shadow
    private ShowdownMoveset moveSet;

    @Shadow
    private BattleBackButton backButton;

    @Unique
    private List<BattleGimmickButton> cachedGimmickButtons = null;

    /**
     * @author Yajat
     * @reason I HATE MYSELF
     */
    @Overwrite
    public boolean mousePrimaryClicked(double mouseX, double mouseY) {
        BattleMoveSelection self = (BattleMoveSelection) (Object) this;

        List<BattleMoveSelection.MoveTile> moveTiles = self.getMoveTiles();

        // Only initialize once
        if (cachedGimmickButtons == null) {
            cachedGimmickButtons = new ArrayList<>();
            List<ShowdownMoveset.Gimmick> gimmicks = moveSet.getGimmicks();
            for (int index = 0; index < gimmicks.size(); index++) {
                ShowdownMoveset.Gimmick gimmick = gimmicks.get(index);
                float initOff = BattleBackButton.WIDTH * 0.65F;
                float xOff = initOff + BattleGimmickButton.SPACING * index;
                BattleGimmickButton button = BattleGimmickButton.Companion.create(gimmick, self, backButton.getX() + xOff, backButton.getY());
                cachedGimmickButtons.add(button);
            }
        }

        BattleGUI battleGUI = self.getBattleGUI();

        BattleMoveSelection.MoveTile move = moveTiles.stream().filter(m -> m.isHovered(mouseX, mouseY)).findFirst().orElse(null);
        BattleGimmickButton gimmick = cachedGimmickButtons.stream().filter(g -> g.isHovered(mouseX, mouseY)).findFirst().orElse(null);

        if (move != null) {
            if (self.getRequest().getActivePokemon().getFormat().getBattleType().getPokemonPerSide() == 1) {
                move.onClick();
                return true;
            } else if (move.getSelectable()) {
                battleGUI.changeActionSelection(
                        new BattleTargetSelection(battleGUI, self.getRequest(), move.getMove(), move.getResponse().getGimmickID(), move.getMove().getGimmickMove())
                );
                self.playDownSound(MinecraftClient.getInstance().getSoundManager());
                return true;
            }
        } else if (self.getBackButton().isHovered(mouseX, mouseY)) {
            self.playDownSound(MinecraftClient.getInstance().getSoundManager());
            battleGUI.changeActionSelection(null);
        } else if (gimmick != null && gimmick.getSelectable()) {
            for (BattleGimmickButton button : cachedGimmickButtons) {
                if (button != gimmick) {
                    button.setToggled(false);
                }
            }
            self.setMoveTiles(gimmick.toggle() ? gimmick.getTiles() : self.getBaseTiles());
        } else if (self.getShiftButton() != null && self.getShiftButton().isHovered(mouseX, mouseY)) {
            self.playDownSound(MinecraftClient.getInstance().getSoundManager());
            battleGUI.selectAction(self.getRequest(), new ShiftActionResponse());
        }

        return false;
    }

    /**
     * @author Yajat
     * @reason Trust me im not a fan of overwrites
     */
    @Overwrite
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        BattleMoveSelection self = (BattleMoveSelection) (Object) this;

        self.getMoveTiles().forEach(tile -> tile.render(context, mouseX, mouseY, delta));
        backButton.render(context, mouseX, mouseY, delta);

        // Only initialize gimmick buttons once
        if (cachedGimmickButtons == null) {
            cachedGimmickButtons = new ArrayList<>();
            List<ShowdownMoveset.Gimmick> gimmicks = moveSet.getGimmicks();
            for (int index = 0; index < gimmicks.size(); index++) {
                ShowdownMoveset.Gimmick gimmick = gimmicks.get(index);
                float initOff = BattleBackButton.WIDTH * 0.65F;
                float xOff = initOff + BattleGimmickButton.SPACING * index;
                BattleGimmickButton button = BattleGimmickButton.Companion.create(gimmick, self, backButton.getX() + xOff, backButton.getY());
                cachedGimmickButtons.add(button);
            }
        }

        for (BattleGimmickButton button : cachedGimmickButtons) {
            button.render(context, mouseX, mouseY, delta);
        }

        if (self.getRequest().getActivePokemon().getFormat().getBattleType().getSlotsPerActor() == 3) {
            String pnx = self.getRequest().getActivePokemon().getPNX();
            if (pnx.length() >= 3 && (pnx.charAt(2) == 'a' || pnx.charAt(2) == 'c')) {
                BattleShiftButton shiftButton = self.getShiftButton();
                if (shiftButton != null) {
                    shiftButton.render(context, mouseX, mouseY, delta);
                }
            }
        }
    }
}
