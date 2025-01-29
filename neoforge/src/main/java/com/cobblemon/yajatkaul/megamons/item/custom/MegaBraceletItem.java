package com.cobblemon.yajatkaul.megamons.item.custom;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.megamons.item.ModItems;
import com.cobblemon.yajatkaul.megamons.showdown.ShowdownUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MegaBraceletItem extends Item {
    private static final Logger LOGGER = LoggerFactory.getLogger("Mega Showdown");

    public MegaBraceletItem(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player arg2, LivingEntity context, InteractionHand arg4) {
        if(context instanceof PokemonEntity && !context.level().isClientSide()){
            Pokemon pokemon = ((PokemonEntity) context).getPokemon();
            Species species = ShowdownUtils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());

            if(species == pokemon.getSpecies()){
                if(species == ShowdownUtils.getSpecies("charizard")){
                    if(pokemon.heldItem().is(ModItems.CHARIZARDITE_X)){
                        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                    }else if(pokemon.heldItem().is(ModItems.CHARIZARDITE_Y)){
                        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                    }
                }
                if(species == ShowdownUtils.getSpecies("mewtwo")){
                    if(pokemon.heldItem().is(ModItems.MEWTWONITE_X)){
                        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                    }else if(pokemon.heldItem().is(ModItems.MEWTWONITE_Y)){
                        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                    }
                }
                else{
                    new FlagSpeciesFeature("mega", true).apply(pokemon);
                }
            }
        }

        return InteractionResult.PASS;
    }
}
