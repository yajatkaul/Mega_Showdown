package com.github.yajatkaul.mega_showdown.item.custom.form_change;

import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import com.github.yajatkaul.mega_showdown.screen.custom.ZygardeCubesScreenHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ZygardeCube extends ToolTipItem {
    public ZygardeCube(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        player.openMenu(
                new SimpleMenuProvider(
                        (id, playerInventory, playerEntity) ->
                                new ZygardeCubesScreenHandler(id, playerInventory, stack),
                        Component.translatable("menu.zygade_cube")
                )
        );

        return InteractionResultHolder.pass(stack);
    }
}
