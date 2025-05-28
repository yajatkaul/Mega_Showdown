package com.cobblemon.yajatkaul.mega_showdown.mixin.UI;

import com.cobblemon.mod.common.client.CobblemonResources;
import com.cobblemon.mod.common.client.gui.summary.widgets.screens.stats.StatWidget;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cobblemon.mod.common.api.gui.GuiUtilsKt.blitk;
import static com.cobblemon.mod.common.util.LocalizationUtilsKt.lang;
import static com.cobblemon.mod.common.util.MiscUtilsKt.cobblemonResource;
import static com.cobblemon.yajatkaul.mega_showdown.screen.GimmikInfoKt.gimmikBar;
import static com.cobblemon.yajatkaul.mega_showdown.screen.GimmikInfoKt.gimmikText;

@Mixin(value = StatWidget.class, remap = false)
public abstract class StatWidgetMixin {

    @Shadow
    @Final
    public static float SCALE;

    @Inject(
            method = "drawFriendship",
            at = @At("RETURN")
    )
    private void injectCustomWidget(int moduleX, int moduleY, MatrixStack matrices, DrawContext context, int friendship, CallbackInfo ci) {
        StatWidget self = (StatWidget) (Object) this;
        int barWidth = 10 * self.getPokemon().getDmaxLevel();
        if (self.getPokemon().getDmaxLevel() == 10) {
            barWidth += 10;
        }
        int yLevel = moduleY + 30;

        if (self.getStatTabIndex() == 3) { // "OTHER" tab
            if (self.getPokemon().getSpecies().getName().equals("Gimmighoul")) {
                yLevel += 60;
            }

            blitk(
                    matrices,
                    cobblemonResource("textures/gui/summary/summary_stats_other_bar.png"),
                    moduleX,
                    yLevel,
                    28,
                    124
            );

            gimmikText(
                    context,
                    CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                    moduleX + 62,
                    yLevel + 2.0f,
                    lang("ui.stats.dynamax_level").formatted(Formatting.BOLD),
                    true,
                    true
            );

            gimmikText(
                    context,
                    moduleX + 11,
                    yLevel + 5.5f,
                    Text.literal(String.valueOf(self.getPokemon().getDmaxLevel())),
                    SCALE,
                    true
            );

            String maxFeature;
            if (self.getPokemon().getGmaxFactor()) {
                maxFeature = "gmax";
            } else {
                maxFeature = "dmax";
            }

            gimmikText(
                    context,
                    moduleX + 113,
                    yLevel + 5.5f,
                    Text.translatable("cobblemon.ui.stats." + maxFeature),
                    SCALE,
                    true
            );

            float level = self.getPokemon().getDmaxLevel() / 10f; // 0.0 to 1.0

            float red = 0.5f - (0.1f * level);
            float green = 0.05f * level;
            float blue = 0.1f * level;

            gimmikBar(
                    matrices,
                    CobblemonResources.INSTANCE.getWHITE(),
                    moduleX + 8,
                    yLevel + 17,
                    8,
                    barWidth,
                    red,
                    green,
                    blue
            );

            blitk(
                    matrices,
                    Identifier.of(MegaShowdown.MOD_ID, "textures/gui/stats/level_ind.png"),
                    moduleX,
                    (yLevel + 16),
                    10,
                    124
            );
        }
    }
}