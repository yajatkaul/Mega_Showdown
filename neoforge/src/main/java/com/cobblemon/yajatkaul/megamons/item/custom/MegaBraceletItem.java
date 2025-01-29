package com.cobblemon.yajatkaul.megamons.item.custom;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.megamons.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MegaBraceletItem extends Item {
    private static final Logger LOGGER = LoggerFactory.getLogger("CobblemonAddon");

    public MegaBraceletItem(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player arg2, LivingEntity context, InteractionHand arg4) {
        if(context instanceof PokemonEntity){
            Pokemon pokemon = ((PokemonEntity) context).getPokemon();
            if(pokemon.heldItem().is(ModItems.CHARIZARDITE_X)){
                new FlagSpeciesFeature("mega", true).apply(pokemon);
            }
        }

        return InteractionResult.PASS;
    }
}
