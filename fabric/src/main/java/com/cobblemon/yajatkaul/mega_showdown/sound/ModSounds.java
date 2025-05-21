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
    public static final SoundEvent ASH_GRENINJA = registerSoundEvent("ash_greninja");
    public static final SoundEvent POWER_CONSTRUCT = registerSoundEvent("power-construct");
    public static final SoundEvent ORIGIN_FORM = registerSoundEvent("origin_form");

    public static final BlockSoundGroup BATTLE_SOUNDS_DMAX = new BlockSoundGroup(0.7F, 1F,
            DYNAMAX, DYNAMAX, DYNAMAX, DYNAMAX, DYNAMAX);
    public static final BlockSoundGroup BATTLE_SOUNDS_TERA = new BlockSoundGroup(1.0F, 1.0F,
            TERASTALLIZATION, TERASTALLIZATION, TERASTALLIZATION, TERASTALLIZATION, TERASTALLIZATION);
    public static final BlockSoundGroup BATTLE_SOUNDS_MEGA = new BlockSoundGroup(1.0F, 1.0F,
            MEGA, MEGA, MEGA, MEGA, MEGA);
    public static final BlockSoundGroup BATTLE_SOUNDS_ZMOVE = new BlockSoundGroup(1.0F, 1.0F,
            ZMOVE, ZMOVE, ZMOVE, ZMOVE, ZMOVE);
    public static final BlockSoundGroup BATTLE_SOUNDS_ASH_GRENINJA = new BlockSoundGroup(1.0F, 1.0F,
            ASH_GRENINJA, ASH_GRENINJA, ASH_GRENINJA, ASH_GRENINJA, ASH_GRENINJA);
    public static final BlockSoundGroup BATTLE_SOUNDS_POWER_CONSTRUCT = new BlockSoundGroup(1.0F, 1.0F,
            POWER_CONSTRUCT, POWER_CONSTRUCT, POWER_CONSTRUCT, POWER_CONSTRUCT, POWER_CONSTRUCT);
    public static final BlockSoundGroup SOUNDS_ORIGIN_FORM = new BlockSoundGroup(1.0F, 1.0F,
            ORIGIN_FORM, ORIGIN_FORM, ORIGIN_FORM, ORIGIN_FORM, ORIGIN_FORM);

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = Identifier.of(MegaShowdown.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds(){

    }
}
