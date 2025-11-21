package com.github.yajatkaul.mega_showdown.components;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public record InventoryStorage(
        CompoundTag tag,
        int count
) {
    public static final Codec<InventoryStorage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CompoundTag.CODEC.fieldOf("tag").forGetter(InventoryStorage::tag),
            Codec.INT.fieldOf("count").forGetter(InventoryStorage::count)
    ).apply(instance, InventoryStorage::new));

    public static InventoryStorage defaultStorage(int count) {
        return new InventoryStorage(new CompoundTag(), count);
    }

    public InventoryStorage save(HolderLookup.Provider registryAccess, SimpleContainer inventory) {
        if (registryAccess == null) {
            MegaShowdown.LOGGER.error("Registry Access is null during serialization");
            return new InventoryStorage(new CompoundTag(), count);
        }

        CompoundTag tag = new CompoundTag();
        ListTag itemsList = new ListTag();

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag entry = new CompoundTag();
                entry.putByte("Slot", (byte) i);

                Tag encodedStack = stack.save(registryAccess);
                entry.put("Item", encodedStack);

                itemsList.add(entry);
            }
        }

        tag.put("Items", itemsList);
        return new InventoryStorage(tag, count);
    }

    public SimpleContainer getInventory(HolderLookup.Provider registryAccess) {
        if (registryAccess == null) {
            MegaShowdown.LOGGER.error("Registry Access is null deserialization");
            return new SimpleContainer(1);
        }
        SimpleContainer inventory = new SimpleContainer(count);
        ListTag itemsList = tag.getList("Items", Tag.TAG_COMPOUND);

        for (int i = 0; i < itemsList.size(); i++) {
            CompoundTag entry = itemsList.getCompound(i);
            int slot = entry.getByte("Slot") & 255;

            CompoundTag itemTag = entry.getCompound("Item");

            Optional<ItemStack> optionalStack = ItemStack.parse(registryAccess, itemTag);

            optionalStack.ifPresent(stack -> {
                if (!stack.isEmpty() && slot < inventory.getContainerSize()) {
                    inventory.setItem(slot, stack);
                }
            });
        }

        return inventory;
    }
}
