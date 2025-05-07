package com.cobblemon.yajatkaul.mega_showdown.mixin.UI;

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

@Mixin(value = Summary.class)
public abstract class SummaryMixin {
    @Inject(
            method = "render",
            at = @At("RETURN")
    )
    private void displayIcons(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Summary summary = (Summary) (Object) this;
        final float SCALE = PokedexGUIConstants.SCALE;
        int x = (summary.width - Summary.BASE_WIDTH) / 2;
        int y = (summary.height - Summary.BASE_HEIGHT) / 2;
        PoseStack matrices = context.pose();

        Pokemon pokemon = summary.getSelectedPokemon$common();

        gimmikInfo(
                matrices,
                ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/summary/tera_types/"+ pokemon.getTeraType().showdownId() + ".png"),
                (x + 58) / SCALE,
                (y + 84) / SCALE,
                32,
                30,
                SCALE
        );

        if(pokemon.getGmaxFactor()){
            gimmikInfo(
                    matrices,
                    ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/summary/gmax.png"),
                    (x + 6) / SCALE,
                    (y + 31.5) / SCALE,
                    24,
                    24,
                    SCALE
            );
        }
    }
}
