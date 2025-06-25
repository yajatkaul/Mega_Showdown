package com.cobblemon.yajatkaul.mega_showdown.item.inventory;

import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zygarde.ZygardeCube;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;

public class CubeInventoryListener implements InventoryChangedListener {
    private final ItemStack stackToUpdate;
    private final PlayerEntity player;

    public CubeInventoryListener(ItemStack stackToUpdate, PlayerEntity player) {
        this.stackToUpdate = stackToUpdate;
        this.player = player;
    }

    @Override
    public void onInventoryChanged(Inventory inventory) {
        stackToUpdate.set(DataManage.ZYGARDE_CUBE_INV, ZygardeCube.serializeInventory((SimpleInventory) inventory,
                player.getWorld().getRegistryManager()));
    }
}