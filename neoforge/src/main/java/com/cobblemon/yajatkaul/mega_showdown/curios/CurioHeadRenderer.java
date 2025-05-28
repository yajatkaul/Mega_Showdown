package com.cobblemon.yajatkaul.mega_showdown.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CurioHeadRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack
            matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light
            , float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!stack.isEmpty()) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();

            ItemStack headArmor = entity.getItemBySlot(EquipmentSlot.HEAD);
            float z_axis = 0.0f;
            if (!headArmor.isEmpty()) {
                z_axis = -0.05f;
            }

            // Scale for baby entities
            if (renderLayerParent.getModel().young) {
                matrixStack.translate(0.0F, 0.75F, z_axis);
                matrixStack.scale(0.5F, 0.5F, 0.5F);
            }

            // Position on head
            if (renderLayerParent.getModel() instanceof HumanoidModel<?>) {
                ((HumanoidModel<?>) renderLayerParent.getModel()).head.translateAndRotate(matrixStack);
            } else if (renderLayerParent.getModel() instanceof EntityModel) {
                // Try to find and use a head part if available
                // This is a fallback for non-humanoid models
                matrixStack.translate(0.0F, 0.25F, z_axis);
            }

            // Position adjustments for the item
            matrixStack.translate(0.0F, -0.25F, z_axis);
            matrixStack.scale(0.625F, 0.625F, 0.625F);

            matrixStack.mulPose(Axis.YP.rotationDegrees(180));
            matrixStack.mulPose(Axis.XP.rotationDegrees(180));

            // Render the item
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    slotContext.entity(),
                    stack,
                    ItemDisplayContext.HEAD,
                    false,
                    matrixStack,
                    renderTypeBuffer,
                    slotContext.entity().level(),
                    light,
                    OverlayTexture.NO_OVERLAY,
                    slotContext.entity().getId() + ItemDisplayContext.HEAD.ordinal());

            matrixStack.popPose();
        }
    }
}