package com.cobblemon.yajatkaul.mega_showdown.curios.render;

import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CurioHandRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack
            matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light
            , float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (stack.isEmpty()) return;

        boolean hasDMaxItemTrinkets = CuriosApi.getCuriosInventory(slotContext.entity())
                .map(inventory -> inventory
                        .isEquipped(stack2 -> stack2.is(ModTags.Items.DYNAMAX_BAND)))
                .orElse(false);

        boolean hasOmniItemTrinkets = CuriosApi.getCuriosInventory(slotContext.entity())
                .map(inventory -> inventory
                        .isEquipped(stack2 -> stack2.is(ModTags.Items.OMNI_RING)))
                .orElse(false);

        if(hasOmniItemTrinkets && hasDMaxItemTrinkets && !stack.is(ModTags.Items.OMNI_RING)) {
            return;
        }

        LivingEntity entity = slotContext.entity();
        matrixStack.pushPose();

        // Determine which arm is the offhand
        HumanoidArm mainhandArm = (entity.getMainArm() == HumanoidArm.LEFT) ? HumanoidArm.LEFT : HumanoidArm.RIGHT;

        if (renderLayerParent.getModel() instanceof ArmedModel model) {
            model.translateToHand(mainhandArm, matrixStack);

            matrixStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));

            if (mainhandArm == HumanoidArm.LEFT) {
                matrixStack.translate(-1.0F / 16.0F, 1.0F / 8.0F, -10.0F / 16.0F);
            } else {
                matrixStack.translate(1.0F / 16.0F, 1.0F / 8.0F, -10.0F / 16.0F);
            }
        }

        // Use the correct display context based on which hand is the offhand
        ItemDisplayContext context = (mainhandArm == HumanoidArm.RIGHT)
                ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
                : ItemDisplayContext.THIRD_PERSON_LEFT_HAND;

        Minecraft.getInstance().getItemRenderer().renderStatic(
                entity,
                stack,
                context,
                mainhandArm == HumanoidArm.RIGHT,
                matrixStack,
                renderTypeBuffer,
                entity.level(),
                light,
                OverlayTexture.NO_OVERLAY,
                0
        );

        matrixStack.popPose();
    }
}
