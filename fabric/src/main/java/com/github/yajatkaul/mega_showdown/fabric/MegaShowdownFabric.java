package com.github.yajatkaul.mega_showdown.fabric;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.command.MegaShowdownCommands;
import com.github.yajatkaul.mega_showdown.datapack.MegaShowdownDatapackRegister;
import com.github.yajatkaul.mega_showdown.fabric.datapack.DatapackRegistry;
import com.github.yajatkaul.mega_showdown.gimmick.MaxGimmick;
import kotlin.Unit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class MegaShowdownFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MegaShowdown.init();
        DatapackRegistry.register();
        generateModWorldGen();

        CommandRegistrationCallback.EVENT.register(MegaShowdownCommands::registerCommands);

        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            Cobblemon.INSTANCE.getShowdownThread().queue(showdownService -> {
                if (showdownService instanceof GraalShowdownService service) {
                    Value receiveMoveDataFn = service.context.getBindings("js").getMember("receiveCustomGmaxMove");
                    for (MaxGimmick gmax : MegaShowdownDatapackRegister.GMAX_REGISTRY) {
                        receiveMoveDataFn.execute(gmax.pokemonShowdownId(), gmax.gmaxMove());
                    }
                }
                return Unit.INSTANCE;
            });
        });
    }

    public static void generateModWorldGen() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.LUSH_CAVES),
                GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE,
                        ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "max_mushroom_placed_key"))
        );
    }
}
