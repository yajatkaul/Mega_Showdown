package com.cobblemon.yajatkaul.mega_showdown.mixin.UI;

import com.cobblemon.mod.common.api.pokedex.entry.PokedexEntry;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.client.gui.pokedex.PokedexGUIConstants;
import com.cobblemon.mod.common.client.gui.pokedex.widgets.PokemonInfoWidget;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cobblemon.yajatkaul.mega_showdown.screen.GimmikInfoKt.gimmikInfo;

@Mixin(value = PokemonInfoWidget.class)
public abstract class PokemonInfoWidgetMixin {
    @Inject(
            method = "renderWidget",
            at = @At("RETURN")
    )
    private void displayGmaxIcon(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        MatrixStack matrices = context.getMatrices();
        PokemonInfoWidget pokeInfo = (PokemonInfoWidget) (Object) this;
        final float SCALE = PokedexGUIConstants.SCALE;

        PokedexEntry entry = pokeInfo.getCurrentEntry();
        if (entry != null) {
            Species species = PokemonSpecies.INSTANCE.getByIdentifier(entry.getSpeciesId());
            if (species != null && !pokeInfo.getVisibleForms().isEmpty()) {
                if(species.canGmax()){
                    gimmikInfo(
                            matrices,
                            Identifier.of(MegaShowdown.MOD_ID, "textures/gui/summary/gmax.png"),
                            (pokeInfo.getPX() + 124) / SCALE,
                            (pokeInfo.getPY() + 36) / SCALE,
                            26,
                            26,
                            SCALE
                    );
                }
            }
        }
    }
}
