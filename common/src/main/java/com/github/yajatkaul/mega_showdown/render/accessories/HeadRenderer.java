package com.github.yajatkaul.mega_showdown.render.accessories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HeadRenderer implements AccessoryRenderer {
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
        poseStack.pushPose();
        LivingEntity entity = reference.entity();

        ItemStack headArmor = entity.getItemBySlot(EquipmentSlot.HEAD);
        float z_axis = 0.0f;
        if (!headArmor.isEmpty()) {
            z_axis = -0.05f;
        }

        // Position on head
        if (model instanceof HumanoidModel<?> humanoidModel) {
            humanoidModel.head.translateAndRotate(poseStack);
        } else if (model instanceof EntityModel) {
            // Try to find and use a head part if available
            // This is a fallback for non-humanoid models
            poseStack.translate(0.0F, 0.25F, z_axis);
        }

        // Position adjustments for the item
        poseStack.translate(0.0F, -0.25F, z_axis);
        poseStack.scale(0.625F, 0.625F, 0.625F);

        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        // Render the item
        Minecraft.getInstance().getItemRenderer().renderStatic(
                entity,
                stack,
                ItemDisplayContext.HEAD,
                false,
                poseStack,
                multiBufferSource,
                entity.level(),
                light,
                OverlayTexture.NO_OVERLAY,
                0
        );

        poseStack.popPose();
    }
}
