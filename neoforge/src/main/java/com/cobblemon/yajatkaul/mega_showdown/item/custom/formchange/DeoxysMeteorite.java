package com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class DeoxysMeteorite extends BlockItem {
    public DeoxysMeteorite(Block arg, Properties arg2) {
        super(arg, arg2);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player user, LivingEntity entity, InteractionHand arg4) {
        if (!user.level().isClientSide && entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrouching()) {
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

                arg.consume(1, user);
                return InteractionResult.SUCCESS;
            }
        }

        return super.interactLivingEntity(arg, user, entity, arg4);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.deoxys_meteorite.tooltip"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
