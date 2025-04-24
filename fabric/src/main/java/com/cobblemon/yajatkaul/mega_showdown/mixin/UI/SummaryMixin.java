package com.cobblemon.yajatkaul.mega_showdown.mixin.UI;

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

@Mixin(value = Summary.class, remap = false)
public abstract class SummaryMixin {
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"
            ),
            remap = false
    )
    private void displayIcons(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Summary summary = (Summary) (Object) this;
        final float SCALE = PokedexGUIConstants.SCALE;
        int x = (summary.width - Summary.BASE_WIDTH) / 2;
        int y = (summary.height - Summary.BASE_HEIGHT) / 2;
        MatrixStack matrices = context.getMatrices();

        Pokemon pokemon = summary.getSelectedPokemon$common();
        gimmikInfo(
                matrices,
                Identifier.of(MegaShowdown.MOD_ID, "textures/gui/summary/tera_types/"+ pokemon.getTeraType().getDisplayName().getString().toLowerCase() + ".png"),
                (x + 58) / SCALE,
                (y + 84) / SCALE,
                32,
                30,
                SCALE
        );

        if(pokemon.getSpecies().canGmax()){
            gimmikInfo(
                    matrices,
                    Identifier.of(MegaShowdown.MOD_ID, "textures/gui/summary/gmax.png"),
                    (x + 6) / SCALE,
                    (y + 31.5) / SCALE,
                    24,
                    24,
                    SCALE
            );
        }
    }
}
