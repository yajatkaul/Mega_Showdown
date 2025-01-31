package com.cobblemon.yajatkaul.megamons;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.yajatkaul.megamons.block.ModBlocks;
import com.cobblemon.yajatkaul.megamons.item.ModItemGroups;
import com.cobblemon.yajatkaul.megamons.item.ModItems;
import com.cobblemon.yajatkaul.megamons.showdown.ShowdownUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MegaShowdown implements ModInitializer {
    public static final String MOD_ID = "mega_showdown";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItemGroups.registerItemGroups();
        ModItems.registerModItem();
        ModBlocks.registerModBlocks();

        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        CobblemonEvents.HELD_ITEM_POST.subscribe(Priority.NORMAL, ShowdownUtils::onHeldItemChange);
    }

    private void onServerStarted(MinecraftServer server) {
        ShowdownUtils.loadMegaStoneIds();
    }

}
