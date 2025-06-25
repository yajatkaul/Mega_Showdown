package com.cobblemon.yajatkaul.mega_showdown.trinket.renderers;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class LikosPendantRenderer implements ArmorRenderer {
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (stack.isEmpty()) {
            return;
        }

        matrices.push();

        matrices.translate(0.0f, 0.3f, -0.15f);

        matrices.scale(0.58f, 0.58f, 0.58f); // Scale item

        if (entity.isInSneakingPose()) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30));
            matrices.translate(0.0f, 0.4f, 0.07f);
        }

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

        // Render the item
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                stack, ModelTransformationMode.HEAD, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0
        );
        matrices.pop();
    }
}
