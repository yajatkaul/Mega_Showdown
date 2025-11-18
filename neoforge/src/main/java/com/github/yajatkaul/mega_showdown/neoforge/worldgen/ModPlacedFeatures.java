package com.github.yajatkaul.mega_showdown.neoforge.worldgen;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> MAX_MUSHROOM_PLACED_KEY = registerKey("max_mushroom_placed_key");
    public static final ResourceKey<PlacedFeature> GRACIDEA_FLOWER_PLACED_KEY = registerKey("gracidea_flower_placed_key");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);


        register(context, MAX_MUSHROOM_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MAX_MUSHROOM_KEY),
                List.of(CountPlacement.of(3),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-59), VerticalAnchor.absolute(64)),
                        BiomeFilter.biome()));

//        register(context, GRACIDEA_FLOWER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.GRACIDEA_FLOWER_KEY),
//                List.of(RarityFilter.onAverageOnceEvery(900),
//                        CountPlacement.of(1),
//                        InSquarePlacement.spread(),
//                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
//                        BiomeFilter.biome()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}