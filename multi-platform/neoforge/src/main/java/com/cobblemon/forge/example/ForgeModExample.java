package com.cobblemon.forge.example;

import com.cobblemon.yajatkaul.megamons.ExampleCommandRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod("cobblemon_multiplatform_mdk")
public class ForgeModExample {

    public ForgeModExample() {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommandRegistration(RegisterCommandsEvent event) {
        ExampleCommandRegistry.registerCommands(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }
}
