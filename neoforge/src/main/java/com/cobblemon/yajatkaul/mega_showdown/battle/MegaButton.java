package com.cobblemon.yajatkaul.mega_showdown.battle;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MegaButton extends AbstractButton {
    static final ResourceLocation SPRITE_SHEET = ResourceLocation.fromNamespaceAndPath(
            MegaShowdown.MOD_ID, "textures/gui/battle_move.png");

    // All values of sprite sheet in pixels
    static final int BUTTON_TEXTURE_WIDTH = 75; // Overall png pixel width
    static final int BUTTON_TEXTURE_HEIGHT = 17; // Overall png pixel height

    static final int BUTTON_WIDTH = 25; // Width in pixels within game
    static final int BUTTON_HEIGHT = 25; // Height in pixels within game

    static final int BUTTON_UPOS_INACTIVE = 0; // UOffset within sprite sheet for inactive image
    static final int BUTTON_UPOS_ACTIVE = 25; // UOffset within sprite sheet for active image
    static final int BUTTON_UPOS_HOVER = 50; // UOffset within sprite sheet for hovering image

    static final int BUTTON_VPOS_INACTIVE = 0; // VOffset within sprite sheet for inactive image
    static final int BUTTON_VPOS_ACTIVE = 25; // VOffset within sprite sheet for active image
    static final int BUTTON_VPOS_HOVER = 50; // VOffset within sprite sheet for hovering image

    private final Screen screen;

    protected MegaButton (int x, int y, Screen screen) {
        super(x, y, BUTTON_TEXTURE_WIDTH, BUTTON_TEXTURE_HEIGHT, CommonComponents.EMPTY);
        this.screen = screen;
    }

    @Override
    public void onPress() {
        if(!this.active)return;
        // Do stuff when button is pressed here.
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (!this.active) {
            guiGraphics.blit( // For when button isn't pressable.
                    SPRITE_SHEET, this.getX(), this.getY(),
                    BUTTON_UPOS_INACTIVE, BUTTON_VPOS_INACTIVE ,
                    BUTTON_WIDTH, BUTTON_HEIGHT,
                    BUTTON_TEXTURE_WIDTH, BUTTON_TEXTURE_HEIGHT);
        }
        else if (this.isHoveredOrFocused()) { // For hovering over with mouse.
            guiGraphics.blit(
                    SPRITE_SHEET, this.getX(), this.getY(),
                    BUTTON_UPOS_HOVER, BUTTON_VPOS_HOVER ,
                    BUTTON_WIDTH, BUTTON_HEIGHT,
                    BUTTON_TEXTURE_WIDTH, BUTTON_TEXTURE_HEIGHT);
        }
        else { // For both active/pressed.
            guiGraphics.blit(
                    SPRITE_SHEET, this.getX(), this.getY(),
                    BUTTON_UPOS_ACTIVE, BUTTON_VPOS_ACTIVE ,
                    BUTTON_WIDTH, BUTTON_HEIGHT,
                    BUTTON_TEXTURE_WIDTH, BUTTON_TEXTURE_HEIGHT);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput arg) {

    }

}