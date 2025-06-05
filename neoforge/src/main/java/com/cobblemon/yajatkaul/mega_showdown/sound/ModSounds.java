package com.cobblemon.yajatkaul.mega_showdown.sound;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MegaShowdown.MOD_ID);

    public static final Supplier<SoundEvent> DYNAMAX = registerSoundEvent("dynamaxing");
    public static final Supplier<SoundEvent> TERASTALLIZATION = registerSoundEvent("terastallization");
    public static final Supplier<SoundEvent> MEGA = registerSoundEvent("mega");
    public static final Supplier<SoundEvent> ZMOVE = registerSoundEvent("zmove");
    public static final Supplier<SoundEvent> FORM_CHANGE_BASIC = registerSoundEvent("form_change_basic");
    public static final Supplier<SoundEvent> POWER_CONSTRUCT = registerSoundEvent("power-construct");
    public static final Supplier<SoundEvent> ORIGIN_FORM = registerSoundEvent("origin_form");
    public static final Supplier<SoundEvent> ARCEUS_MULTITYPE = registerSoundEvent("arceus_multitype");
    public static final Supplier<SoundEvent> KYUREM_FUSION = registerSoundEvent("kyurem_fusion");
    public static final Supplier<SoundEvent> GIRATINIA_FORM = registerSoundEvent("giratina");
    public static final Supplier<SoundEvent> TERAPAGOS_SPAWN = registerSoundEvent("terapagos_spawn");

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
