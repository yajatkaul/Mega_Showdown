package com.cobblemon.yajatkaul.mega_showdown.sound;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent DYNAMAX = registerSoundEvent("dynamaxing");
    public static final SoundEvent TERASTALLIZATION = registerSoundEvent("terastallization");
    public static final SoundEvent MEGA = registerSoundEvent("mega");
    public static final SoundEvent ZMOVE = registerSoundEvent("zmove");
    public static final SoundEvent FORM_CHANGE_BASIC = registerSoundEvent("form_change_basic");
    public static final SoundEvent POWER_CONSTRUCT = registerSoundEvent("power-construct");
    public static final SoundEvent ORIGIN_FORM = registerSoundEvent("origin_form");
    public static final SoundEvent GIRATINIA_FORM = registerSoundEvent("giratina");
    public static final SoundEvent KYUREM_FUSION = registerSoundEvent("kyurem_fusion");
    public static final SoundEvent ARCEUS_MULTITYPE = registerSoundEvent("arceus_multitype");
    public static final SoundEvent TERAPAGOS_SPAWN = registerSoundEvent("terapagos_spawn");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(MegaShowdown.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {

    }
}
