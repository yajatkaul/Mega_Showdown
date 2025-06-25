package com.cobblemon.yajatkaul.mega_showdown.curios.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CurioBeltRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack
            matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light
            , float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (stack.isEmpty()) {
            return;
        }
        LivingEntity entity = slotContext.entity();

        ItemStack chestArmor = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggingArmor = entity.getItemBySlot(EquipmentSlot.LEGS);

        float z_axis = -0.2f;
        if (!chestArmor.isEmpty() || !leggingArmor.isEmpty()) {
            z_axis = -0.27f;
        }

        matrixStack.pushPose();

        matrixStack.translate(-0.15f, 0.55f, z_axis);

        matrixStack.mulPose(Axis.YP.rotationDegrees(180));
        matrixStack.mulPose(Axis.XP.rotationDegrees(180));

        matrixStack.scale(0.18f, 0.18f, 0.18f); // Scale item

        if (entity.isCrouching()) {
            matrixStack.mulPose(Axis.XP.rotationDegrees(-30));
            matrixStack.translate(-0.1f, -1.2f, 0.72f);
        }

        // Render the item
        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer, entity.level(), 0
        );
        matrixStack.popPose();
    }
}

