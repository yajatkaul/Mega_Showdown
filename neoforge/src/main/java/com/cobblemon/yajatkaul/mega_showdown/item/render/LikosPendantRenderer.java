package com.cobblemon.yajatkaul.mega_showdown.item.render;

import com.cobblemon.yajatkaul.mega_showdown.item.custom.LikosPendant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

public class LikosPendantRenderer {
    @SubscribeEvent
    public static void onRenderLiving(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity entity = event.getEntity();
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.CHEST);

        if (stack.getItem() instanceof LikosPendant && event.getRenderer().getModel() instanceof HumanoidModel<?> model) {
            PoseStack poseStack = event.getPoseStack();

            poseStack.pushPose();
            model.body.translateAndRotate(poseStack); // Align to the chest

            renderLikosPendant(poseStack, event.getMultiBufferSource(), stack, entity, event.getPackedLight());

            poseStack.popPose();
        }
    }

    private static void renderLikosPendant(PoseStack poseStack, MultiBufferSource bufferSource,
                                           ItemStack stack, LivingEntity entity, int light) {
        poseStack.pushPose();

        poseStack.translate(0.0f, 1.2f, 0.5);
        poseStack.scale(0.58f, 0.58f, 0.58f);

        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        if (entity.isCrouching()) {
            poseStack.mulPose(Axis.XP.rotationDegrees(30));
            poseStack.translate(0.0f, 0.4f, 0.07f);
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        // Render the item
        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY,
                poseStack, bufferSource, entity.level(), 0
        );

        poseStack.popPose();
    }
}
