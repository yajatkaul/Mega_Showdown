package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MaxSoup extends Item {
    public MaxSoup(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity context, InteractionHand arg4) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (context instanceof PokemonEntity pk) {
            Pokemon pokemon = pk.getPokemon();
            if (pokemon.getEntity() == null || pokemon.getEntity().level().isClientSide || pokemon.getEntity().isBattling()) {
                return InteractionResult.PASS;
            }

            if (!Utils.GMAX_SPECIES.contains(pokemon.getSpecies().getName())) {
                return InteractionResult.PASS;
            }

            if (pokemon.getOwnerPlayer() == player && pokemon.getGmaxFactor()) {
                pokemon.setGmaxFactor(false);

                player.setItemInHand(arg4, new ItemStack(Items.BOWL));
                Vec3 pos = pk.position();

                player.level().playSound(
                        null, pos.x, pos.y, pos.z,
                        SoundEvents.GENERIC_DRINK,
                        SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
                );


                player.displayClientMessage(Component.translatable("message.mega_showdown.gmax_not_possible")
                        .withColor(0xFFFFFF), true);
                return InteractionResult.SUCCESS;
            } else if (pokemon.getOwnerPlayer() == player && !pokemon.getGmaxFactor()) {
                pokemon.setGmaxFactor(true);

                player.setItemInHand(arg4, new ItemStack(Items.BOWL));
                Vec3 pos = pk.position();

                player.level().playSound(
                        null, pos.x, pos.y, pos.z,
                        SoundEvents.GENERIC_DRINK,
                        SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
                );

                player.displayClientMessage(Component.translatable("message.mega_showdown.gmax_possible")
                        .withColor(0xFFFFFF), true);
                return InteractionResult.SUCCESS;
            }
        }

        return super.interactLivingEntity(stack, player, context, arg4);
    }

    @Override
    public void appendHoverText(ItemStack arg, TooltipContext arg2, List<Component> tooltipComponents, TooltipFlag arg3) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.max_soup.tooltip"));
        super.appendHoverText(arg, arg2, tooltipComponents, arg3);
    }
}
