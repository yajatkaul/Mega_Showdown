package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.block.PokemonStones;
import com.cobblemon.yajatkaul.mega_showdown.cobbleEvents.CobbleEventHandler;
import com.cobblemon.yajatkaul.mega_showdown.commands.MegaCommands;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.creativeMenu.ModItemGroups;
import com.cobblemon.yajatkaul.mega_showdown.event.ModEvents;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.networking.BattleNetwork;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MegaShowdown implements ModInitializer {
    public static final String MOD_ID = "mega_showdown";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItemGroups.registerItemGroups();
        ModItems.registerModItem();
        MegaStones.registerModItem();

        ModBlocks.registerBlocks();
        PokemonStones.registerBlocks();
        MegaOres.registerBlocks();

        DataManage.registerDataComponentTypes();

        BattleNetwork.registerC2SPackets();

        Reflection.initialize(ShowdownConfig.class);

        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);

        MegaCommands.register();

        ModEvents.register();
    }

    private void onServerStarted(MinecraftServer server) {
        Utils.loadMegaStoneIds();
        Utils.registerRemapping();

        CobblemonEvents.HELD_ITEM_POST.subscribe(Priority.NORMAL, CobbleEventHandler::onHeldItemChange);

        CobblemonEvents.POKEMON_RELEASED_EVENT_POST.subscribe(Priority.NORMAL, CobbleEventHandler::onReleasePokemon);

        CobblemonEvents.TRADE_COMPLETED.subscribe(Priority.NORMAL, CobbleEventHandler::onMegaTraded);

        CobblemonEvents.HELD_ITEM_POST.subscribe(Priority.NORMAL, CobbleEventHandler::primalEvent);

        CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, CobbleEventHandler::devolveFainted);

        CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, CobbleEventHandler::battleStarted);

        CobblemonEvents.MEGA_EVOLUTION.subscribe(Priority.NORMAL, CobbleEventHandler::megaEvolution);

        if(ShowdownConfig.battleModeOnly.get() || ShowdownConfig.battleMode.get()){
            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, CobbleEventHandler::getBattleEndInfo);
            CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, CobbleEventHandler::deVolveFlee);

            ServerPlayConnectionEvents.JOIN.register((handler, sender, serverJoin) -> {
                ServerPlayerEntity player = handler.player;
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

                for (Pokemon pokemon : playerPartyStore) {
                    new FlagSpeciesFeature("mega", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                }
            });

            ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(newPlayer);

                if(alive){
                    oldPlayer.removeAttached(DataManage.MEGA_DATA);
                }else{
                    newPlayer.removeAttached(DataManage.MEGA_DATA);
                }

                for (Pokemon pokemon : playerPartyStore) {
                    new FlagSpeciesFeature("mega", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                }
            });
        }
    }
}
