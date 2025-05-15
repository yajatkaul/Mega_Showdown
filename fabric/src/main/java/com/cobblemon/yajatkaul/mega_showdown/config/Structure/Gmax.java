package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class Gmax {
    public String pokemon;

    public Gmax() {
    }

    public static final PacketCodec<RegistryByteBuf, Gmax> CODEC = PacketCodec.of(
            Gmax::writeToBuf,
            Gmax::readFromBuf
    );

    public void writeToBuf(RegistryByteBuf buf) {
        PacketCodecs.STRING.encode(buf, pokemon);
    }

    public static Gmax readFromBuf(RegistryByteBuf buf) {
        Gmax gmax = new Gmax();
        gmax.pokemon = PacketCodecs.STRING.decode(buf);
        return gmax;
    }
}