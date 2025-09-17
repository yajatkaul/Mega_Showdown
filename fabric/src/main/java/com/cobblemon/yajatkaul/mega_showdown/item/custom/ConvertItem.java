package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.util.PlayerExtensionsKt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ConvertItem extends Item {
    private final Item convertTo;

    public ConvertItem(Settings settings, Item convertTo) {
        super(settings);
        this.convertTo = convertTo;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (entity instanceof PlayerEntity player && !player.getOffHandStack().equals(stack)) {
            int count = stack.getCount();
            player.getInventory().removeStack(slot);
            ItemStack newStack = new ItemStack(this.convertTo, count);
            if (!player.getInventory().insertStack(slot, newStack)) {
                // For some reason the new item failed to be given to the player, try again.
                PlayerExtensionsKt.giveOrDropItemStack(player, newStack, false);
            }
        }
    }
}
