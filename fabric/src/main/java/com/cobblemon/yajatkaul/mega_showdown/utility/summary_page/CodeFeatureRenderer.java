package com.cobblemon.yajatkaul.mega_showdown.utility.summary_page;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public abstract class CodeFeatureRenderer {
    public boolean shouldRender(Pokemon pokemon) {
        return true;
    }

    public abstract void render(int mouseX, int mouseY, int moduleX, int moduleY, MatrixStack matrices, DrawContext context, Pokemon pokemon);
}