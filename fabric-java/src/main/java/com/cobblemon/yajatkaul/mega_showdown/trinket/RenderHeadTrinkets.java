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
import net.minecraft.util.math.RotationAxis;

public class RenderHeadTrinkets implements TrinketRenderer {
    @Override
    public void render(ItemStack stack, SlotReference slotRef, EntityModel<? extends LivingEntity> model,
                       MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
                       float headPitch) {

        if (!stack.isEmpty()) {
            matrixStack.push();

            // Scale for baby entities
            if (model instanceof BipedEntityModel && model.child) {
                matrixStack.translate(0.0F, 0.75F, 0.0F);
                matrixStack.scale(0.5F, 0.5F, 0.5F);
            }

            // Position on head
            if (model instanceof BipedEntityModel) {
                ((BipedEntityModel<?>) model).head.rotate(matrixStack);
            } else {
                // Fallback for non-humanoid models
                matrixStack.translate(0.0F, 0.25F, 0.0F);
            }

            // Position adjustments for the item
            matrixStack.translate(0.0F, -0.25F, 0.0F);
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            matrixStack.scale(0.625F, 0.625F, 0.625F);

            // Render the item
            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    entity,
                    stack,
                    ModelTransformationMode.HEAD,
                    false,
                    matrixStack,
                    vertexConsumers,
                    entity.getWorld(),
                    light,
                    OverlayTexture.DEFAULT_UV,
                    entity.getId() + ModelTransformationMode.HEAD.ordinal());

            matrixStack.pop();
        }
    }
}