package com.github.yajatkaul.mega_showdown.item.custom.form_change;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.codec.Effect;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipBlockItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class Gracedia extends ToolTipBlockItem {
    public Gracedia(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, LivingEntity context, InteractionHand arg4) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (context instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            if (pokemon.getEntity().isBattling() || pokemon.getOwnerPlayer() != player || pokemon.getPersistentData().contains("form_changing")) {
                return InteractionResult.PASS;
            }

            if (pokemon.getSpecies().getName().equals("Shaymin")) {
                long timeOfDay = player.level().getDayTime() % 24000;
                boolean isDaytime = timeOfDay < 12000;
                boolean isSkyFormActive = pokemon.getAspects().contains("sky-forme");

                if (isDaytime && !isSkyFormActive) {
                    Effect.getEffect("mega_showdown:end_rod").applyEffects(pokemon, List.of("gracidea_forme=sky"), null);
                } else if (!isDaytime && isSkyFormActive) {
                    Effect.getEffect("mega_showdown:end_rod").applyEffects(pokemon, List.of("gracidea_forme=land"), null);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
