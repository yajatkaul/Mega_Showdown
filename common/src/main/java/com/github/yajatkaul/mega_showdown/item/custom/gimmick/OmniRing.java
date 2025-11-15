package com.github.yajatkaul.mega_showdown.item.custom.gimmick;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.data.SlotTypeLoader;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class OmniRing extends ToolTipItem {
    public OmniRing(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        AccessoriesCapability capability = AccessoriesCapability.get(player);

        if (level.isClientSide || capability == null) {
            return InteractionResultHolder.pass(stack);
        }

        List<String> slots = List.of("dynamax_slot", "tera_slot", "z_slot", "mega_slot");

        for (String slotName : slots) {
            AccessoriesContainer slot = capability.getContainer(SlotTypeLoader.getSlotType(level, slotName));
            ExpandedSimpleContainer accessories = slot.getAccessories();

            if (accessories == null) {
                MegaShowdown.LOGGER.info("No {} found", slot);
                return InteractionResultHolder.pass(stack);
            }

            for (int i = 0; i < accessories.getContainerSize(); i++) {
                if (accessories.getItem(i).isEmpty()) {
                    accessories.setItem(i, stack.copy());
                    player.setItemInHand(hand, ItemStack.EMPTY);

                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ARMOR_EQUIP_GENERIC.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
                    return InteractionResultHolder.success(stack);
                }
            }
        }

        return InteractionResultHolder.pass(stack);
    }
}
