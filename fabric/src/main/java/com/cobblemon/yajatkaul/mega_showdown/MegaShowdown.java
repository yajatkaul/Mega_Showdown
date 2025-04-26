package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.yajatkaul.mega_showdown.block.BlockRegister;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.entity.ModBlockEntities;
import com.cobblemon.yajatkaul.mega_showdown.commands.MegaCommands;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.creativeMenu.ModItemGroups;
import com.cobblemon.yajatkaul.mega_showdown.event.CobbleEvents;
import com.cobblemon.yajatkaul.mega_showdown.event.ModEvents;
import com.cobblemon.yajatkaul.mega_showdown.event.TrinketEvent;
import com.cobblemon.yajatkaul.mega_showdown.item.*;
import com.cobblemon.yajatkaul.mega_showdown.networking.BattleNetwork;
import com.cobblemon.yajatkaul.mega_showdown.screen.ModScreenHandlers;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.TeraTypeHelper;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import com.cobblemon.yajatkaul.mega_showdown.worldgen.ModWorldGeneration;
import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MegaShowdown implements ModInitializer {
    public static final String MOD_ID = "mega_showdown";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItemGroups.registerItemGroups();
        ItemRegister.register();
        BlockRegister.register();
        ModSounds.registerSounds();
        ModBlockEntities.registerBlockEntities();
        ModScreenHandlers.registerScreenHandlers();

        ModWorldGeneration.generateModWorldGen();

        DataManage.registerDataComponentTypes();

        BattleNetwork.registerC2SPackets();

        Reflection.initialize(ShowdownConfig.class);

        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);

        MegaCommands.register();
        ModEvents.register();

        ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of(MOD_ID, "gyaradosjumpingmega"),
                FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
                Text.literal("Gyrados Jumping Mega"),
                ResourcePackActivationType.NORMAL
        );
    }

    private void onServerStarted(MinecraftServer server) {
        Utils.registerRemapping();
        TeraTypeHelper.loadShardData();

        CobbleEvents.register();
        TrinketEvent.register();
    }
}
