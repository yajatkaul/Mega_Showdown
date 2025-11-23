package com.github.yajatkaul.mega_showdown.cobblemon.features;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.GlobalSpeciesFeatures;
import com.cobblemon.mod.common.api.pokemon.feature.IntSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.IntSpeciesFeatureProvider;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.util.MiscUtilsKt;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DynamaxLevelHandler {
    private static final IntSpeciesFeatureProvider DynamaxLevel;
    private static final String KEY = "dynamax_level";

    static {
        DynamaxLevel = new IntSpeciesFeatureProvider();
        DynamaxLevel.setDefault(0);
        DynamaxLevel.setKeys(List.of(KEY));
        DynamaxLevel.setMin(0);
        DynamaxLevel.setMax(Cobblemon.config.getMaxDynamaxLevel());
        DynamaxLevel.setVisible(MegaShowdownConfig.dynamax);

        if (MegaShowdownConfig.dynamax) {
            IntSpeciesFeatureProvider.DisplayData display = new IntSpeciesFeatureProvider.DisplayData();
            display.setColour(new Vec3(198, 0, 0));
            display.setName("cobblemon.ui.stats.dynamax_level");
            display.setUnderlay(MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_stats_other_bar.png"));
            display.setOverlay(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/stats/dynamax_level.png"));
            DynamaxLevel.setDisplay(display);
        }
    }

    public static void register () {
        GlobalSpeciesFeatures.register(KEY, DynamaxLevel);
    }

    public static void update (Pokemon pokemon) {
        IntSpeciesFeature feature = DynamaxLevel.get(pokemon);
        if (feature == null) return;

        feature.setValue(pokemon.getDmaxLevel());
        feature.apply(pokemon);
        pokemon.markFeatureDirty(feature);
    }
}
