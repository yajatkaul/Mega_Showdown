package com.cobblemon.yajatkaul.mega_showdown.item.impl;

import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.api.item.PokemonSelectingItem;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.item.battle.BagItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MSDPokemonSelectingItem extends Item implements PokemonSelectingItem {
    public MSDPokemonSelectingItem(Properties settings) {
        super(settings);
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use (@NotNull ServerPlayer player, @NotNull ItemStack itemStack) {
        return DefaultImpls.use(this, player, itemStack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use (Level world, Player user, InteractionHand hand) {
        if (user instanceof ServerPlayer serverPlayer) {
            return this.use(serverPlayer, serverPlayer.getItemInHand(hand));
        }
        return InteractionResultHolder.success(user.getItemInHand(hand));
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> interactGeneral (@NotNull ServerPlayer player, @NotNull ItemStack itemStack) {
        return DefaultImpls.interactGeneral(this, player, itemStack);
    }

    @Nullable
    @Override
    public BagItem getBagItem () {
        return null;
    }

    @Override
    public boolean canUseOnBattlePokemon (@NotNull BattlePokemon battlePokemon) {
        return false;
    }

    @Override
    public void applyToBattlePokemon (@NotNull ServerPlayer player, @NotNull ItemStack itemStack, @NotNull BattlePokemon battlePokemon) {

    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> interactWithSpecificBattle (@NotNull ServerPlayer player, @NotNull ItemStack itemStack, @NotNull BattlePokemon battlePokemon) {
        return InteractionResultHolder.fail(itemStack);
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> interactGeneralBattle (@NotNull ServerPlayer player, @NotNull ItemStack itemStack, @NotNull BattleActor battleActor) {
        return InteractionResultHolder.fail(itemStack);
    }
}
