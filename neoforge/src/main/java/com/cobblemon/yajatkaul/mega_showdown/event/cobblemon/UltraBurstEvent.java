package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon;

import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers.UltraBurstEventHandler;
import net.neoforged.neoforge.common.NeoForge;


public class UltraBurstEvent {
    public static void register() {
        NeoForge.EVENT_BUS.register(new UltraBurstEventHandler());
    }
}