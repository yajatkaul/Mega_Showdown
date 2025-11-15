package com.github.yajatkaul.mega_showdown.status;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.status.custom.DynamaxStatusEffect;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;

public class MegaShowdownStatusEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(MegaShowdown.MOD_ID, Registries.MOB_EFFECT);

    public static final RegistrySupplier<MobEffect> DYNAMAX =
            register("dynamax", DynamaxStatusEffect::new);

    private static RegistrySupplier<MobEffect> register(String name, Supplier<MobEffect> effect) {
        return MOB_EFFECTS.register(name, effect);
    }

    public static void register() {
        MOB_EFFECTS.register();
    }
}
