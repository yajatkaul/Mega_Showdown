package com.cobblemon.yajatkaul.mega_showdown.screen.custom;

import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SlotItemType extends SlotItemHandler {
    public SlotItemType(ItemStackHandler handler, int index, int x, int y) {
        super(handler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        // Add restrictions for the items here
        if (this.getSlotIndex() == 0) {
            return stack.is(FormeChangeItems.ZYGARDE_CELL);
        } else if (this.getSlotIndex() == 1) {
            return stack.is(FormeChangeItems.ZYGARDE_CORE);
        }
        return false;
    }
}