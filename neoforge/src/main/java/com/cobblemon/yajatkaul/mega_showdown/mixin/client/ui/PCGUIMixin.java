package com.cobblemon.yajatkaul.mega_showdown.mixin.client.ui;

import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.pokedex.PokedexGUIConstants;
import com.cobblemon.mod.common.client.gui.summary.Summary;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cobblemon.yajatkaul.mega_showdown.screen.GimmikInfoKt.gimmikInfo;

@Mixin(value = PCGUI.class)
public class PCGUIMixin {
    @Inject(
            method = "render",
            at = @At("RETURN")
    )
    private void displayIcons(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        PCGUI pcgui = (PCGUI) (Object) this;
        final float SCALE = PokedexGUIConstants.SCALE;
        int x = (pcgui.width - Summary.BASE_WIDTH) / 2;
        int y = (pcgui.height - Summary.BASE_HEIGHT) / 2;
        PoseStack matrices = context.pose();
        matrices.pushPose();
        matrices.translate(0.0, 0.0, 2000.0);

        Pokemon pokemon = pcgui.getPreviewPokemon$common();

        if (pokemon != null) {
            gimmikInfo(
                    matrices,
                    ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/summary/tera_types/" + pokemon.getTeraType().showdownId() + ".png"),
                    (x + 48.5) / SCALE,
                    (y + 57) / SCALE,
                    32,
                    30,
                    SCALE
            );

            if (pokemon.getGmaxFactor()) {
                gimmikInfo(
                        matrices,
                        ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/summary/gmax.png"),
                        (x - 3) / SCALE,
                        (y + 5) / SCALE,
                        24,
                        24,
                        SCALE
                );
            }
        }

        matrices.popPose();
    }
}
