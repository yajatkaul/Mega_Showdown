package com.github.yajatkaul.mega_showdown.sound;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class MegaShowdownSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(MegaShowdown.MOD_ID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> DYNAMAX = registerSoundEvent("dynamaxing");
    public static final RegistrySupplier<SoundEvent> TERASTALLIZATION = registerSoundEvent("terastallization");
    public static final RegistrySupplier<SoundEvent> MEGA = registerSoundEvent("mega");
    public static final RegistrySupplier<SoundEvent> Z_MOVE = registerSoundEvent("z_move");
    public static final RegistrySupplier<SoundEvent> FORM_CHANGE_BASIC = registerSoundEvent("form_change_basic");
    public static final RegistrySupplier<SoundEvent> POWER_CONSTRUCT = registerSoundEvent("power_construct");
    public static final RegistrySupplier<SoundEvent> ORIGIN_FORM = registerSoundEvent("origin_form");
    public static final RegistrySupplier<SoundEvent> ARCEUS_MULTITYPE = registerSoundEvent("arceus_multitype");
    public static final RegistrySupplier<SoundEvent> KYUREM_FUSION = registerSoundEvent("kyurem_fusion");
    public static final RegistrySupplier<SoundEvent> GIRATINIA_FORM = registerSoundEvent("giratina");
    public static final RegistrySupplier<SoundEvent> TERAPAGOS_SPAWN = registerSoundEvent("terapagos_spawn");

    private static RegistrySupplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register() {
        SOUND_EVENTS.register();
    }
}
