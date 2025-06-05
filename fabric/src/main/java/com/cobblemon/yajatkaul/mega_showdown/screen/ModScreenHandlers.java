package com.cobblemon.yajatkaul.mega_showdown.screen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.screen.custom.ZygardeCubeScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static void registerScreenHandlers() {

    }

    public static final ScreenHandlerType<ZygardeCubeScreenHandler> ZYGARDE_CUBE_SCREEN_HANDLER_TYPE =
            Registry.register(Registries.SCREEN_HANDLER,
                    Identifier.of(MegaShowdown.MOD_ID, "zygarde_cube_screen_handler"),
                    new ScreenHandlerType<>(ZygardeCubeScreenHandler::new, FeatureSet.empty()));


}