package com.cobblemon.yajatkaul.mega_showdown.trinket.renderers;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class RenderBeltTrinkets implements TrinketRenderer {
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (stack.isEmpty()) {
            return;
        }

        ItemStack chestArmor = entity.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggingArmor = entity.getEquippedStack(EquipmentSlot.LEGS);

        float z_axis = -0.2f;
        if (!chestArmor.isEmpty() || !leggingArmor.isEmpty()) {
            z_axis = -0.27f;
        }

        matrices.push();

        matrices.translate(-0.15f, 0.55f, z_axis);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

        matrices.scale(0.18f, 0.18f, 0.18f);

        if (entity.isInSneakingPose()) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-30));
            matrices.translate(-0.1f, -1.2f, 0.72f);
        }
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                stack, ModelTransformationMode.HEAD, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0
        );
        matrices.pop();
    }
}
