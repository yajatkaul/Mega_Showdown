package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent;
import com.cobblemon.mod.common.api.events.battles.BattleFledEvent;
import com.cobblemon.mod.common.api.events.battles.BattleStartedPreEvent;
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.config.Config;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax.Dynamax;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.TeraItem;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ZRingItem;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class RevertEvents {
    public static Unit battleEnded(BattleVictoryEvent battleVictoryEvent) {
        battleVictoryEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
            for (Pokemon pokemon: playerPartyStore){

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

        boolean isMega = pokemon.getAspects().stream()
                .anyMatch(aspect -> aspect.startsWith("mega"));

        if (isMega) {
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
                    pokemon.getEntity().removeEffect(MobEffects.GLOWING);
                }
            }
        });

        return Unit.INSTANCE;
    }

    public static void checkKeldeo(PlayerPartyStore pokemons){
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

    public static Unit battleStarted(@NotNull BattleStartedPreEvent battleEvent) {
        for(ServerPlayer player: battleEvent.getBattle().getPlayers()){
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
            checkKeldeo(playerPartyStore);

            for (Pokemon pokemon : playerPartyStore) {
                EventUtils.revertFormesEnd(pokemon);
            }

            if (Config.revertMegas && !Config.multipleMegas) {
                player.getServer().getCommands().performPrefixedCommand(player.createCommandSourceStack(), "/msdresetmega");
            }

            GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);

            boolean hasDMAXItemCurios = CuriosApi.getCuriosInventory(player)
                    .map(inventory -> inventory.isEquipped(stack ->
                            (stack.getItem() instanceof Dynamax || stack.is(ModTags.Items.DYNAMAX_BAND))))
                    .orElse(false);

            if(isBlockNearby(player, ModBlocks.POWER_SPOT.get(), Config.powerSpotRange) || Config.dynamaxAnywhere){
                if((player.getOffhandItem().getItem() instanceof Dynamax
                        || player.getOffhandItem().is(ModTags.Items.DYNAMAX_BAND)
                        || hasDMAXItemCurios) && Config.dynamax){
                    data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","dynamax_band"));
                }else {
                    data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","dynamax_band"));
                }
            }else {
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","dynamax_band"));
            }

            boolean hasTeraItemCurios = CuriosApi.getCuriosInventory(player)
                    .map(inventory -> inventory.isEquipped(stack -> (stack.getItem() instanceof TeraItem)))
                    .orElse(false);

            ItemStack teraOrb = CuriosApi.getCuriosInventory(player)
                    .flatMap(curiosInventory -> curiosInventory.findFirstCurio(
                            stack -> (stack.getItem() instanceof TeraItem)
                    ))
                    .map(SlotResult::stack)
                    .orElse(null);

            if(teraOrb == null || teraOrb.getDamageValue() >= 100){
                hasTeraItemCurios = false;
            }

            if(hasTeraItemCurios && Config.teralization){
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","tera_orb"));
            }else {
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","tera_orb"));
            }

            if(Config.revertMegas && Config.mega && !Config.multipleMegas &&
                    MegaLogic.Possible(player, true) && !player.getData(DataManage.MEGA_DATA)){
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","key_stone"));
            }else{
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","key_stone"));
            }

            boolean hasZItemCurios = CuriosApi.getCuriosInventory(player)
                    .map(inventory -> inventory.isEquipped(stack -> (stack.getItem() instanceof ZRingItem || stack.is(ModTags.Items.Z_RINGS))))
                    .orElse(false);

            if((player.getOffhandItem().getItem() instanceof ZRingItem || player.getOffhandItem().is(ModTags.Items.Z_CRYSTALS) || hasZItemCurios) && Config.zMoves){
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon","z_ring"));
            }else{
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon","z_ring"));
            }
        }

        return Unit.INSTANCE;
    }

    public static boolean isBlockNearby(ServerPlayer player, Block targetBlock, int radius) {
        BlockPos playerPos = player.blockPosition();
        ServerLevel level = player.serverLevel();

        // scan a cube around the player
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos checkPos = playerPos.offset(dx, dy, dz);
                    if (level.getBlockState(checkPos).is(targetBlock)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
