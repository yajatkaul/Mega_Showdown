package com.github.yajatkaul.mega_showdown.mixin.minecraft;

import com.github.yajatkaul.mega_showdown.item.custom.tera.LikosPendant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private ItemStack offHandItem;

    @Shadow
    private ItemStack mainHandItem;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        LocalPlayer localplayer = this.minecraft.player;
        ItemStack itemstack = localplayer.getMainHandItem();
        ItemStack itemstack1 = localplayer.getOffhandItem();

        if (itemstack.getItem() instanceof LikosPendant) {
            this.mainHandItem = itemstack;
        }

        if (itemstack1.getItem() instanceof LikosPendant) {
            this.offHandItem = itemstack1;
        }
    }
}
