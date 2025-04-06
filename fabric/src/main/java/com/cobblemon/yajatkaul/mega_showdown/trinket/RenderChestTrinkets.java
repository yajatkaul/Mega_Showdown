package com.cobblemon.yajatkaul.mega_showdown.trinket;

import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;

public class RenderChestTrinkets implements TrinketRenderer {
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {

        ItemStack chestArmor = entity.getEquippedStack(EquipmentSlot.CHEST);

        float z_axis = 0.0f;
        if (!chestArmor.isEmpty()) {
            z_axis = -0.05f;
        }
        if (stack.getItem() instanceof MegaBraceletItem) {
            matrices.push();

            matrices.translate(0.0, -0.25, z_axis); // Adjust this value as needed

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

            matrices.scale(0.58f, 0.58f, 0.58f); // Scale item

            // Render the item
            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    stack, ModelTransformationMode.HEAD, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0
            );
            matrices.pop();
        }
    }
}
