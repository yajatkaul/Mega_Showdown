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

        Arm arm = Arm.RIGHT;
        if (entity.getMainArm() != Arm.RIGHT) {
            arm = Arm.LEFT;
        }

        boolean bl = entity.getMainArm() != arm; //false if only right hand
        if (!stack.isEmpty()) {
            matrices.push();
            if (contextModel.child) {
                float m = 0.5F;
                matrices.translate(0.0F, 0.75F, 0.0F);
                matrices.scale(0.5F, 0.5F, 0.5F);
            }

            if (contextModel instanceof BipedEntityModel model) {
                model.rightArm.rotate(matrices);
            }

            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));

            matrices.translate((float) (bl ? -1 : 1) / 16.0F, 0.125F, -0.625F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity, stack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND
                    , bl, matrices, vertexConsumers, entity.getWorld(), light, OverlayTexture.DEFAULT_UV, entity.getId() + ModelTransformationMode.THIRD_PERSON_RIGHT_HAND.ordinal());
            matrices.pop();
        }
    }
}
