package com.github.yajatkaul.mega_showdown.mixin.client;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.codec.item.ItemRenderingCodec;
import com.github.yajatkaul.mega_showdown.codec.item.PerspectivesCodec;
import com.github.yajatkaul.mega_showdown.render.ItemRenderingLoader;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    @Shadow
    public abstract ItemModelShaper getItemModelShaper();

    @ModifyVariable(
            method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    public BakedModel modifyModel(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ItemDisplayContext renderMode) {
        for (ItemRenderingCodec itemRender: ItemRenderingLoader.REGISTRY) {
            Item item = BuiltInRegistries.ITEM.get(itemRender.itemId());
            PerspectivesCodec perspectives = itemRender.perspectivesCodec();
            if (stack.getItem() == item) {
                if (renderMode == ItemDisplayContext.GUI) {
                    return getItemModelShaper().getModelManager().getModel(ModelResourceLocation.inventory(perspectives.guiLoc()));
                } else if (renderMode == ItemDisplayContext.HEAD) {
                    return getItemModelShaper().getModelManager().getModel(ModelResourceLocation.inventory(perspectives.headLoc()));
                } else if (renderMode == ItemDisplayContext.GROUND) {
                    return getItemModelShaper().getModelManager().getModel(ModelResourceLocation.inventory(perspectives.groundLoc()));
                } else if (renderMode == ItemDisplayContext.FIXED) {
                    return getItemModelShaper().getModelManager().getModel(ModelResourceLocation.inventory(perspectives.fixedLoc()));
                }
            }
        }
        return bakedModel;
    }

    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    public BakedModel getHeldItemModelMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {
        for (ItemRenderingCodec itemRender: ItemRenderingLoader.REGISTRY) {
            Item item = BuiltInRegistries.ITEM.get(itemRender.itemId());
            if (stack.getItem() == item) {
                return this.itemModelShaper.getModelManager().getModel(ModelResourceLocation.inventory(itemRender.itemId_3d()));
            }
        }
        return bakedModel;
    }
}
