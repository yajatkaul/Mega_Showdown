package com.cobblemon.yajatkaul.mega_showdown.worldgen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> MAX_MUSHROOM_PLACED_KEY = registerKey("max_mushroom_placed");
    public static final RegistryKey<PlacedFeature> GRACIDEA_FLOWER_PLACED_KEY = registerKey("gracidea_flower_placed_key");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, MAX_MUSHROOM_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MAX_MUSHROOM_KEY),
                CountPlacementModifier.of(8),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.fixed(-59), YOffset.fixed(64)),
                BiomePlacementModifier.of());

        register(context, GRACIDEA_FLOWER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.GRACIDEA_FLOWER_KEY),
                RarityFilterPlacementModifier.of(900),
                CountPlacementModifier.of(1),
                SquarePlacementModifier.of(),
                HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE),
                BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MegaShowdown.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
