package com.cobblemon.yajatkaul.mega_showdown.trinket;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;

public class RenderHandTrinkets implements TrinketRenderer {
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {

        if (stack.isEmpty()) return;

        // Determine which arm is the offhand (opposite of main hand)
        Arm mainhandArm = (entity.getMainArm() == Arm.LEFT) ? Arm.LEFT : Arm.RIGHT;

        matrices.push();

        // Handle child model scaling
        if (contextModel.child) {
            matrices.translate(0.0F, 0.75F, 0.0F);
            matrices.scale(0.5F, 0.5F, 0.5F);
        }

        // Rotate to the offhand arm position
        if (contextModel instanceof BipedEntityModel model) {
            if (mainhandArm == Arm.LEFT) {
                model.leftArm.rotate(matrices);
            } else {
                model.rightArm.rotate(matrices);
            }
        }

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        if (mainhandArm == Arm.LEFT) {
            matrices.translate(-1.0F / 16.0F, 1.0F / 8.0F, -10.0F / 16.0F);
        } else {
            matrices.translate(1.0F / 16.0F, 1.0F / 8.0F, -10.0F / 16.0F);
        }

        // Use the correct display context based on which hand is the offhand
        ModelTransformationMode context = (mainhandArm == Arm.LEFT)
                ? ModelTransformationMode.THIRD_PERSON_RIGHT_HAND
                : ModelTransformationMode.THIRD_PERSON_LEFT_HAND;

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                entity,
                stack,
                context,
                mainhandArm == Arm.RIGHT,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                light,
                OverlayTexture.DEFAULT_UV,
                entity.getId() + context.ordinal()
        );

        matrices.pop();
    }
}
