package com.cobblemon.yajatkaul.mega_showdown.config.structure.abstracts;

import net.minecraft.network.FriendlyByteBuf;

public class Effects {
    public String particle_apply;
    public String particle_revert;
    public String sound_apply;
    public String sound_revert;
    public Integer particle_apply_amplifier;
    public Integer particle_revert_amplifier;

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(particle_apply != null ? particle_apply : "");
        buf.writeUtf(particle_revert != null ? particle_revert : "");
        buf.writeUtf(sound_apply != null ? sound_apply : "");
        buf.writeUtf(sound_revert != null ? sound_revert : "");
        buf.writeInt(particle_apply_amplifier != null ? particle_apply_amplifier : 1);
        buf.writeInt(particle_revert_amplifier != null ? particle_revert_amplifier : 1);
    }

    public static Effects read(FriendlyByteBuf buf) {
        Effects effects = new Effects();
        effects.particle_apply = buf.readUtf();
        effects.particle_revert = buf.readUtf();
        effects.sound_apply = buf.readUtf();
        effects.sound_revert = buf.readUtf();
        effects.particle_apply_amplifier = buf.readInt();
        effects.particle_revert_amplifier = buf.readInt();
        return effects;
    }
}

