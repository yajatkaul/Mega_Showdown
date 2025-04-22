package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.inventory.CubeInventoryListener;
import com.cobblemon.yajatkaul.mega_showdown.screen.custom.ZygardeCubeScreenHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Optional;

public class ZygardeCube extends Item {
    private final SimpleInventory inventory;

    public ZygardeCube(Settings settings) {
        super(settings);
        this.inventory = new SimpleInventory(2);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient || hand == Hand.OFF_HAND){
            return TypedActionResult.fail(user.getStackInHand(hand));
        }

        RegistryWrapper.WrapperLookup registries = user.getWorld().getRegistryManager();

        ItemStack stack = user.getStackInHand(hand);

        NbtCompound tag = stack.getOrDefault(DataManage.ZYGARDE_CUBE_INV, new NbtCompound());

        deserializeInventory(tag, inventory, registries);

        user.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                (syncId, inv, player) -> new ZygardeCubeScreenHandler(syncId, inv, inventory, player, stack),
                Text.translatable("menu.zygade_cube")
        ));

        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public static NbtCompound serializeInventory(SimpleInventory inventory, RegistryWrapper.WrapperLookup registries) {
        NbtCompound tag = new NbtCompound();
        NbtList itemsList = new NbtList();

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                NbtCompound entry = new NbtCompound();
                entry.putByte("Slot", (byte) i);

                // Use the encode() method to serialize the ItemStack
                NbtElement encodedStack = stack.encode(registries);
                entry.put("Item", encodedStack);

                itemsList.add(entry);
            }
        }

        tag.put("Items", itemsList);
        return tag;
    }

    public static void deserializeInventory(NbtCompound tag, SimpleInventory inventory, RegistryWrapper.WrapperLookup registries) {
        NbtList itemsList = tag.getList("Items", NbtElement.COMPOUND_TYPE);
        inventory.clear();

        for (int i = 0; i < itemsList.size(); i++) {
            NbtCompound entry = itemsList.getCompound(i);
            int slot = entry.getByte("Slot") & 255;

            // Deserialize using ItemStack.fromNbt()
            NbtCompound itemTag = entry.getCompound("Item");

            Optional<ItemStack> optionalStack = ItemStack.fromNbt(registries, itemTag);

            // Handle Optional<ItemStack> properly
            optionalStack.ifPresent(stack -> {
                if (!stack.isEmpty() && slot < inventory.size()) {
                    inventory.setStack(slot, stack);
                }
            });
        }
    }
}
