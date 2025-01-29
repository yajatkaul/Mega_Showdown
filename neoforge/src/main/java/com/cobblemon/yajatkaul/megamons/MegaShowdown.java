package com.cobblemon.yajatkaul.megamons;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.events.pokemon.interaction.ExperienceCandyUseEvent;
import com.cobblemon.mod.common.api.events.pokemon.interaction.HeldItemUpdatedEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.megamons.block.ModBlocks;
import com.cobblemon.yajatkaul.megamons.item.ModCreativeModeTabs;
import com.cobblemon.yajatkaul.megamons.item.ModItems;
import kotlin.Unit;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(MegaShowdown.MOD_ID)
public final class MegaShowdown {

    public static final String MOD_ID = "mega_showdown";
    public MegaShowdown(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        CobblemonEvents.HELD_ITEM_POST.subscribe(Priority.NORMAL, this::onHeldItemChange);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event){

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger("CobblemonAddon");

    private Unit onHeldItemChange(HeldItemEvent.Post event) {
        Pokemon pokemon = event.getPokemon();

        if(!pokemon.heldItem().is(ModItems.CHARIZARDITE_X)){
            new FlagSpeciesFeature("mega-x", false).apply(pokemon);
        }

        LOGGER.info(String.valueOf(pokemon.heldItem().getItem()));

        return Unit.INSTANCE;
    }

}