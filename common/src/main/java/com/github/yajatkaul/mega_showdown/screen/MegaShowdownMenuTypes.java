package com.github.yajatkaul.mega_showdown.screen;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.screen.custom.ZygardeCubesScreenHandler;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class MegaShowdownMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(MegaShowdown.MOD_ID, Registries.MENU);

    private static <T extends AbstractContainerMenu> RegistrySupplier<MenuType<T>> registerMenuType(
            String name,
            MenuType.MenuSupplier<T> factory
    ) {
        return MENUS.register(name, () -> new MenuType<T>(factory, FeatureFlags.DEFAULT_FLAGS));
    }

    public static void register() {
        MENUS.register();
    }    public static final RegistrySupplier<MenuType<ZygardeCubesScreenHandler>> ZYGARDE_CUBE_MENU =
            registerMenuType("zygade_menu", ZygardeCubesScreenHandler::new);




}
