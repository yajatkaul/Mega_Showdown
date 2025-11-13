package com.github.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.github.yajatkaul.mega_showdown.item.custom.PokemonSelectingItem;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DynamaxCandy extends PokemonSelectingItem {
    public DynamaxCandy(Item.Properties arg) {
        super(arg);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity context, InteractionHand hand) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (context instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            if (pokemon.getOwnerPlayer() != player || pokemonEntity.isBattling()) {
                return InteractionResult.PASS;
            }

            if (pokemon.getSpecies().getDynamaxBlocked()) {
                return InteractionResult.PASS;
            }

            if (pokemon.getDmaxLevel() < Cobblemon.config.getMaxDynamaxLevel()) {
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

                return InteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(Component.translatable("message.mega_showdown.dmax_level_cap")
                        .withStyle(ChatFormatting.RED), true);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer player, @NotNull ItemStack itemStack, @NotNull Pokemon pokemon) {
        if (pokemon.getEntity() != null && pokemon.getEntity().isBattling() || player.isCrouching() || pokemon.getOwnerPlayer() != player) {
            return InteractionResultHolder.pass(itemStack);
        }

        if (pokemon.getDmaxLevel() < Cobblemon.config.getMaxDynamaxLevel()) {
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
        } else {
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
