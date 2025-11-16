package com.github.yajatkaul.mega_showdown.render.accessories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BeltRenderer implements AccessoryRenderer {
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

        ItemStack chestArmor = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggingArmor = entity.getItemBySlot(EquipmentSlot.LEGS);

        float z_axis = -0.2f;
        if (!chestArmor.isEmpty() || !leggingArmor.isEmpty()) {
            z_axis = -0.27f;
        }

        poseStack.pushPose();

        poseStack.translate(-0.15f, 0.55f, z_axis);

        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        poseStack.scale(0.18f, 0.18f, 0.18f); // Scale item

        if (entity.isCrouching()) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-30));
            poseStack.translate(-0.1f, -1.2f, 0.72f);
        }

        // Render the item
        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, entity.level(), 0
        );
        poseStack.popPose();
    }
}
