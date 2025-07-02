package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon;

import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.DynamaxEventEnd;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.DynamaxEventStart;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers.DynamaxEventHandler;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.DynamaxUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;


public class DynamaxEvent {
    public static void register() {
        DynamaxEventStart.EVENT.register(DynamaxEventHandler::dynamaxStartHandler);

        DynamaxEventEnd.EVENT.register(DynamaxEventHandler::dynamaxEndHandler);

        ServerTickEvents.END_SERVER_TICK.register(DynamaxUtils::updateScalingAnimations);
    }
}