package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class N_Solarizer extends Item {
    public N_Solarizer(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack arg, PlayerEntity player, LivingEntity context, Hand hand) {
        if (player.getWorld().isClient) {
            return ActionResult.PASS;
        }

        if (!(context instanceof PokemonEntity pk)) {
            return ActionResult.PASS;
        }

        Pokemon pokemon = pk.getPokemon();
        if (pokemon.getOwnerPlayer() != player) {
            return ActionResult.PASS;
        }

        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
        Pokemon currentValue = arg.getOrDefault(DataManage.N_SOLAR, null);

        if (currentValue != null && pokemon.getSpecies().getName().equals("Necrozma")) {
            if (checkFused(pokemon)){
                player.sendMessage(
                        Text.literal("Already fused!").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return ActionResult.PASS;
            }

            HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
            if(map == null){
                map = new HashMap<>();
            }
            map.put(pokemon.getUuid(), currentValue);
            player.setAttached(DataManage.DATA_MAP, map);

            pk.setAttached(DataManage.N_SOLAR_POKEMON, currentValue);
            arg.set(DataManage.N_SOLAR, null);
            new FlagSpeciesFeature("dusk-fusion", true).apply(pokemon);
            setTradable(pokemon, false);

            AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "fusion");
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.n_solarizer.inactive"));
        } else if (currentValue == null && pokemon.getSpecies().getName().equals("Solgaleo")) {
            arg.set(DataManage.N_SOLAR, pokemon);
            playerPartyStore.remove(pokemon);
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.n_solarizer.charged"));
        } else if (pokemon.getSpecies().getName().equals("Necrozma") && checkEnabled(pokemon)) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("ultra"));
            FlagSpeciesFeature feature = featureProvider.get(pokemon);

            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if(enabled) {
                    return ActionResult.PASS;
                }
            }

            if(!pokemon.getEntity().hasAttached(DataManage.N_LUNAR_POKEMON)){
                HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
                Pokemon toAdd = map.get(pokemon.getUuid());
                playerPartyStore.add(toAdd);
                map.remove(pokemon.getUuid());
                player.setAttached(DataManage.DATA_MAP, map);
            }else{
                playerPartyStore.add(pokemon.getEntity().getAttached(DataManage.N_LUNAR_POKEMON));
                pk.removeAttached(DataManage.N_LUNAR_POKEMON);
            }

            new FlagSpeciesFeature("dusk-fusion", false).apply(pokemon);
            setTradable(pokemon, true);
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.n_solarizer.inactive"));
        } else {
            return ActionResult.PASS;
        }

        player.setStackInHand(hand, arg);
        return ActionResult.SUCCESS;
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
    public void onItemEntityDestroyed(ItemEntity entity) {
        if(entity.getOwner() instanceof ServerPlayerEntity player){
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
            Pokemon currentValue = entity.getStack().getOrDefault(DataManage.N_SOLAR, null);

            if(currentValue != null){
                playerPartyStore.add(currentValue);
                entity.getStack().set(DataManage.N_SOLAR, null);
            }
        }

        super.onItemEntityDestroyed(entity);
    }
}
