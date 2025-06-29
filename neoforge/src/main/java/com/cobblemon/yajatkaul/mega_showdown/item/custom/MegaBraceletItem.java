package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.MegaLogic;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Optional;


public class MegaBraceletItem extends Item {
    public MegaBraceletItem(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);

            // Get the player's Curios inventory
            Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(player);

            if (curios.isPresent()) {
                ICuriosItemHandler handler = curios.get();

                // Check if the "evowear" slot exists
                handler.getStacksHandler("evowear").ifPresent(slotHandler -> {
                    IItemHandlerModifiable stacks = slotHandler.getStacks(); // Get modifiable stack handler

                    for (int i = 0; i < stacks.getSlots(); i++) {
                        if (stacks.getStackInSlot(i).isEmpty()) { // Correct way to access slot
                            // Equip the item
                            stacks.setStackInSlot(i, stack.copy());
                            player.setItemInHand(hand, ItemStack.EMPTY); // Remove from hand

                            // Play equip sound
                            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.PLAYERS, 1.0f, 1.0f);
                            break;
                        }
                    }
                });
            }

            return InteractionResultHolder.success(stack);
        }

        return super.use(world, player, hand);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, @NotNull LivingEntity context, InteractionHand hand) {
        if (player.level().isClientSide || MegaShowdownConfig.battleModeOnly) {
            return InteractionResult.PASS;
        }

        if (context instanceof PokemonEntity pk) {
            Pokemon pokemon = pk.getPokemon();
            if (pokemon.getEntity() == null || pokemon.getEntity().level().isClientSide) {
                return InteractionResult.PASS;
            }

            MegaLogic.EvoLogic(player);
        }

        return InteractionResult.PASS;
    }


    @Override
    public void appendHoverText(ItemStack arg, TooltipContext arg2, List<Component> tooltipComponents, TooltipFlag arg3) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.megabracelet.tooltip"));
        super.appendHoverText(arg, arg2, tooltipComponents, arg3);
    }
}
