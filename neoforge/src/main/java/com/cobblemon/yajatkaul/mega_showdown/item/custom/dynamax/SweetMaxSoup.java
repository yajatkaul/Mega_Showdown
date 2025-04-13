package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SweetMaxSoup extends Item {
    public SweetMaxSoup(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity context, InteractionHand arg4) {
        if(player.level().isClientSide){
            return InteractionResult.PASS;
        }

        if(context instanceof PokemonEntity pk){
            Pokemon pokemon = pk.getPokemon();
            if(pokemon.getEntity() == null || pokemon.getEntity().level().isClientSide || pokemon.getEntity().isBattling()){
                return InteractionResult.PASS;
            }

            if(!pokemon.getSpecies().getName().equals("Urshifu")){
                return InteractionResult.PASS;
            }

            if(pokemon.getOwnerPlayer() == player && pokemon.getGmaxFactor()){
                pokemon.setGmaxFactor(false);

                stack.shrink(1);

                player.displayClientMessage(Component.literal("Your pokemon cannot gmax now")
                        .withColor(0xFFFFFF), true);
                return InteractionResult.SUCCESS;
            }else if (pokemon.getOwnerPlayer() == player && !pokemon.getGmaxFactor()){
                pokemon.setGmaxFactor(true);
                stack.shrink(1);

                player.displayClientMessage(Component.literal("Your pokemon can gmax now")
                        .withColor(0xFFFFFF), true);
                return InteractionResult.SUCCESS;
            }
        }

        return super.interactLivingEntity(stack, player, context, arg4);
    }

    @Override
    public void appendHoverText(ItemStack arg, TooltipContext arg2, List<Component> tooltipComponents, TooltipFlag arg3) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.sweet_max_soup.tooltip"));
        super.appendHoverText(arg, arg2, tooltipComponents, arg3);
    }
}
