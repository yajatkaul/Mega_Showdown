package com.github.yajatkaul.mega_showdown.utils;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class NBTInventoryUtils {
    public static CompoundTag serializeInventory(SimpleContainer inventory) {
        CompoundTag tag = new CompoundTag();
        ListTag itemsList = new ListTag();

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag entry = new CompoundTag();
                entry.putByte("Slot", (byte) i);

                Tag encodedStack = stack.save(MegaShowdown.getServer().registryAccess());
                entry.put("Item", encodedStack);

                itemsList.add(entry);
            }
        }

        tag.put("Items", itemsList);
        return tag;
    }

    public static SimpleContainer deserializeInventory(CompoundTag tag) {
        SimpleContainer inventory = new SimpleContainer(2);
        ListTag itemsList = tag.getList("Items", Tag.TAG_COMPOUND);

        for (int i = 0; i < itemsList.size(); i++) {
            CompoundTag entry = itemsList.getCompound(i);
            int slot = entry.getByte("Slot") & 255;

            CompoundTag itemTag = entry.getCompound("Item");

            Optional<ItemStack> optionalStack = ItemStack.parse(MegaShowdown.getServer().registryAccess(), itemTag);

            optionalStack.ifPresent(stack -> {
                if (!stack.isEmpty() && slot < inventory.getContainerSize()) {
                    inventory.setItem(slot, stack);
                }
            });
        }

        return inventory;
    }
}
