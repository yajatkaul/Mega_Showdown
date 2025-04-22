package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.inventory.ItemInventoryUtil;
import com.cobblemon.yajatkaul.mega_showdown.screen.custom.ZygardeCubeMenu;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ZygardeCube extends Item {
    public ZygardeCube(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide || hand == InteractionHand.OFF_HAND) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        ItemStack stack = player.getItemInHand(hand);
        ItemInventoryUtil inventory = new ItemInventoryUtil(stack, player);

        CompoundTag capTag = stack.get(DataManage.ZYGARDE_INV);
        HolderLookup.Provider provider = level.registryAccess();

        ItemStackHandler handler = inventory.getHandler();

        // Deserialize saved inventory if it exists
        if (capTag != null) {
            handler.deserializeNBT(provider, capTag);
        }

        // Serialize current state to send to client
        CompoundTag serializedTag = handler.serializeNBT(provider);

        player.openMenu(
                new SimpleMenuProvider(
                        (id, playerInventory, playerEntity) ->
                                new ZygardeCubeMenu(id, playerInventory, handler),
                        Component.translatable("menu.zygade_cube")
                ),
                buf -> buf.writeNbt(serializedTag)
        );

        return InteractionResultHolder.success(stack);
    }
}
