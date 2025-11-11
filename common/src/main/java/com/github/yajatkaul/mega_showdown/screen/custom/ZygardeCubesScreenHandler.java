package com.github.yajatkaul.mega_showdown.screen.custom;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import com.github.yajatkaul.mega_showdown.item.custom.form_change.ZygardeCube;
import com.github.yajatkaul.mega_showdown.screen.MegaShowdownMenuTypes;
import com.github.yajatkaul.mega_showdown.utils.NBTInventoryUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ZygardeCubesScreenHandler extends AbstractContainerMenu {
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 2;
    private final ItemStack cube;
    private final SimpleContainer cubeInv;

    public ZygardeCubesScreenHandler(int id, Inventory inv) {
        super(MegaShowdownMenuTypes.ZYGARDE_CUBE_MENU.get(), id);
        this.cube = ItemStack.EMPTY;
        this.cubeInv = new SimpleContainer(2);

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // Add custom inventory slots
        this.addSlot(new ZygardeSlots(this.cubeInv, 0, 62, 36));
        this.addSlot(new ZygardeSlots(this.cubeInv, 1, 98, 36));
    }

    public ZygardeCubesScreenHandler(int id, Inventory inv, ItemStack cube) {
        super(MegaShowdownMenuTypes.ZYGARDE_CUBE_MENU.get(), id);
        this.cube = cube;

        CompoundTag compoundTag = cube.get(MegaShowdownDataComponents.NBT_COMPONENT.get());
        if (compoundTag == null) {
            compoundTag = new CompoundTag();
        }
        cube.set(MegaShowdownDataComponents.NBT_COMPONENT.get(), compoundTag);
        cubeInv = NBTInventoryUtils.deserializeInventory(compoundTag);

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new ZygardeSlots(this.cubeInv, 0, 62, 36));
        this.addSlot(new ZygardeSlots(this.cubeInv, 1, 98, 36));
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if we're trying to move a Zygarde Cube from player inventory
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT &&
                sourceStack == this.cube) {
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

        if (cube.getItem() instanceof ZygardeCube) {
            CompoundTag updatedTag = NBTInventoryUtils.serializeInventory(this.cubeInv);
            cube.set(MegaShowdownDataComponents.NBT_COMPONENT.get(), updatedTag);
        }

        return copyOfSourceStack;
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickType, Player player) {
        if (clickType == ClickType.THROW && slotId >= 0 && slotId < slots.size()) {
            Slot slot = slots.get(slotId);
            if (slot.hasItem() && slot.getItem() == this.cube) {
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
}
