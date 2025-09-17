package com.cobblemon.yajatkaul.mega_showdown.utility.summary_page;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

public abstract class CodeFeatureRenderer {
    public boolean shouldRender(Pokemon pokemon) {
        return true;
    }

    public abstract void render(int mouseX, int mouseY, int moduleX, int moduleY, PoseStack matrices, GuiGraphics context, Pokemon pokemon);
}