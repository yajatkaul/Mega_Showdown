package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent;
import com.cobblemon.mod.common.api.events.battles.BattleFledEvent;
import com.cobblemon.mod.common.api.events.battles.BattleStartedPreEvent;
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.TeraItem;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ZRingItem;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import dev.emi.trinkets.api.TrinketsApi;
import kotlin.Unit;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class RevertEvents {
    public static Unit battleStarted(BattleStartedPreEvent battleEvent) {
        for(ServerPlayerEntity player: battleEvent.getBattle().getPlayers()){
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

            checkKeldeo(playerPartyStore);
            if(ShowdownConfig.battleMode.get()){
                for (Pokemon pokemon : playerPartyStore) {
                    EventUtils.revertFormesEnd(pokemon);
                    if(pokemon.getAspects().contains("mega-x") || pokemon.getAspects().contains("mega-y") || pokemon.getAspects().contains("mega")){
                        MegaLogic.Devolve(pokemon, player, true);
                    }
                }
            }

            GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);

            boolean hasTeraItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> item.getItem() instanceof TeraItem)).orElse(false);

            if(hasTeraItemTrinkets && ShowdownConfig.teralization.get()){
                data.getKeyItems().add(Identifier.of("cobblemon","tera_orb"));
            }else{
                data.getKeyItems().remove(Identifier.of("cobblemon","tera_orb"));
            }

            if((ShowdownConfig.scuffedMode.get() || ShowdownConfig.battleMode.get()
                    || ShowdownConfig.battleModeOnly.get()) && MegaLogic.Possible(player, true)
                    && (player.getAttached(DataManage.MEGA_DATA) == null || !player.getAttached(DataManage.MEGA_DATA))){
                data.getKeyItems().add(Identifier.of("cobblemon","key_stone"));
            }else{
                data.getKeyItems().remove(Identifier.of("cobblemon","key_stone"));
            }

            boolean hasZItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> item.getItem() instanceof ZRingItem)).orElse(false);

            if((player.getOffHandStack().getItem() instanceof ZRingItem || hasZItemTrinkets) && ShowdownConfig.zMoves.get()){
                data.getKeyItems().add(Identifier.of("cobblemon","z_ring"));
            }else{
                data.getKeyItems().remove(Identifier.of("cobblemon","z_ring"));
            }
        }

        return Unit.INSTANCE;
    }

    public static Unit getBattleEndInfo(BattleVictoryEvent battleVictoryEvent) {
        battleVictoryEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
            for (Pokemon pokemon: playerPartyStore){
                if(pokemon.getAspects().contains("mega-x") || pokemon.getAspects().contains("mega-y") || pokemon.getAspects().contains("mega")){
                    MegaLogic.Devolve(pokemon, serverPlayer, true);
                }

                EventUtils.revertFormesEnd(pokemon);

                if(pokemon.getEntity() != null){
                    pokemon.getEntity().removeStatusEffect(StatusEffects.GLOWING);
                }
            }
        });

        return Unit.INSTANCE;
    }

    public static Unit devolveFainted(BattleFaintedEvent battleFaintedEvent) {
        Pokemon pokemon = battleFaintedEvent.getKilled().getOriginalPokemon();
        ServerPlayerEntity serverPlayer = battleFaintedEvent.getKilled().getOriginalPokemon().getOwnerPlayer();

        if(serverPlayer == null || serverPlayer.getWorld().isClient){
            return Unit.INSTANCE;
        }

        if(pokemon.getAspects().contains("mega-x") || pokemon.getAspects().contains("mega-y") || pokemon.getAspects().contains("mega")){
            MegaLogic.Devolve(pokemon, serverPlayer, true);
        }

        return Unit.INSTANCE;
    }

    public static Unit deVolveFlee(BattleFledEvent battleFledEvent) {
        battleFledEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
            for (Pokemon pokemon: playerPartyStore){
                EventUtils.revertFormesEnd(pokemon);

                if(pokemon.getAspects().contains("mega-x") || pokemon.getAspects().contains("mega-y") || pokemon.getAspects().contains("mega")){
                    MegaLogic.Devolve(pokemon, serverPlayer, true);
                }

                if(pokemon.getEntity() != null){
                    pokemon.getEntity().removeStatusEffect(StatusEffects.GLOWING);
                }
            }
        });

        return Unit.INSTANCE;
    }

    private static void checkKeldeo(PlayerPartyStore pokemons){
        for(Pokemon pokemon: pokemons){
            if(pokemon.getSpecies().getName().equals("Keldeo")){
                FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("resolute"));
                FlagSpeciesFeature feature = featureProvider.get(pokemon);
                boolean hasMove = false;

                for(Move move: pokemon.getMoveSet().getMoves()){
                    if(move.getName().equals(Moves.INSTANCE.getByName("secretsword").getName())){
                        hasMove = true;
                    }
                }

                if(pokemon.getAspects().contains("resolute")){
                    if(!hasMove){
                        new FlagSpeciesFeature("resolute", false).apply(pokemon);
                        if(pokemon.getEntity() != null){
                            EventUtils.playEvolveAnimation(pokemon.getEntity());
                        }
                    }
                }else {
                    if(hasMove){
                        new FlagSpeciesFeature("resolute", true).apply(pokemon);
                        if(pokemon.getEntity() != null){
                            EventUtils.playEvolveAnimation(pokemon.getEntity());
                        }
                    }
                }
            }
        }
    }
}
