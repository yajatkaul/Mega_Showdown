package com.github.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class SweetMaxSoup extends ToolTipItem {
    public SweetMaxSoup(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity context, InteractionHand arg4) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (context instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            if (pokemonEntity.isBattling() || !pokemon.getSpecies().getName().equals("Urshifu") || pokemon.getOwnerPlayer() != player) {
                return InteractionResult.PASS;
            }

            if (pokemon.getGmaxFactor()) {
                pokemon.setGmaxFactor(false);

                if (!player.isCreative()) {
                    player.setItemInHand(arg4, new ItemStack(Items.BOWL));
                }
                Vec3 pos = pokemonEntity.position();

                player.level().playSound(
                        null, pos.x, pos.y, pos.z,
                        SoundEvents.GENERIC_DRINK,
                        SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
                );

                player.displayClientMessage(Component.translatable("message.mega_showdown.gmax_not_possible")
                        .withColor(0xFFFFFF), true);
                return InteractionResult.SUCCESS;
            } else {
                pokemon.setGmaxFactor(true);

                if (!player.isCreative()) {
                    player.setItemInHand(arg4, new ItemStack(Items.BOWL));
                }
                Vec3 pos = pokemonEntity.position();

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
}
