package com.cobblemon.yajatkaul.mega_showdown.item.inventory;

import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ItemInventoryUtil {
    private final ItemStackHandler handler;

    public ItemInventoryUtil() {
        this.handler = new ItemStackHandler(2) {
            @Override
            protected int getStackLimit(int slot, ItemStack stack) {
                if (slot == 1) return 5;
                return 95;
            }
        };
    }

    public ItemInventoryUtil(ItemStack stack, Player player) {
        this.handler = new ItemStackHandler(2) {
            @Override
            protected int getStackLimit(int slot, ItemStack stack) {
                if (slot == 1) return 5;
                return 95;
            }

            @Override
            protected void onContentsChanged(int slot) {
                HolderLookup.Provider provider = player.level().registryAccess();
                CompoundTag updatedTag = serializeNBT(provider);
                stack.set(DataManage.ZYGARDE_INV, updatedTag);
                player.setItemInHand(InteractionHand.MAIN_HAND, stack);
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                if (stack.isEmpty()) return ItemStack.EMPTY;

                ItemStack existing = getStackInSlot(slot);
                int limit = getStackLimit(slot, stack);

                if (!ItemStack.isSameItemSameComponents(stack, existing)) {
                    if (!existing.isEmpty()) return stack;
                    if (stack.getCount() > limit) {
                        // Cap the stack to limit
                        ItemStack remainder = stack.copy();
                        if (!simulate) {
                            ItemStack newStack = stack.copy();
                            newStack.setCount(limit);
                            setStackInSlot(slot, newStack);
                        }
                        remainder.setCount(stack.getCount() - limit);
                        return remainder;
                    } else {
                        if (!simulate) {
                            setStackInSlot(slot, stack.copy());
                        }
                        return ItemStack.EMPTY;
                    }
                } else {
                    int combined = existing.getCount() + stack.getCount();
                    if (combined <= limit) {
                        if (!simulate) {
                            existing.setCount(combined);
                            setStackInSlot(slot, existing);
                        }
                        return ItemStack.EMPTY;
                    } else {
                        int accepted = limit - existing.getCount();
                        if (accepted <= 0) return stack;

                        if (!simulate) {
                            existing.setCount(limit);
                            setStackInSlot(slot, existing);
                        }

                        ItemStack remainder = stack.copy();
                        remainder.setCount(stack.getCount() - accepted);
                        return remainder;
                    }
                }
            }
        };
    }

    public ItemStackHandler getHandler() {
        return handler;
    }
}