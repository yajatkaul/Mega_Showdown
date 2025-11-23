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

        ItemStack stack = inventory.getItem(0);
        if (!stack.isEmpty()) {
            Tag encodedStack = stack.save(registries);
            tag.put("Item", encodedStack);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.contains("Item")) {
            CompoundTag itemTag = tag.getCompound("Item");
            Optional<ItemStack> optionalStack = ItemStack.parse(registries, itemTag);

            optionalStack.ifPresent(stack -> {
                if (!stack.isEmpty()) {
                    inventory.setItem(0, stack);
                }
            });
        }
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