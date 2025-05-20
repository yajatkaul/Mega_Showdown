package com.cobblemon.yajatkaul.mega_showdown.sound;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent DYNAMAX = registerSoundEvent("dynamaxing");
    public static final SoundEvent TERASTALLIZATION = registerSoundEvent("terastallization");
    public static final SoundEvent MEGA = registerSoundEvent("mega");
    public static final SoundEvent ZMOVE = registerSoundEvent("zmove");

    public static final BlockSoundGroup BATTLE_SOUNDS_DMAX = new BlockSoundGroup(0.7F, 1F,
            DYNAMAX, DYNAMAX, DYNAMAX, DYNAMAX, DYNAMAX);
    public static final BlockSoundGroup BATTLE_SOUNDS_TERA = new BlockSoundGroup(1.0F, 1.0F,
            TERASTALLIZATION, TERASTALLIZATION, TERASTALLIZATION, TERASTALLIZATION, TERASTALLIZATION);
    public static final BlockSoundGroup BATTLE_SOUNDS_MEGA = new BlockSoundGroup(1.0F, 1.0F,
            MEGA, MEGA, MEGA, MEGA, MEGA);
    public static final BlockSoundGroup BATTLE_SOUNDS_ZMOVE = new BlockSoundGroup(1.0F, 1.0F,
            ZMOVE, ZMOVE, ZMOVE, ZMOVE, ZMOVE);


    private static SoundEvent registerSoundEvent(String name){
        Identifier id = Identifier.of(MegaShowdown.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds(){

    }
}
