package com.github.yajatkaul.mega_showdown.creative;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MegaShowdownTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(MegaShowdown.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> MEGA_TAB = CREATIVE_TABS.register(
            "mega_tab",
            () -> CreativeTabRegistry.create(
                    Component.translatable("creativeTab.mega_showdown.mega_tab"),
                    () -> new ItemStack(MegaShowdownItems.KEYSTONE) // Icon
            )
    );

    public static final RegistrySupplier<CreativeModeTab> TERA_TAB = CREATIVE_TABS.register(
            "tera_tab",
            () -> CreativeTabRegistry.create(
                    Component.translatable("creativeTab.mega_showdown.tera_tab"),
                    () -> new ItemStack(MegaShowdownItems.KEYSTONE) // Icon
            )
    );

    public static final RegistrySupplier<CreativeModeTab> Z_TAB = CREATIVE_TABS.register(
            "z_tab",
            () -> CreativeTabRegistry.create(
                    Component.translatable("creativeTab.mega_showdown.z_tab"),
                    () -> new ItemStack(MegaShowdownItems.KEYSTONE) // Icon
            )
    );

    public static final RegistrySupplier<CreativeModeTab> FORM_TAB = CREATIVE_TABS.register(
            "form_tab",
            () -> CreativeTabRegistry.create(
                    Component.translatable("creativeTab.mega_showdown.forms_tab"),
                    () -> new ItemStack(MegaShowdownItems.BLUE_ORB) // Icon
            )
    );

    public static void register() {
        CREATIVE_TABS.register();
    }
}
