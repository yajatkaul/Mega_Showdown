package com.github.yajatkaul.mega_showdown.neoforge;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlocks;
import com.github.yajatkaul.mega_showdown.cobblemon.features.GlobalFeatureManager;
import com.github.yajatkaul.mega_showdown.datapack.MegaShowdownDatapackRegister;
import com.github.yajatkaul.mega_showdown.gimmick.MaxGimmick;
import com.github.yajatkaul.mega_showdown.neoforge.datapack.DatapackRegistry;
import kotlin.Unit;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@Mod(MegaShowdown.MOD_ID)
public final class MegaShowdownNeoForge {
    public MegaShowdownNeoForge(IEventBus modEventBus) {
        GlobalFeatureManager.registerEarly();
        MegaShowdown.init();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::datapackRegistryEvent);

        NeoForge.EVENT_BUS.register(this);
    }

    private void datapackRegistryEvent(DataPackRegistryEvent.NewRegistry event) {
        DatapackRegistry.register(event);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                MegaShowdownBlocks.GRACIDEA_FLOWER.getId(),
                MegaShowdownBlocks.POTTED_GRACIDEA
        ));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartedEvent event) {
        Cobblemon.INSTANCE.getShowdownThread().queue(showdownService -> {
            if (showdownService instanceof GraalShowdownService service) {
                Value receiveMoveDataFn = service.context.getBindings("js").getMember("receiveCustomGmaxMove");
                for (MaxGimmick gmax : MegaShowdownDatapackRegister.GMAX_REGISTRY) {
                    receiveMoveDataFn.execute(gmax.pokemonShowdownId(), gmax.gmaxMove());
                }
            }
            return Unit.INSTANCE;
        });
    }
}
