package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.battle.BattleHandling;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItemGroups;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.networking.BattleNetwork;
import com.cobblemon.yajatkaul.mega_showdown.showdown.ShowdownUtils;
import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
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

        DataManage.registerDataComponentTypes();

        BattleNetwork.registerC2SPackets();

        Reflection.initialize(ShowdownConfig.class);

        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
    }

    private void onServerStarted(MinecraftServer server) {

        ShowdownUtils.loadMegaStoneIds();
        CobblemonEvents.HELD_ITEM_POST.subscribe(Priority.NORMAL, ShowdownUtils::onHeldItemChange);
        CobblemonEvents.POKEMON_RELEASED_EVENT_POST.subscribe(Priority.NORMAL, ShowdownUtils::onReleasePokemon);

        if(ShowdownConfig.battleModeOnly.get()){
            CobblemonEvents.BATTLE_STARTED_POST.subscribe(Priority.NORMAL, BattleHandling::getBattleInfo);
            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, BattleHandling::getBattleEndInfo);
            CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, BattleHandling::deVolveFlee);

            ServerPlayConnectionEvents.JOIN.register((handler, sender, serverJoin) -> {
                ServerPlayerEntity player = handler.player;
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

                player.setAttached(DataManage.MEGA_DATA, false);
                player.setAttached(DataManage.MEGA_POKEMON, null);
                player.setAttached(DataManage.BATTLE_ID, null);

                for (Pokemon pokemon : playerPartyStore) {
                    new FlagSpeciesFeature("mega", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                }
            });
        }
    }


}
