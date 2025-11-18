package com.github.yajatkaul.mega_showdown.neoforge.worldgen;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_MAX_MUSHROOM = registerKey("add_max_mushroom");
    public static final ResourceKey<BiomeModifier> ADD_GRACIDEA_FLOWER = registerKey("add_gracidea_flower");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        // CF -> PF -> BM
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_MAX_MUSHROOM, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.LUSH_CAVES)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.MAX_MUSHROOM_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

//        context.register(ADD_GRACIDEA_FLOWER, new BiomeModifiers.AddFeaturesBiomeModifier(
//                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS)),
//                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GRACIDEA_FLOWER_PLACED_KEY)),
//                GenerationStep.Decoration.VEGETAL_DECORATION
//        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, name));
    }
}