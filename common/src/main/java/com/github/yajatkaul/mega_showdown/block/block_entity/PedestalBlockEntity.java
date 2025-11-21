package com.github.yajatkaul.mega_showdown.block.block_entity;

import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PedestalBlockEntity extends BlockEntity {
    public final SimpleContainer inventory = new SimpleContainer(1);

    public PedestalBlockEntity(BlockPos pos, BlockState blockState) {
        super(MegaShowdownBlockEntities.PEDESTAL_BLOCK_ENTITY.get(), pos, blockState);
    }

    public void clearContents() {
        inventory.setItem(0, ItemStack.EMPTY);
        sync();
    }

    public void drops() {
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", save(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.setItem(0, getInventory(registries, tag).getItem(0));
    }

    public Tag save(HolderLookup.Provider registryAccess) {
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
        return tag;
    }

    public SimpleContainer getInventory(HolderLookup.Provider registryAccess, CompoundTag tag) {
        SimpleContainer inventory = new SimpleContainer(1);
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

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    public void sync() {
        setChanged();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
}