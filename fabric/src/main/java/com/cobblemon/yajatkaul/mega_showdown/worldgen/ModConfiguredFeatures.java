package com.cobblemon.yajatkaul.mega_showdown.worldgen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> MAX_MUSHROOM_KEY = registerKey("max_mushroom");
    public static final RegistryKey<ConfiguredFeature<?,?>> GRACIDEA_FLOWER_KEY = registerKey("gracidea_flower");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {

        register(context, MAX_MUSHROOM_KEY, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.MAX_MUSHROOM
                                .getDefaultState().with(SweetBerryBushBlock.AGE, 3))),
                        List.of(Blocks.MOSS_BLOCK)));

//        register(context, GRACIDEA_FLOWER_KEY, Feature.RANDOM_PATCH,
//                new RandomPatchFeatureConfig(
//                        3,
//                        1,
//                        2,
//                        PlacedFeatures.createEntry(
//                                Feature.SIMPLE_BLOCK,
//                                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.GRACIDEA_FLOWER.getDefaultState()))
//                        )
//                ));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MegaShowdown.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
