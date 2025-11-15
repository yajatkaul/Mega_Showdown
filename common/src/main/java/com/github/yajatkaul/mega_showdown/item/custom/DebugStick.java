package com.github.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DebugStick extends Item {
    public DebugStick(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (livingEntity instanceof PokemonEntity pk) {
            player.displayClientMessage(Component.literal(String.valueOf(pk.getAspects()))
                    .withStyle(ChatFormatting.GREEN), true);
        }

        return InteractionResult.SUCCESS;
    }
}
