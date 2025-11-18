package com.github.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.item.battle.BagItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public abstract class PokemonSelectingItem extends ToolTipItem implements com.cobblemon.mod.common.api.item.PokemonSelectingItem {
    public PokemonSelectingItem(Properties settings) {
        super(settings);
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull ServerPlayer player, @NotNull ItemStack itemStack) {
        return DefaultImpls.use(this, player, itemStack);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (user instanceof ServerPlayer serverPlayer) {
            return this.use(serverPlayer, serverPlayer.getItemInHand(hand));
        }
        return InteractionResultHolder.success(user.getItemInHand(hand));
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> interactGeneral(@NotNull ServerPlayer player, @NotNull ItemStack itemStack) {
        return DefaultImpls.interactGeneral(this, player, itemStack);
    }

    @Nullable
    @Override
    public BagItem getBagItem() {
        return null;
    }

    @Override
    public boolean canUseOnBattlePokemon(@NotNull ItemStack stack, @NotNull BattlePokemon battlePokemon) {
        return false;
    }

    @Override
    public void applyToBattlePokemon(@NotNull ServerPlayer player, @NotNull ItemStack itemStack, @NotNull BattlePokemon battlePokemon) {

    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> interactWithSpecificBattle(@NotNull ServerPlayer player, @NotNull ItemStack itemStack, @NotNull BattlePokemon battlePokemon) {
        return InteractionResultHolder.fail(itemStack);
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> interactGeneralBattle(@NotNull ServerPlayer player, @NotNull ItemStack itemStack, @NotNull BattleActor battleActor) {
        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        tooltipComponents.add(Component.translatable("tooltip." + id.getNamespace() + "." + id.getPath() + ".tooltip"));
        super.appendHoverText(itemStack, tooltipContext, tooltipComponents, tooltipFlag);
    }
}
