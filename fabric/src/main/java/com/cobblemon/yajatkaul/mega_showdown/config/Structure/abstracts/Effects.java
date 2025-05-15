package com.cobblemon.yajatkaul.mega_showdown.config.structure.abstracts;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class Effects {
    public String particle_apply;
    public String particle_revert;
    public String sound_apply;
    public String sound_revert;
    public Integer particle_apply_amplifier;
    public Integer particle_revert_amplifier;

    public static final PacketCodec<RegistryByteBuf, Effects> CODEC = PacketCodec.of(
            Effects::writeToBuf,
            Effects::readFromBuf
    );

    public void writeToBuf(RegistryByteBuf buf) {
        PacketCodecs.STRING.encode(buf, particle_apply == null ? "" : particle_apply);
        PacketCodecs.STRING.encode(buf, particle_revert == null ? "" : particle_revert);
        PacketCodecs.STRING.encode(buf, sound_apply == null ? "" : sound_apply);
        PacketCodecs.STRING.encode(buf, sound_revert == null ? "" : sound_revert);
        PacketCodecs.INTEGER.encode(buf, particle_apply_amplifier == null ? 1 : particle_apply_amplifier);
        PacketCodecs.INTEGER.encode(buf, particle_revert_amplifier == null ? 1 : particle_revert_amplifier);
    }

    public static Effects readFromBuf(RegistryByteBuf buf) {
        Effects effects = new Effects();
        effects.particle_apply = PacketCodecs.STRING.decode(buf);
        effects.particle_revert = PacketCodecs.STRING.decode(buf);
        effects.sound_apply = PacketCodecs.STRING.decode(buf);
        effects.sound_revert = PacketCodecs.STRING.decode(buf);
        effects.particle_apply_amplifier = PacketCodecs.INTEGER.decode(buf);
        effects.particle_revert_amplifier = PacketCodecs.INTEGER.decode(buf);
        return effects;
    }
}
