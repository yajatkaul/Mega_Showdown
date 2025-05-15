package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import net.minecraft.network.FriendlyByteBuf;

public class Gmax {
    public String pokemon;

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(pokemon);
    }

    public static Gmax read(FriendlyByteBuf buf) {
        Gmax g = new Gmax();
        g.pokemon = buf.readUtf();
        return g;
    }
}