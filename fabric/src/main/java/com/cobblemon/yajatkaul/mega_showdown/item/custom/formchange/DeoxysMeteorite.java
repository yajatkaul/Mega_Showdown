package com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.List;

public class DeoxysMeteorite extends BlockItem {
    public DeoxysMeteorite(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getWorld().isClient && entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrawling()) {
            Pokemon pokemon = pk.getPokemon();

            if (pokemon.getSpecies().getName().equals("Deoxys")) {
                if (pokemon.getAspects().contains("normal-forme")) {
                    new StringSpeciesFeature("meteorite_forme", "attack").apply(pokemon);
                } else if (pokemon.getAspects().contains("attack-forme")) {
                    new StringSpeciesFeature("meteorite_forme", "speed").apply(pokemon);
                } else if (pokemon.getAspects().contains("speed-forme")) {
                    new StringSpeciesFeature("meteorite_forme", "defense").apply(pokemon);
                } else if (pokemon.getAspects().contains("defense-forme")) {
                    new StringSpeciesFeature("meteorite_forme", "normal").apply(pokemon);
                }

                stack.decrementUnlessCreative(1, user);
                return ActionResult.SUCCESS;
            }
        }

        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.deoxys_meteorite.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
