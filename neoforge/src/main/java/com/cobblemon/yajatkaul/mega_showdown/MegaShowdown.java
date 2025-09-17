package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.entity.ModBlockEntities;
import com.cobblemon.yajatkaul.mega_showdown.commands.MegaCommands;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.creativeTab.ModCreativeModeTabs;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datapack.DatapackRegister;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.GmaxData;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.CobbleEvents;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.DynamaxUtils;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.ItemsRegistration;
import com.cobblemon.yajatkaul.mega_showdown.item.inventory.ItemInventoryUtil;
import com.cobblemon.yajatkaul.mega_showdown.networking.NetworkHandler;
import com.cobblemon.yajatkaul.mega_showdown.screen.ModMenuTypes;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import com.cobblemon.yajatkaul.mega_showdown.utility.showdown.LoadShowdownItems;
import kotlin.Unit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(MegaShowdown.MOD_ID)
public final class MegaShowdown {
    public static final Logger LOGGER = LoggerFactory.getLogger("Mega Showdown");
    public static final String MOD_ID = "mega_showdown";

    public static final ItemCapability<ItemInventoryUtil, Void> ITEM_STORAGE =
            ItemCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "item_storage"),
                    ItemInventoryUtil.class
            );

    public MegaShowdown(IEventBus modEventBus, @NotNull ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        ModBlocks.register(modEventBus);
        MegaOres.register();
        ModSounds.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ItemsRegistration.register(modEventBus);

        DataManage.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, MegaShowdownConfig.SPEC);
        modEventBus.addListener(NetworkHandler::register);

        NeoForge.EVENT_BUS.addListener(MegaCommands::register);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::registerDatapackRegistries);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    ModBlocks.GRACIDEA_FLOWER.getId(),
                    ModBlocks.POTTED_GRACIDEA
            );
        });

        CobbleEvents.register();
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
                MegaShowdown.ITEM_STORAGE,
                (itemStack, ctx) -> new ItemInventoryUtil(),
                FormeChangeItems.ZYGARDE_CUBE.get()
        );
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        DynamaxUtils.updateScalingAnimations();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        event.getServer().reloadableRegistries();
        Utils.registryLoader(event.getServer().registryAccess());
        LoadShowdownItems.registerRemapping();

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

    private void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        DatapackRegister.register(event);
    }
}