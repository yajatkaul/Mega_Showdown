package com.cobblemon.yajatkaul.mega_showdown.sound;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MegaShowdown.MOD_ID);

    public static final Supplier<SoundEvent> DYNAMAX = registerSoundEvent("dynamaxing");
    public static final Supplier<SoundEvent> TERASTALLIZATION = registerSoundEvent("terastallization");

    private static Supplier<SoundEvent> registerSoundEvent(String name){
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static final DeferredSoundType BATTLE_SOUNDS_DMAX = new DeferredSoundType(
            1.0F, // volume
            1.0F, // pitch
            DYNAMAX, // breakSound
            DYNAMAX, // stepSound
            DYNAMAX, // placeSound
            DYNAMAX, // hitSound
            DYNAMAX  // fallSound
    );

    public static final DeferredSoundType BATTLE_SOUNDS_TERA = new DeferredSoundType(
            1.0F,
            1.0F,
            TERASTALLIZATION,
            TERASTALLIZATION,
            TERASTALLIZATION,
            TERASTALLIZATION,
            TERASTALLIZATION
    );

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
