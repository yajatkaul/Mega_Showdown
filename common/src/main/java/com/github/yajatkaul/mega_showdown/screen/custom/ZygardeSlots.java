package com.github.yajatkaul.mega_showdown.screen.custom;

import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ZygardeSlots extends Slot {
    public ZygardeSlots(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        int index = this.getContainerSlot();
        if (index == 0) {
            return stack.is(MegaShowdownItems.ZYGARDE_CELL.get());
        } else if (index == 1) {
            return stack.is(MegaShowdownItems.ZYGARDE_CORE.get());
        }
        return false;
    }

    @Override
    public int getMaxStackSize() {
        int index = this.getContainerSlot();
        if (index == 0) {
            return 95;
        } else {
            return 5;
        }
    }
}