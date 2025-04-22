package com.cobblemon.yajatkaul.mega_showdown.screen.custom;

import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class SlotItemType extends Slot {
    public SlotItemType(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        // Add restrictions for the items here
        if (this.getIndex() == 0) {
            return stack.isOf(FormeChangeItems.ZYGARDE_CELL);
        } else if (this.getIndex() == 1) {
            return stack.isOf(FormeChangeItems.ZYGARDE_CORE);
        }
        return false;
    }
}
