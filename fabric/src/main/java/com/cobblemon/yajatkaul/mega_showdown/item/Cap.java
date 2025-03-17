package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class Cap extends Item {

    public Cap(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        if(entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling()){
            if(pk.getPokemon().getSpecies().getName().equals("Pikachu")){
                new StringSpeciesFeature("league_cap", "partner").apply(pk);
                stack.decrement(1);
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
