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
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.Dynamax;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.TeraItem;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ZRingItem;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import kotlin.Unit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

public class RevertEvents {
    public static Unit battleEnded(BattleVictoryEvent battleVictoryEvent) {
        battleVictoryEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
            for (Pokemon pokemon: playerPartyStore){
                if(pokemon.getAspects().contains("mega-x") || pokemon.getAspects().contains("mega-y") || pokemon.getAspects().contains("mega")){
                    MegaLogic.Devolve(pokemon, serverPlayer, true);
                }

                EventUtils.revertFormesEnd(pokemon);

                if(pokemon.getEntity() != null){
                    pokemon.getEntity().removeEffect(MobEffects.GLOWING);
                }
            }
        });

        return Unit.INSTANCE;
    }

    public static Unit devolveFainted(BattleFaintedEvent battleFaintedEvent) {
        Pokemon pokemon = battleFaintedEvent.getKilled().getOriginalPokemon();
        ServerPlayer serverPlayer = battleFaintedEvent.getKilled().getOriginalPokemon().getOwnerPlayer();

        if(serverPlayer == null){
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
                    pokemon.getEntity().removeEffect(MobEffects.GLOWING);
                }
            }
        });

        return Unit.INSTANCE;
    }

    public static void checkKeldeo(PlayerPartyStore pokemons){
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

    public static Unit battleStarted(@NotNull BattleStartedPreEvent battleEvent) {
        for(ServerPlayer player: battleEvent.getBattle().getPlayers()){
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
            checkKeldeo(playerPartyStore);

            if(Config.battleMode){
                for (Pokemon pokemon : playerPartyStore) {
                    EventUtils.revertFormesEnd(pokemon);

                    if(pokemon.getAspects().contains("mega-x") || pokemon.getAspects().contains("mega-y") || pokemon.getAspects().contains("mega")){
                        MegaLogic.Devolve(pokemon, player, true);
                    }
                }
            }

            GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);

            boolean hasTeraItemCurios = CuriosApi.getCuriosInventory(player)
                    .map(inventory -> inventory.isEquipped(stack -> stack.getItem() instanceof TeraItem))
                    .orElse(false);

            ItemStack teraOrb = CuriosApi.getCuriosInventory(player)
                    .flatMap(curiosInventory -> curiosInventory.findFirstCurio(TeraMoves.TERA_ORB.get()))
                    .map(SlotResult::stack)
                    .orElse(null);

            if(teraOrb != null && teraOrb.getDamageValue() >= 100){
                hasTeraItemCurios = false;
            }

            if(hasTeraItemCurios && Config.teralization){
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","tera_orb"));
            }else {
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","tera_orb"));
            }

            if((Config.battleMode || Config.scuffedMode || Config.battleModeOnly) &&
                    MegaLogic.Possible(player, true) && !player.getData(DataManage.MEGA_DATA)){
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","key_stone"));
            }else{
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","key_stone"));
            }

            boolean hasZItemCurios = CuriosApi.getCuriosInventory(player)
                    .map(inventory -> inventory.isEquipped(stack -> stack.getItem() instanceof ZRingItem))
                    .orElse(false);

            if((player.getOffhandItem().getItem() instanceof ZRingItem || hasZItemCurios) && Config.zMoves){
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","z_ring"));
            }else{
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","z_ring"));
            }

            boolean hasDMAXItemCurios = CuriosApi.getCuriosInventory(player)
                    .map(inventory -> inventory.isEquipped(stack -> stack.getItem() instanceof Dynamax))
                    .orElse(false);

            if((player.getOffhandItem().getItem() instanceof Dynamax || hasDMAXItemCurios)){
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","dynamax_band"));
            }else {
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","dynamax_band"));
            }
        }

        return Unit.INSTANCE;
    }
}
