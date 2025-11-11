package com.github.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.github.yajatkaul.mega_showdown.item.custom.PokemonSelectingItem;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DynamaxCandy extends PokemonSelectingItem {
    public DynamaxCandy(Item.Properties arg) {
        super(arg);
    }

    @Override
    public void appendHoverText(ItemStack arg, Item.TooltipContext arg2, List<Component> tooltipComponents, TooltipFlag arg3) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dynamax_candy.tooltip"));
        super.appendHoverText(arg, arg2, tooltipComponents, arg3);
    }

    @Nullable
    @Override
    public InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer player, @NotNull ItemStack itemStack, @NotNull Pokemon pokemon) {
        if (pokemon.getEntity() != null && pokemon.getEntity().isBattling() || player.isCrouching() || pokemon.getOwnerPlayer() != player) {
            return InteractionResultHolder.pass(itemStack);
        }

        if (pokemon.getDmaxLevel() < 10) {
            pokemon.setDmaxLevel(pokemon.getDmaxLevel() + 1);

            player.displayClientMessage(Component.translatable("message.mega_showdown.dmax_level_up", pokemon.getDisplayName(false), pokemon.getDmaxLevel())
                    .withStyle(ChatFormatting.GREEN), true);

            if (pokemon.getDmaxLevel() == Cobblemon.config.getMaxDynamaxLevel()) {
                AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "dynamax/dynamax_candy_max");
            }
            if (pokemon.getSpecies().getName().equals("Calyrex")) {
                ParticlesList.calyrexDynamaxLevelUpParticles.apply(pokemon.getEntity());
            } else {
                ParticlesList.otherDynamaxLevelUpParticles.apply(pokemon.getEntity());
            }
            itemStack.consume(1, player);

            return InteractionResultHolder.success(itemStack);
        } else if (pokemon.getDmaxLevel() >= Cobblemon.config.getMaxDynamaxLevel()) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.dmax_level_cap")
                    .withStyle(ChatFormatting.RED), true);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public boolean canUseOnPokemon(@NotNull ItemStack stack, @NotNull Pokemon pokemon) {
        return pokemon.getDmaxLevel() < Cobblemon.config.getMaxDynamaxLevel() && !pokemon.getSpecies().getDynamaxBlocked();
    }
}
