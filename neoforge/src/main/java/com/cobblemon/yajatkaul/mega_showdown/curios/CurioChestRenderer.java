package com.cobblemon.yajatkaul.mega_showdown.curios;

import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
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

public class CurioChestRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack
            matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light
            , float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)  {
        if(stack.isEmpty()){
            return;
        }

        LivingEntity entity = slotContext.entity();

        ItemStack chestArmor = entity.getItemBySlot(EquipmentSlot.CHEST);
        float z_axis = 0.0f;
        if (!chestArmor.isEmpty()) {
            z_axis = -0.0f;
        }

        matrixStack.pushPose();

        matrixStack.translate(0.0f, -0.25f, z_axis);

        matrixStack.mulPose(Axis.YP.rotationDegrees(180));
        matrixStack.mulPose(Axis.XP.rotationDegrees(180));

        matrixStack.scale(0.58f, 0.58f, 0.58f);

        if(entity.isCrouching()){
            matrixStack.mulPose(Axis.XP.rotationDegrees(-30));
            matrixStack.translate(0.0f, -0.2f, z_axis - 0.4f);
        }

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer, entity.level(), 0
        );
        matrixStack.popPose();
    }
}

