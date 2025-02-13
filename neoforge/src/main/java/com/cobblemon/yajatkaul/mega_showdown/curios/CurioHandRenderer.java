package com.cobblemon.yajatkaul.mega_showdown.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CurioHandRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack
            matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light
            , float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)  {

        boolean bl = slotContext.entity().getMainArm() != HumanoidArm.RIGHT; //false if only right hand
        if (!stack.isEmpty()) {
            matrixStack.pushPose();
            if (renderLayerParent.getModel().young) {
                float m = 0.5F;
                matrixStack.translate(0.0F, 0.75F, 0.0F);
                matrixStack.scale(0.5F, 0.5F, 0.5F);
            }

            if(renderLayerParent.getModel() instanceof ArmedModel model){
                model.translateToHand(HumanoidArm.RIGHT, matrixStack);
            }
            matrixStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));

            matrixStack.translate((float)(bl ? -1 : 1) / 16.0F, 0.125F, -0.625F);

            Minecraft.getInstance().getItemRenderer().renderStatic(slotContext.entity(), stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
                    , bl, matrixStack, renderTypeBuffer, slotContext.entity().level(), light, OverlayTexture.NO_OVERLAY, slotContext.entity().getId() + ItemDisplayContext.THIRD_PERSON_RIGHT_HAND.ordinal());
            matrixStack.popPose();
        }
    }
}
