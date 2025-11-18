package com.github.yajatkaul.mega_showdown.utils;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AccessoriesUtils {
    public static boolean checkTagInAccessories(LivingEntity player, TagKey<Item> tag) {
        return AccessoriesCapability.getOptionally(player)
                .map(capability -> capability.getAllEquipped()
                        .stream()
                        .anyMatch(stack -> stack.stack().is(tag)))
                .orElse(false);
    }

    public static ItemStack findFirstItemWithTag(Player player, TagKey<Item> tag) {
        return AccessoriesCapability.getOptionally(player)
                .map(capability -> capability.getAllEquipped()
                        .stream()
                        .map(SlotEntryReference::stack)
                        .filter(stack -> stack.is(tag))
                        .findFirst()
                        .orElse(ItemStack.EMPTY))
                .orElse(ItemStack.EMPTY);
    }
}
