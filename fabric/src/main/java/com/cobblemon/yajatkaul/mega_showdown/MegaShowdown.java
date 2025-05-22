package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.common.data.CobblemonDataProvider;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.cobblemon.yajatkaul.mega_showdown.block.BlockRegister;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.entity.ModBlockEntities;
import com.cobblemon.yajatkaul.mega_showdown.commands.MegaCommands;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.creativeMenu.ModItemGroups;
import com.cobblemon.yajatkaul.mega_showdown.datapack.ModDatapack;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.GmaxData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.showdown.Abilities;
import com.cobblemon.yajatkaul.mega_showdown.datapack.showdown.HeldItems;
import com.cobblemon.yajatkaul.mega_showdown.datapack.showdown.Moves;
import com.cobblemon.yajatkaul.mega_showdown.event.CobbleEvents;
import com.cobblemon.yajatkaul.mega_showdown.event.ModEvents;
import com.cobblemon.yajatkaul.mega_showdown.event.TrinketEvent;
import com.cobblemon.yajatkaul.mega_showdown.item.*;
import com.cobblemon.yajatkaul.mega_showdown.item.configActions.ConfigResults;
import com.cobblemon.yajatkaul.mega_showdown.networking.PacketRegister;
import com.cobblemon.yajatkaul.mega_showdown.screen.ModScreenHandlers;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.TeraTypeHelper;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import com.cobblemon.yajatkaul.mega_showdown.worldgen.ModWorldGeneration;
import com.google.common.reflect.Reflection;
import kotlin.Unit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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

        Reflection.initialize(ShowdownConfig.class);

        MegaCommands.register();
        ModEvents.register();

        ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of(MOD_ID, "gyaradosjumpingmega"),
                FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
                Text.translatable("message.mega_showdown.gyrados_jump_mega"),
                ResourcePackActivationType.NORMAL
        );

        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);


        UseItemCallback.EVENT.register(ConfigResults::useItem);

        CobblemonDataProvider.INSTANCE.register(HeldItems.INSTANCE);
        CobblemonDataProvider.INSTANCE.register(Abilities.INSTANCE);
        CobblemonDataProvider.INSTANCE.register(Moves.INSTANCE);
        ModDatapack.register();

        CobbleEvents.register();
        TrinketEvent.register();
    }

    private void onServerStarted(MinecraftServer server) {
        Utils.registryLoader(server.getRegistryManager());

        Utils.registerRemapping();
        TeraTypeHelper.loadShardData();

        Cobblemon.INSTANCE.getShowdownThread().queue(showdownService -> {
            if(showdownService instanceof GraalShowdownService service){
                Value receiveMoveDataFn = service.context.getBindings("js").getMember("receiveCustomGmaxMove");
                for (GmaxData gmax: Utils.gmaxRegistry) {
                    receiveMoveDataFn.execute(gmax.pokemon(), gmax.gmaxMove());
                }
            }
            return Unit.INSTANCE;
        });
    }
}
