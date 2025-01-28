package com.cobblemon.fabric.example;

import com.cobblemon.yajatkaul.megamons.ExampleCommandRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class FabricModExample implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(ExampleCommandRegistry::registerCommands);
    }

}
