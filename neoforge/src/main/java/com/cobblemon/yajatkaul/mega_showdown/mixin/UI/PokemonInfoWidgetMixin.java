package com.cobblemon.yajatkaul.mega_showdown.mixin.UI;

import com.cobblemon.mod.common.api.pokedex.entry.PokedexEntry;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.client.gui.pokedex.PokedexGUIConstants;
import com.cobblemon.mod.common.client.gui.pokedex.widgets.PokemonInfoWidget;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cobblemon.yajatkaul.mega_showdown.screen.GimmikInfoKt.gimmikInfo;

@Mixin(value = PokemonInfoWidget.class, remap = false)
public abstract class PokemonInfoWidgetMixin {
    @Inject(
            method = "renderWidget", at = @At(
            value = "INVOKE",
            target = "Lcom/cobblemon/mod/common/client/gui/pokedex/ScaledButton;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
            ordinal = 6, shift = At.Shift.AFTER
    ),
            remap = false
    )
    private void displayGmaxIcon(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        PoseStack matrices = context.pose();
        PokemonInfoWidget pokeInfo = (PokemonInfoWidget) (Object) this;
        final float SCALE = PokedexGUIConstants.SCALE;

        PokedexEntry entry = pokeInfo.getCurrentEntry();
        if (entry != null) {
            Species species = PokemonSpecies.INSTANCE.getByIdentifier(entry.getSpeciesId());
            if (species != null && !pokeInfo.getVisibleForms().isEmpty()) {
                if(species.canGmax()){
                    gimmikInfo(
                            matrices,
                            ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/summary/gmax.png"),
                            (pokeInfo.getPX() + 112) / SCALE,
                            (pokeInfo.getPY() + 25) / SCALE,
                            29,
                            28,
                            SCALE
                    );
                }
            }
        }
    }
}
