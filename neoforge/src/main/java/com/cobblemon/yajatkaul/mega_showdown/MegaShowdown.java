package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.cobbleEvents.CobbleEventsHandler;
import com.cobblemon.yajatkaul.mega_showdown.commands.MegaCommands;
import com.cobblemon.yajatkaul.mega_showdown.curios.ChestRenderer;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.networking.NetworkHandler;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MegaEvo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import com.cobblemon.yajatkaul.mega_showdown.creativeTab.ModCreativeModeTabs;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import static com.cobblemon.yajatkaul.mega_showdown.megaevo.Controls.MEGA_ITEM_KEY;

@Mod(MegaShowdown.MOD_ID)
public final class MegaShowdown {
    public static final Logger LOGGER = LoggerFactory.getLogger("Mega Showdown");
    public static final String MOD_ID = "mega_showdown";

    public MegaShowdown(IEventBus modEventBus, @NotNull ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);

        ModBlocks.register(modEventBus);
        MegaOres.register();

        ModItems.register(modEventBus);
        MegaStones.register();

        DataManage.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        modEventBus.addListener(NetworkHandler::register);

        NeoForge.EVENT_BUS.addListener(MegaCommands::register);
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        Utils.loadMegaStoneIds();
        Utils.registerRemapping();

        CobblemonEvents.HELD_ITEM_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::onHeldItemChange);

        CobblemonEvents.POKEMON_RELEASED_EVENT_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::onReleasePokemon);

        CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, CobbleEventsHandler::devolveFainted);

        CobblemonEvents.TRADE_COMPLETED.subscribe(Priority.NORMAL, CobbleEventsHandler::onMegaTraded);

        CobblemonEvents.HELD_ITEM_POST.subscribe(Priority.NORMAL, CobbleEventsHandler::primalEvent);

        CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, CobbleEventsHandler::battleStarted);

        CobblemonEvents.MEGA_EVOLUTION.subscribe(Priority.NORMAL, CobbleEventsHandler::megaEvolution);

        // Battle mode only
        if(Config.battleModeOnly || Config.battleMode){
            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, CobbleEventsHandler::battleEnded);
            CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, CobbleEventsHandler::deVolveFlee);
        }
    }

    @SubscribeEvent
    private void onServerJoin(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        if(Config.battleModeOnly){
            if(playerLoggedInEvent.getEntity() instanceof ServerPlayer player){
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

                for (Pokemon pokemon : playerPartyStore) {
                    new FlagSpeciesFeature("mega", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                }
            }
        }
    }

    @SubscribeEvent
    private void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event){
        if(Config.battleModeOnly){
            if(event.getEntity() instanceof ServerPlayer player){
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

                for (Pokemon pokemon : playerPartyStore) {
                    new FlagSpeciesFeature("mega", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                }
            }
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            NeoForge.EVENT_BUS.addListener(ClientModEvents::onClientTick);
            NeoForge.EVENT_BUS.addListener(ChestRenderer::onRenderPlayer);
        }

        // Register the key binding
        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            event.register(MEGA_ITEM_KEY.get());
        }

        public static void onClientTick(ClientTickEvent.Post event) {
            while (MEGA_ITEM_KEY.get().consumeClick()) {
                PacketDistributor.sendToServer(new MegaEvo("mega_evo"));
            }
        }
    }
}