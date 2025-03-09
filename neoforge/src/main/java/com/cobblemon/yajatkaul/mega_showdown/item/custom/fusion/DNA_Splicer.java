package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DNA_Splicer extends Item {
    public DNA_Splicer(Properties arg) {
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
        boolean currentValue = arg.getOrDefault(DataManage.KYUREM_DATA, false);

        if(pokemon.getSpecies().getName().equals("Kyurem") && checkEnabled(pokemon)){
            new FlagSpeciesFeature("white", false).apply(pokemon);
            new FlagSpeciesFeature("black", false).apply(pokemon);
            playerPartyStore.add(player.getData(DataManage.KYUREM_FUSION));

            arg.set(DataManage.KYUREM_DATA, false);
            player.setData(DataManage.KYUREM_FUSION, new Pokemon());
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.dna_splicer.inactive"));
        }else if (currentValue && pokemon.getSpecies().getName().equals("Kyurem") && !player.getData(DataManage.KYUREM_FUSION).equals(new Pokemon())) {

            if(player.getData(DataManage.KYUREM_FUSION).getSpecies().getName().equals("Reshiram")){
                new FlagSpeciesFeature("white", true).apply(pokemon);
            }else{
                new FlagSpeciesFeature("black", true).apply(pokemon);
            }

            arg.set(DataManage.KYUREM_DATA, false);
            AdvancementHelper.grantAdvancement((ServerPlayer) player, "fusion");
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.dna_splicer.inactive"));
        } else if (!currentValue && pokemon.getSpecies().getName().equals("Reshiram")) {
            arg.set(DataManage.KYUREM_DATA, true);
            player.setData(DataManage.KYUREM_FUSION, pk.getPokemon());
            playerPartyStore.remove(pk.getPokemon());
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.dna_splicer.charged"));
        }else if (!currentValue && pokemon.getSpecies().getName().equals("Zekrom")) {
            arg.set(DataManage.KYUREM_DATA, true);
            player.setData(DataManage.KYUREM_FUSION, pk.getPokemon());
            playerPartyStore.remove(pk.getPokemon());
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.dna_splicer.charged"));
        } else {
            return InteractionResult.PASS;
        }

        player.setItemInHand(hand, arg);
        player.getInventory().setChanged();
        return InteractionResult.SUCCESS;
    }

    private boolean checkEnabled(Pokemon pokemon){
        FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("black"));
        FlagSpeciesFeature feature = featureProvider.get(pokemon);

        if(feature != null){
            boolean enabled = featureProvider.get(pokemon).getEnabled();

            if(enabled) {
                return true;
            }
        }

        featureProvider = new FlagSpeciesFeatureProvider(List.of("white"));
        feature = featureProvider.get(pokemon);

        if(feature != null){
            boolean enabled = featureProvider.get(pokemon).getEnabled();

            if(enabled){
                return true;
            }
        }

        return false;
    }

    private void particleMagic(LivingEntity pokemon){

    }
}
