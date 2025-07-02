package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon;

import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.UltraBurstEventStart;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers.UltraBurstEventHandler;


public class UltraBurstEvent {
    public static void register() {
        UltraBurstEventStart.EVENT.register(UltraBurstEventHandler::ultraBurstHandler);
    }
}