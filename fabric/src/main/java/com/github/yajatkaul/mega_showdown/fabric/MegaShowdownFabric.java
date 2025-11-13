package com.github.yajatkaul.mega_showdown.fabric;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.datapack.MegaShowdownDatapackRegister;
import com.github.yajatkaul.mega_showdown.fabric.datapack.DatapackRegistry;
import com.github.yajatkaul.mega_showdown.gimmick.MaxGimmick;
import kotlin.Unit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public final class MegaShowdownFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MegaShowdown.init();
        DatapackRegistry.register();
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
}
