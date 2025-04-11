package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.List;

public class SweetMaxSoup extends Item {
    public SweetMaxSoup(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
        if(player.getWorld().isClient){
            return ActionResult.PASS;
        }

        if(context instanceof PokemonEntity pk){
            Pokemon pokemon = pk.getPokemon();
            if(pokemon.getEntity() == null || pokemon.getEntity().getWorld().isClient || pokemon.getEntity().isBattling()){
                return ActionResult.PASS;
            }

            if(!pokemon.getSpecies().getName().equals("Urshifu")){
                return ActionResult.PASS;
            }

            if(pokemon.getOwnerPlayer() == player && pokemon.getGmaxFactor()){
                pokemon.setGmaxFactor(false);

                stack.decrement(1);

                player.sendMessage(
                        Text.literal("Your pokemon cannot gmax now").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF))),
                        true
                );
                return ActionResult.SUCCESS;
            }else if (pokemon.getOwnerPlayer() == player && !pokemon.getGmaxFactor()){
                pokemon.setGmaxFactor(true);
                stack.decrement(1);

                player.sendMessage(
                        Text.literal("Your pokemon can gmax now").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF))),
                        true
                );
                return ActionResult.SUCCESS;
            }
        }

        return super.useOnEntity(stack, player, context, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.sweet_max_soup.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
