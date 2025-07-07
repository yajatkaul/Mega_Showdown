package com.cobblemon.yajatkaul.mega_showdown.screen.custom;

import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.inventory.CubeInventoryListener;
import com.cobblemon.yajatkaul.mega_showdown.screen.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;

public class ZygardeCubeScreenHandler extends ScreenHandler {
    private final SimpleInventory inventory;

    public ZygardeCubeScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(2), null, null);  // Calls the second constructor
    }

    public ZygardeCubeScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PlayerEntity playerEntity, ItemStack itemStack) {
        super(ModScreenHandlers.ZYGARDE_CUBE_SCREEN_HANDLER_TYPE, syncId);
        this.inventory = (SimpleInventory) inventory;

        if (playerEntity != null && itemStack != null && !playerEntity.getWorld().isClient) {
            ((SimpleInventory) inventory).addListener(new CubeInventoryListener(itemStack, playerEntity));
        }

        this.addSlot(new SlotItemType(inventory, 0, 62, 36) {
            @Override
            public int getMaxItemCount() {
                return 95;
            }
        });

        this.addSlot(new SlotItemType(inventory, 1, 98, 36) {
            @Override
            public int getMaxItemCount() {
                return 5;
            }
        });

        // Add player inventory and hotbar
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }


    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        if ((actionType == SlotActionType.THROW || actionType == SlotActionType.PICKUP) && slotIndex >= 0 && slotIndex < slots.size()) {
            Slot slot = slots.get(slotIndex);
            if (slot.hasStack() && slot.getStack().isOf(FormeChangeItems.ZYGARDE_CUBE)) {
                if (player instanceof ServerPlayerEntity serverPlayer) {
                    serverPlayer.closeHandledScreen();
                }
                return;
            }
        }

        super.onSlotClick(slotIndex, button, actionType, player);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}