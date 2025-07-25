package com.cobblemon.yajatkaul.mega_showdown.mixin.client.ui;

import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.pokedex.PokedexGUIConstants;
import com.cobblemon.mod.common.client.gui.summary.Summary;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
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
    private void displayIcons(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        PCGUI pcgui = (PCGUI) (Object) this;
        final float SCALE = PokedexGUIConstants.SCALE;
        int x = (pcgui.width - Summary.BASE_WIDTH) / 2;
        int y = (pcgui.height - Summary.BASE_HEIGHT) / 2;
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(0.0, 0.0, 2000.0);

        Pokemon pokemon = pcgui.getPreviewPokemon$common();

        if (pokemon != null) {
            gimmikInfo(
                    matrices,
                    Identifier.of(MegaShowdown.MOD_ID, "textures/gui/summary/tera_types/" + pokemon.getTeraType().showdownId() + ".png"),
                    (x + 48.5) / SCALE,
                    (y + 57) / SCALE,
                    32,
                    30,
                    SCALE
            );

            if (pokemon.getGmaxFactor()) {
                gimmikInfo(
                        matrices,
                        Identifier.of(MegaShowdown.MOD_ID, "textures/gui/summary/gmax.png"),
                        (x - 3) / SCALE,
                        (y + 5) / SCALE,
                        24,
                        24,
                        SCALE
                );
            }
        }

        matrices.pop();
    }
}
