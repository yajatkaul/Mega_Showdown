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
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax.Dynamax;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.TeraItem;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ZRingItem;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import dev.emi.trinkets.api.TrinketsApi;
import kotlin.Unit;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

public class RevertEvents {
    public static Unit battleStarted(BattleStartedPreEvent battleEvent) {
        for(ServerPlayerEntity player: battleEvent.getBattle().getPlayers()){
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

            checkKeldeo(playerPartyStore);
            for (Pokemon pokemon : playerPartyStore) {
                EventUtils.revertFormesEnd(pokemon);
            }

            GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);

            boolean hasDMaxItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> item.isIn(ModTags.Items.DYNAMAX_BAND))).orElse(false);

            if(isBlockNearby(player, ModBlocks.POWER_SPOT, ShowdownConfig.powerSpotRange.get()) || ShowdownConfig.dynamaxAnywhere.get()) {
                if ((player.getOffHandStack().getItem() instanceof Dynamax
                        || player.getOffHandStack().isIn(ModTags.Items.DYNAMAX_BAND)
                        || hasDMaxItemTrinkets) && ShowdownConfig.dynamax.get()) {
                    data.getKeyItems().add(Identifier.of("cobblemon", "dynamax_band"));
                } else {
                    data.getKeyItems().remove(Identifier.of("cobblemon", "dynamax_band"));
                }
            }else {
                data.getKeyItems().remove(Identifier.of("cobblemon","dynamax_band"));
            }

            boolean hasTeraItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> (item.getItem() instanceof TeraItem))).orElse(false);

            ItemStack teraOrb = TrinketsApi.getTrinketComponent(player)
                    .flatMap(component -> component.getAllEquipped().stream()
                            .map(Pair::getRight)
                            .filter(stack -> !stack.isEmpty() && (
                                    stack.getItem() instanceof TeraItem
                            ))
                            .findFirst()
                    ).orElse(null);

            if (teraOrb == null || teraOrb.getDamage() >= 100) {
                hasTeraItemTrinkets = false;
            }

            if(hasTeraItemTrinkets && ShowdownConfig.teralization.get()){
                data.getKeyItems().add(Identifier.of("cobblemon","tera_orb"));
            }else{
                data.getKeyItems().remove(Identifier.of("cobblemon","tera_orb"));
            }

            if(ShowdownConfig.revertMegas.get() && ShowdownConfig.mega.get() && MegaLogic.Possible(player, true)
                    && (player.getAttached(DataManage.MEGA_DATA) == null || !player.getAttached(DataManage.MEGA_DATA))){
                data.getKeyItems().add(Identifier.of("cobblemon","key_stone"));
            }else{
                data.getKeyItems().remove(Identifier.of("cobblemon","key_stone"));
            }

            boolean hasZItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> (item.getItem() instanceof ZRingItem
                            || item.isIn(ModTags.Items.Z_RINGS)))).orElse(false);

            if((player.getOffHandStack().getItem() instanceof ZRingItem
                    || player.getOffHandStack().isIn(ModTags.Items.Z_RINGS)
                    || hasZItemTrinkets) && ShowdownConfig.zMoves.get()){
                data.getKeyItems().add(Identifier.of("cobblemon","z_ring"));
            }else{
                data.getKeyItems().remove(Identifier.of("cobblemon","z_ring"));
            }
        }

        return Unit.INSTANCE;
    }

    public static boolean isBlockNearby(ServerPlayerEntity player, Block targetBlock, int radius) {
        BlockPos playerPos = player.getBlockPos();
        ServerWorld world = player.getServerWorld();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos checkPos = playerPos.add(dx, dy, dz);
                    if (world.getBlockState(checkPos).isOf(targetBlock)) {
                        return true; // Found the block
                    }
                }
            }
        }

        return false; // Not found
    }

    public static Unit getBattleEndInfo(BattleVictoryEvent battleVictoryEvent) {
        battleVictoryEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
            for (Pokemon pokemon: playerPartyStore){
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

        if(pokemon.getAspects().contains("mega_x") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega")){
            MegaLogic.Devolve(pokemon, true);
        }

        return Unit.INSTANCE;
    }

    public static Unit deVolveFlee(BattleFledEvent battleFledEvent) {
        battleFledEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
            for (Pokemon pokemon: playerPartyStore){
                EventUtils.revertFormesEnd(pokemon);

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
