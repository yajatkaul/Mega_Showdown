package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Cap extends Item {
    public Cap(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, LivingEntity context, InteractionHand arg4) {

        if(context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling()){
            if(pk.getPokemon().getSpecies().getName().equals("Pikachu")){
                new StringSpeciesFeature("league_cap", "partner").apply(pk);
                arg.shrink(1);
            }
        }

        return super.interactLivingEntity(arg, player, context, arg4);
    }
}
