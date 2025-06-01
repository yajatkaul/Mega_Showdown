package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.yajatkaul.mega_showdown.event.trinkets.TeraOrbEvent;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;

public class TrinketEvent implements Trinket {

    public static void register() {
        TrinketsApi.registerTrinket(TeraMoves.TERA_ORB, new TeraOrbEvent());
    }
}
