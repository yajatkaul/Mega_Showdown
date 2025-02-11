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
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
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

        CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, BattleHandling::devolveFainted);

        if(ShowdownConfig.battleModeOnly.get()){
            CobblemonEvents.BATTLE_STARTED_POST.subscribe(Priority.NORMAL, BattleHandling::getBattleInfo);
            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, BattleHandling::getBattleEndInfo);
            CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, BattleHandling::deVolveFlee);

            ServerPlayConnectionEvents.JOIN.register((handler, sender, serverJoin) -> {
                ServerPlayerEntity player = handler.player;
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

                player.removeAttached(DataManage.MEGA_DATA);
                player.removeAttached(DataManage.MEGA_POKEMON);
                player.removeAttached(DataManage.BATTLE_ID);

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
                    oldPlayer.removeAttached(DataManage.MEGA_POKEMON);
                    oldPlayer.removeAttached(DataManage.BATTLE_ID);
                }else{
                    newPlayer.removeAttached(DataManage.MEGA_DATA);
                    newPlayer.removeAttached(DataManage.MEGA_POKEMON);
                    newPlayer.removeAttached(DataManage.BATTLE_ID);
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
