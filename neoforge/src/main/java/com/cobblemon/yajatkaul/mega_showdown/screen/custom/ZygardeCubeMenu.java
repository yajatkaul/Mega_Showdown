package com.cobblemon.yajatkaul.mega_showdown.screen.custom;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.inventory.ItemInventoryUtil;
import com.cobblemon.yajatkaul.mega_showdown.screen.ModMenuTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ZygardeCubeMenu extends AbstractContainerMenu {
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 2;
    private final ItemStackHandler inventory;

    public ZygardeCubeMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        super(ModMenuTypes.ZYGARDE_CUBE_MENU.get(), id);

        HolderLookup.Provider provider = inv.player.level().registryAccess();

        CompoundTag tag = buf.readNbt();

        this.inventory = new ItemInventoryUtil().getHandler();
        this.inventory.deserializeNBT(provider, tag);

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // Add custom inventory slots
        this.addSlot(new SlotItemType(this.inventory, 0, 62, 36));
        this.addSlot(new SlotItemType(this.inventory, 1, 98, 36));
    }

    public ZygardeCubeMenu(int id, Inventory inv, ItemStackHandler inventory) {
        super(ModMenuTypes.ZYGARDE_CUBE_MENU.get(), id);
        this.inventory = inventory;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new SlotItemType(inventory, 0, 62, 36));
        this.addSlot(new SlotItemType(inventory, 1, 98, 36));
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if we're trying to move a Zygarde Cube from player inventory
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT &&
                sourceStack.is(FormeChangeItems.ZYGARDE_CUBE)) {
            // Close the menu and return empty to prevent the move
            playerIn.closeContainer();
            return ItemStack.EMPTY;
        }

        boolean moved;

        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // Move from player inventory to custom inventory
            moved = moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX,
                    TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false);
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // Move from custom inventory to player inventory
            moved = moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX,
                    VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false);
        } else {
            MegaShowdown.LOGGER.info("Invalid slotIndex: {}", pIndex);
            return ItemStack.EMPTY;
        }

        if (!moved) return ItemStack.EMPTY;

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(playerIn, sourceStack);

        // Force persistence update if the item is the Zygarde Cube
        ItemStack cube = playerIn.getItemInHand(InteractionHand.MAIN_HAND);
        if (cube.is(FormeChangeItems.ZYGARDE_CUBE)) {
            CompoundTag updatedTag = inventory.serializeNBT(playerIn.level().registryAccess());
            cube.set(DataManage.ZYGARDE_INV, updatedTag);
            playerIn.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, cube);
        }

        return copyOfSourceStack;
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickType, Player player) {
        if (clickType == ClickType.THROW && slotId >= 0 && slotId < slots.size()) {
            Slot slot = slots.get(slotId);
            if (slot.hasItem() && slot.getItem().is(FormeChangeItems.ZYGARDE_CUBE)) {
                player.closeContainer();
                return;
            }
        }

        super.clicked(slotId, dragType, clickType, player);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void slotsChanged(Container arg) {
        super.slotsChanged(arg);
    }

    @Override
    public void removed(Player arg) {
        super.removed(arg);
    }
}