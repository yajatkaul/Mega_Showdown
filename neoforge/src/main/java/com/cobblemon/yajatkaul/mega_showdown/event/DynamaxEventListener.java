package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.neoforged.bus.api.SubscribeEvent;

public class DynamaxEventListener {
    @SubscribeEvent
    public void onDynamax(DynamaxEvent event) {
        MegaShowdown.LOGGER.info("Dynamaxed");
    }
}
