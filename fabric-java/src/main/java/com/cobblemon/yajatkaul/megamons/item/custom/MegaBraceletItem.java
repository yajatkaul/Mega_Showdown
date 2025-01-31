package com.cobblemon.yajatkaul.megamons.item.custom;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.megamons.MegaShowdown;
import com.cobblemon.yajatkaul.megamons.item.ModItems;
import com.cobblemon.yajatkaul.megamons.showdown.ShowdownUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.List;

public class MegaBraceletItem extends Item {

    public MegaBraceletItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity context, Hand hand) {
        if(context instanceof PokemonEntity && !context.getWorld().isClient){
            Pokemon pokemon = ((PokemonEntity) context).getPokemon();
            Species species = ShowdownUtils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());

            if(species == pokemon.getSpecies()){
                if(species == ShowdownUtils.getSpecies("charizard")){
                    if(pokemon.heldItem().isOf(ModItems.CHARIZARDITE_X)){
                        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                    }else if(pokemon.heldItem().isOf(ModItems.CHARIZARDITE_Y)){
                        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                    }
                }
                if(species == ShowdownUtils.getSpecies("mewtwo")){
                    if(pokemon.heldItem().isOf(ModItems.MEWTWONITE_X)){
                        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                    }else if(pokemon.heldItem().isOf(ModItems.MEWTWONITE_Y)){
                        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                    }
                }
                else{
                    new FlagSpeciesFeature("mega", true).apply(pokemon);
                }
            }
        }

        return super.useOnEntity(stack, user, context, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.megabracelet.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
