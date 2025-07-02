package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon;

import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers.DynamaxEventHandler;
import net.neoforged.neoforge.common.NeoForge;


public class DynamaxEvent {
    public static void register() {
        NeoForge.EVENT_BUS.register(new DynamaxEventHandler());
    }
}