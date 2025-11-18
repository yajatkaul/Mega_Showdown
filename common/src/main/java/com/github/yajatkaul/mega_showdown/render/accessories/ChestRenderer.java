package com.github.yajatkaul.mega_showdown.render.accessories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ChestRenderer implements AccessoryRenderer {
    @Override
    public <M extends LivingEntity> void render(
            ItemStack stack,
            SlotReference reference,
            PoseStack poseStack,
            EntityModel<M> model,
            MultiBufferSource multiBufferSource,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        LivingEntity entity = reference.entity();

        float z_axis = 0.0f;

        poseStack.pushPose();

        poseStack.translate(0.0f, -0.25f, z_axis);

        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        poseStack.scale(0.58f, 0.58f, 0.58f);

        if (entity.isCrouching()) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-30));
            poseStack.translate(0.0f, -0.2f, z_axis - 0.4f);
        }

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, entity.level(), 0
        );
        poseStack.popPose();
    }
}
