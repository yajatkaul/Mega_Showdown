package com.cobblemon.yajatkaul.mega_showdown.block.custom.entity.renderer;

import com.cobblemon.yajatkaul.mega_showdown.block.custom.PedestalBlock;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.entity.PedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    public PedestalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(PedestalBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack stack = blockEntity.inventory.getStackInSlot(0);
        if (stack.isEmpty()) return;

        Direction facing = blockEntity.getBlockState().getValue(PedestalBlock.FACING);
        float angle = switch (facing) {
            case NORTH -> 0f;
            case EAST -> 270f;
            case WEST -> 90f;
            default -> 180f;
        };

        poseStack.pushPose();
        poseStack.translate(0.5, 1.15, 0.5);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack,
                ItemDisplayContext.FIXED,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                blockEntity.getLevel(),
                0
        );

        poseStack.popPose();
    }
}