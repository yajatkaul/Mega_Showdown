package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokemonRef;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class N_Solarizer extends Item {
    public N_Solarizer(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, @NotNull LivingEntity context, InteractionHand hand) {
        if (player.level().isClientSide) {
            return InteractionResult.PASS;
        }

        if (!(context instanceof PokemonEntity pk)) {
            return InteractionResult.PASS;
        }

        Pokemon pokemon = pk.getPokemon();
        if (pokemon.getOwnerPlayer() != player) {
            return InteractionResult.PASS;
        }

        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);
        PokemonRef refValue = arg.getOrDefault(DataManage.N_SOLAR, null);
        Pokemon currentValue;

        if(refValue == null){
            currentValue = null;
        }else{
            currentValue = refValue.getPokemon();
        }

        if (currentValue != null && pokemon.getSpecies().getName().equals("Necrozma")) {
            if (checkFused(pokemon)){
                player.displayClientMessage(Component.literal("Already fused!")
                        .withColor(0xFF0000), true);
                return InteractionResult.PASS;
            }

            HashMap<UUID, Pokemon> map = player.getData(DataManage.DATA_MAP);
            map.put(pokemon.getUuid(), currentValue);
            player.setData(DataManage.DATA_MAP, map);

            pk.setData(DataManage.N_SOLAR_POKEMON, currentValue);
            arg.set(DataManage.N_SOLAR, null);
            new FlagSpeciesFeature("dusk-fusion", true).apply(pokemon);

            AdvancementHelper.grantAdvancement((ServerPlayer) player, "fusion");
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.n_solarizer.inactive"));
        } else if (currentValue == null && pokemon.getSpecies().getName().equals("Solgaleo")) {
            arg.set(DataManage.N_SOLAR, new PokemonRef(pokemon));
            playerPartyStore.remove(pokemon);
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.n_solarizer.charged"));
        } else if (pokemon.getSpecies().getName().equals("Necrozma") && checkEnabled(pokemon)) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("ultra"));
            FlagSpeciesFeature feature = featureProvider.get(pokemon);

            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if(enabled) {
                    return InteractionResult.PASS;
                }
            }

            if(!pokemon.getEntity().hasData(DataManage.N_SOLAR_POKEMON)){
                HashMap<UUID, Pokemon> map = player.getData(DataManage.DATA_MAP);
                Pokemon toAdd = map.get(pokemon.getUuid());
                playerPartyStore.add(toAdd);
                map.remove(pokemon.getUuid());
                player.setData(DataManage.DATA_MAP, map);
            }else{
                playerPartyStore.add(pokemon.getEntity().getData(DataManage.N_SOLAR_POKEMON));
                pk.removeData(DataManage.N_SOLAR_POKEMON);
            }

            new FlagSpeciesFeature("dusk-fusion", false).apply(pokemon);
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.n_solarizer.inactive"));
        } else {
            return InteractionResult.PASS;
        }

        player.setItemInHand(hand, arg);
        player.getInventory().setChanged();
        return InteractionResult.SUCCESS;
    }

    private boolean checkEnabled(Pokemon pokemon){
        FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("dusk-fusion"));
        FlagSpeciesFeature feature = featureProvider.get(pokemon);

        if(feature != null){
            return featureProvider.get(pokemon).getEnabled();
        }
        return false;
    }

    private boolean checkFused(Pokemon pokemon){
        FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("dusk-fusion"));
        FlagSpeciesFeature feature = featureProvider.get(pokemon);

        if(feature != null){
            boolean enabled = featureProvider.get(pokemon).getEnabled();

            if(enabled){
                return true;
            }
        }

        featureProvider = new FlagSpeciesFeatureProvider(List.of("dawn-fusion"));
        feature = featureProvider.get(pokemon);

        if(feature != null){
            boolean enabled = featureProvider.get(pokemon).getEnabled();

            if(enabled){
                return true;
            }
        }

        return false;
    }


    @Override
    public void onDestroyed(ItemEntity entity, DamageSource damageSource) {
        if(entity.getOwner() instanceof ServerPlayer player){
            PokemonRef refValue = entity.getItem().getOrDefault(DataManage.N_SOLAR, null);
            Pokemon currentValue;

            if(refValue == null){
                currentValue = null;
            }else{
                currentValue = refValue.getPokemon();
            }

            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

            if(currentValue != null){
                playerPartyStore.add(currentValue);
                entity.getItem().set(DataManage.N_SOLAR, null);
            }
        }

        super.onDestroyed(entity, damageSource);
    }
}
