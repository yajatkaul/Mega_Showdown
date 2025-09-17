package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.gui.GuiUtilsKt;
import com.cobblemon.mod.common.api.pokemon.feature.SynchronizedSpeciesFeature;
import com.cobblemon.mod.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer;
import com.cobblemon.mod.common.client.gui.summary.widgets.SoundlessWidget;
import com.cobblemon.mod.common.client.gui.summary.widgets.screens.stats.StatWidget;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.utility.summary_page.CodeFeatureRenderer;
import com.cobblemon.yajatkaul.mega_showdown.utility.summary_page.CodeFeatureRenderers;
import com.cobblemon.yajatkaul.mega_showdown.utility.summary_page.SummaryPageButtonTextures;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(StatWidget.class)
public abstract class StatWidgetMixin extends SoundlessWidget {
    public StatWidgetMixin(int pX, int pY, int pWidth, int pHeight, @NotNull Text component) {
        super(pX, pY, pWidth, pHeight, component);
    }

    @Shadow(remap = false)
    public abstract Pokemon getPokemon();

    @Shadow(remap = false)
    public abstract List<SummarySpeciesFeatureRenderer<SynchronizedSpeciesFeature>> getRenderableFeatures();

    @Shadow
    protected abstract void drawFriendship(int moduleX, int moduleY, MatrixStack matrices, DrawContext context, int friendship);

    @Shadow
    protected abstract void drawFullness(int moduleX, int moduleY, MatrixStack matrices, DrawContext context, Pokemon pokemon);

    @Unique
    private int index = 0;

    @Unique
    private static final int BUILTIN_RENDERERS = 2;

    @Inject(
            method = "renderWidget",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/stats/StatWidget;drawFriendship(IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/gui/DrawContext;I)V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void renderFeaturePages(DrawContext context, int pMouseX, int pMouseY, float pPartialTicks, CallbackInfo info, @Local(name = "drawY") int drawY, @Local(name = "matrices") MatrixStack matrices) {
        int moduleX = this.getX() + 5;

        for (int i = index; i < index + 4; ++i) {
            if (i == 0) {
                this.drawFriendship(moduleX, drawY, matrices, context, this.getPokemon().getFriendship());
            } else if (i == 1) {
                this.drawFullness(moduleX, drawY, matrices, context, this.getPokemon());
            } else {
                CodeFeatureRenderer codeRenderer = CodeFeatureRenderers.get(i - BUILTIN_RENDERERS, this.getPokemon());
                if (codeRenderer != null) {
                    codeRenderer.render(pMouseX, pMouseY, moduleX, drawY, matrices, context, this.getPokemon());
                } else {
                    int dataRendererIndex = i - BUILTIN_RENDERERS - CodeFeatureRenderers.size(this.getPokemon());
                    List<SummarySpeciesFeatureRenderer<SynchronizedSpeciesFeature>> canRender = this.getValidFeatureRenders();

                    if (dataRendererIndex >= 0 && dataRendererIndex < canRender.size()) {
                        canRender.get(dataRendererIndex).render(context, moduleX, drawY, this.getPokemon());
                    }
                }
            }
            drawY += 30;
        }

        if (this.renderPrevButton()) {
            GuiUtilsKt.blitk(
                    matrices,
                    this.isHoveringPrev(pMouseX, pMouseY) ? SummaryPageButtonTextures.PREV_PAGE_HOVER : SummaryPageButtonTextures.PREV_PAGE,
                    this.getX() + 4,
                    this.getY() + 140,
                    SummaryPageButtonTextures.DIMENSIONS,
                    SummaryPageButtonTextures.DIMENSIONS
            );
        }
        if (this.renderNextButton()) {
            GuiUtilsKt.blitk(
                    matrices,
                    this.isHoveringNext(pMouseX, pMouseY) ? SummaryPageButtonTextures.NEXT_PAGE_HOVER : SummaryPageButtonTextures.NEXT_PAGE,
                    this.getX() + 124,
                    this.getY() + 140,
                    SummaryPageButtonTextures.DIMENSIONS,
                    SummaryPageButtonTextures.DIMENSIONS
            );
        }

        info.cancel();
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void clickButtons(double pMouseX, double pMouseY, int pButton, CallbackInfoReturnable<Boolean> cir) {
        if (this.renderPrevButton() && this.isHoveringPrev((int) pMouseX, (int) pMouseY)) {
            this.index -= 4;
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(CobblemonSounds.PC_CLICK, 1f));
        } else if (this.renderNextButton() && this.isHoveringNext((int) pMouseX, (int) pMouseY)) {
            this.index += 4;
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(CobblemonSounds.PC_CLICK, 1f));
        }
    }

    @Unique
    private boolean renderPrevButton() {
        return this.index > 3;
    }

    @Unique
    private boolean renderNextButton() {
        return this.index + 4 < BUILTIN_RENDERERS + CodeFeatureRenderers.size(this.getPokemon()) + this.getValidFeatureRenders().size();
    }

    @Unique
    private boolean isHoveringPrev(int mouseX, int mouseY) {
        return mouseX > this.getX() + 4
                && mouseX < this.getX() + 4 + SummaryPageButtonTextures.DIMENSIONS
                && mouseY > this.getY() + 140
                && mouseY < this.getY() + 140 + SummaryPageButtonTextures.DIMENSIONS;
    }

    @Unique
    private boolean isHoveringNext(int mouseX, int mouseY) {
        return mouseX > this.getX() + 124
                && mouseX < this.getX() + 124 + SummaryPageButtonTextures.DIMENSIONS
                && mouseY > this.getY() + 140
                && mouseY < this.getY() + 140 + SummaryPageButtonTextures.DIMENSIONS;
    }

    @Unique
    private List<SummarySpeciesFeatureRenderer<SynchronizedSpeciesFeature>> getValidFeatureRenders() {
        return this.getRenderableFeatures()
                .stream()
                .filter(renderer -> this.getPokemon().getFeature(renderer.getName()) != null)
                .toList();
    }
}