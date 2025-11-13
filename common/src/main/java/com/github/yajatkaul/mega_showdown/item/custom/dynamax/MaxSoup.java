package com.github.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.item.custom.PokemonSelectingItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class MaxSoup extends PokemonSelectingItem {
    public MaxSoup(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity context, InteractionHand hand) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (context instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            if (pokemon.getOwnerPlayer() != player || pokemonEntity.isBattling()) {
                return InteractionResult.PASS;
            }

            if (!pokemon.getSpecies().getForms().stream().anyMatch(formData -> formData.getLabels().contains("gmax"))) {
                return InteractionResult.PASS;
            }

            if (pokemon.getGmaxFactor()) {
                pokemon.setGmaxFactor(false);

                if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(Items.BOWL));
                }
                Vec3 pos = pokemonEntity.position();

                player.level().playSound(
                        null, pos.x, pos.y, pos.z,
                        SoundEvents.GENERIC_DRINK,
                        SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
                );

                player.displayClientMessage(Component.translatable("message.mega_showdown.gmax_not_possible")
                        .withColor(0xFFFFFF), true);
            } else {
                pokemon.setGmaxFactor(true);

                if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(Items.BOWL));
                }
                Vec3 pos = pokemonEntity.position();

                player.level().playSound(
                        null, pos.x, pos.y, pos.z,
                        SoundEvents.GENERIC_DRINK,
                        SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
                );

                player.displayClientMessage(Component.translatable("message.mega_showdown.gmax_possible")
                        .withColor(0xFFFFFF), true);
            }
            return InteractionResult.SUCCESS;
        }

        return super.interactLivingEntity(stack, player, context, hand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        tooltipComponents.add(Component.translatable("tooltip." + id.getNamespace() + "." + id.getPath() + ".tooltip"));
        super.appendHoverText(itemStack, tooltipContext, tooltipComponents, tooltipFlag);
    }

    @Override
    public @Nullable InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer player, @NotNull ItemStack itemStack, @NotNull Pokemon pokemon) {
        PokemonEntity pokemonEntity = pokemon.getEntity();
        Vec3 pos;
        pos = Objects.requireNonNullElse(pokemonEntity, player).position();

        if (pokemon.getGmaxFactor()) {
            pokemon.setGmaxFactor(false);

            if (!player.isCreative()) {
                itemStack.shrink(1);
                player.addItem(new ItemStack(Items.BOWL));
            }

            player.level().playSound(
                    null, pos.x, pos.y, pos.z,
                    SoundEvents.GENERIC_DRINK,
                    SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
            );

            player.displayClientMessage(Component.translatable("message.mega_showdown.gmax_not_possible")
                    .withColor(0xFFFFFF), true);
        } else {
            pokemon.setGmaxFactor(true);

            if (!player.isCreative()) {
                itemStack.shrink(1);
                player.addItem(new ItemStack(Items.BOWL));
            }

            player.level().playSound(
                    null, pos.x, pos.y, pos.z,
                    SoundEvents.GENERIC_DRINK,
                    SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
            );

            player.displayClientMessage(Component.translatable("message.mega_showdown.gmax_possible")
                    .withColor(0xFFFFFF), true);
        }
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public boolean canUseOnPokemon(@NotNull ItemStack stack, @NotNull Pokemon pokemon) {
        return pokemon.getSpecies().getForms().stream().anyMatch(formData -> formData.getLabels().contains("gmax"));
    }
}
