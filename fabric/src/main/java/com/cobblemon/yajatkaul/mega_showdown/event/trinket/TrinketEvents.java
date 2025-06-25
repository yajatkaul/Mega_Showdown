package com.cobblemon.yajatkaul.mega_showdown.event.trinket;

import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;

public class TrinketEvents implements Trinket {

    public static void register() {
        TrinketsApi.registerTrinket(TeraMoves.TERA_ORB, new TrinketEventsHandler());
    }
}
