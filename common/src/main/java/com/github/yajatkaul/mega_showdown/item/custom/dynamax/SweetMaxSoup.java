package com.github.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.item.custom.PokemonSelectingItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SweetMaxSoup extends PokemonSelectingItem {
    public SweetMaxSoup(Properties arg) {
        super(arg);
    }

    @Override
    public @Nullable InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer serverPlayer, @NotNull ItemStack itemStack, @NotNull Pokemon pokemon) {
        if (!canUseOnPokemon(itemStack, pokemon)) {
            return InteractionResultHolder.fail(itemStack);
        }

        if (pokemon.getGmaxFactor()) {
            pokemon.setGmaxFactor(false);

            serverPlayer.displayClientMessage(Component.translatable("message.mega_showdown.gmax_not_possible")
                    .withStyle(ChatFormatting.GREEN), true);
        } else {
            pokemon.setGmaxFactor(true);

            serverPlayer.displayClientMessage(Component.translatable("message.mega_showdown.gmax_possible")
                    .withStyle(ChatFormatting.GREEN), true);
        }
        if (!serverPlayer.isCreative()) {
            serverPlayer.setItemInHand(serverPlayer.getUsedItemHand(), new ItemStack(Items.BOWL));
        }

        PokemonEntity pokemonEntity = pokemon.getEntity();
        if (pokemonEntity != null) {
            Vec3 pos = pokemonEntity.position();

            serverPlayer.level().playSound(
                    null, pos.x, pos.y, pos.z,
                    SoundEvents.GENERIC_DRINK,
                    SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
            );
        } else {
            Vec3 pos = serverPlayer.position();

            serverPlayer.level().playSound(
                    null, pos.x, pos.y, pos.z,
                    SoundEvents.GENERIC_DRINK,
                    SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
            );
        }

        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public boolean canUseOnPokemon(@NotNull ItemStack stack, @NotNull Pokemon pokemon) {
        return pokemon.getSpecies().getName().equals("Urshifu");
    }
}
