package com.github.yajatkaul.mega_showdown.screen.custom;

import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
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
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ZygardeCubesScreenHandler extends AbstractContainerMenu {
    private final ItemStack cube;
    private final SimpleContainer cubeInv;
    private final Level level;

    public ZygardeCubesScreenHandler(int id, Inventory inv) {
        this(id, inv, ItemStack.EMPTY, null);
    }

    public ZygardeCubesScreenHandler(int id, Inventory inv, ItemStack cube, Level level) {
        super(MegaShowdownMenuTypes.ZYGARDE_CUBE_MENU.get(), id);
        this.cube = cube;
        this.level = level;

        CompoundTag compoundTag = cube.get(MegaShowdownDataComponents.NBT_INV.get());
        if (compoundTag == null) {
            compoundTag = new CompoundTag();
        }
        cube.set(MegaShowdownDataComponents.NBT_INV.get(), compoundTag);

        if (level != null) {
            this.cubeInv = NBTInventoryUtils.deserializeInventory(compoundTag, level.registryAccess());
        } else {
            this.cubeInv = new SimpleContainer(2);
        }

        this.addSlot(new ZygardeSlots(this.cubeInv, 0, 62, 36));
        this.addSlot(new ZygardeSlots(this.cubeInv, 1, 98, 36));

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player playerIn, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (invSlot < this.cubeInv.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.cubeInv.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.cubeInv.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return newStack;
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickType, Player player) {
        if (slotId >= 0 && slotId < slots.size()) {
            Slot slot = slots.get(slotId);
            if (slot.hasItem() && slot.getItem() == this.cube) {
                return;
            }
        }

        super.clicked(slotId, dragType, clickType, player);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (level != null) {
            CompoundTag tag = NBTInventoryUtils.serializeInventory(this.cubeInv, level.registryAccess());
            this.cube.set(MegaShowdownDataComponents.NBT_INV.get(), tag);
        }
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
