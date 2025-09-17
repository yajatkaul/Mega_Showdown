package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.util.PlayerExtensionsKt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ConvertItem extends Item {
    private final Item convertTo;

    public ConvertItem(Properties arg, Item convertTo) {
        super(arg);
        this.convertTo = convertTo;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level arg2, Entity entity, int slot, boolean bl) {
        super.inventoryTick(stack, arg2, entity, slot, bl);

        if (entity instanceof Player player && !player.getOffhandItem().equals(stack)) {
            int count = stack.getCount();
            player.getInventory().removeItemNoUpdate(slot);
            ItemStack newStack = new ItemStack(this.convertTo, count);
            if (!player.getInventory().add(slot, newStack)) {
                // For some reason the new item failed to be given to the player, try again.
                PlayerExtensionsKt.giveOrDropItemStack(player, newStack, false);
            }
        }
    }
}
