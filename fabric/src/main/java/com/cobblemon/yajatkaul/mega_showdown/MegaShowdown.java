package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.cobblemon.yajatkaul.mega_showdown.block.BlockRegister;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.entity.ModBlockEntities;
import com.cobblemon.yajatkaul.mega_showdown.commands.MegaCommands;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.creativeMenu.ModItemGroups;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datapack.DatapackRegister;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.GmaxData;
import com.cobblemon.yajatkaul.mega_showdown.event.ModEvents;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.CobbleEvents;
import com.cobblemon.yajatkaul.mega_showdown.event.trinket.TrinketEvents;
import com.cobblemon.yajatkaul.mega_showdown.item.ItemRegister;
import com.cobblemon.yajatkaul.mega_showdown.networking.PacketRegister;
import com.cobblemon.yajatkaul.mega_showdown.screen.ModScreenHandlers;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.TeraTypeHelper;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import com.cobblemon.yajatkaul.mega_showdown.utility.showdown.LoadShowdownItems;
import com.cobblemon.yajatkaul.mega_showdown.worldgen.ModWorldGeneration;
import com.google.common.reflect.Reflection;
import kotlin.Unit;
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

        PacketRegister.registerC2SPackets();

        Reflection.initialize(MegaShowdownConfig.class);

        MegaCommands.register();
        ModEvents.register();

        ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of(MOD_ID, "gyaradosjumpingmega"),
                FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
                Text.translatable("message.mega_showdown.gyrados_jump_mega"),
                ResourcePackActivationType.NORMAL
        );

        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);

        DatapackRegister.register();

        CobbleEvents.register();
        TrinketEvents.register();
    }

    private void onServerStarted(MinecraftServer server) {
        Utils.registryLoader(server.getRegistryManager());

        LoadShowdownItems.registerRemapping();
        TeraTypeHelper.loadShardData();

        Cobblemon.INSTANCE.getShowdownThread().queue(showdownService -> {
            if (showdownService instanceof GraalShowdownService service) {
                Value receiveMoveDataFn = service.context.getBindings("js").getMember("receiveCustomGmaxMove");
                for (GmaxData gmax : Utils.gmaxRegistry) {
                    receiveMoveDataFn.execute(gmax.pokemonShowdownId(), gmax.gmaxMove());
                }
            }
            return Unit.INSTANCE;
        });
    }
}
