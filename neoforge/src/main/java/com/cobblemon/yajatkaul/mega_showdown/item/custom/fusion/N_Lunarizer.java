package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
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

import java.util.Set;


public class N_Lunarizer extends Item {
    public N_Lunarizer(Properties arg) {
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
        boolean currentValue = arg.getOrDefault(DataManage.N_LUNAR, false);

        if (currentValue && pokemon.getSpecies().getName().equals("Necrozma") && !player.getData(DataManage.N_LUNAR_POKEMON).equals(new Pokemon())) {
            arg.set(DataManage.N_LUNAR, false);
            pokemon.setForcedAspects(Set.of("dawn-fusion"));
            arg.set(DataComponents.CUSTOM_NAME, Component.literal("N-Lunarizer (Inactive)"));
        } else if (!currentValue && pokemon.getSpecies().getName().equals("Lunala")) {
            arg.set(DataManage.N_LUNAR, true);
            player.setData(DataManage.N_LUNAR_POKEMON, pk.getPokemon());
            playerPartyStore.remove(pk.getPokemon());
            arg.set(DataComponents.CUSTOM_NAME, Component.literal("N-Lunarizer (Charged)"));
        } else if (pokemon.getSpecies().getName().equals("Necrozma") && pokemon.getForcedAspects().contains("dawn-fusion")) {
            playerPartyStore.add(player.getData(DataManage.N_LUNAR_POKEMON));
            player.setData(DataManage.N_LUNAR_POKEMON, new Pokemon());
            pokemon.setForcedAspects(Set.of());
            arg.set(DataComponents.CUSTOM_NAME, Component.literal("N-Lunarizer (Inactive)"));
        } else {
            return InteractionResult.PASS;
        }

        player.setItemInHand(hand, arg);
        player.getInventory().setChanged();
        return InteractionResult.SUCCESS;
    }
}
