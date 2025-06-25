package com.cobblemon.yajatkaul.mega_showdown.curios.render;

import com.cobblemon.yajatkaul.mega_showdown.item.KeyItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class LikosPendantLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public LikosPendantLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        ItemStack chestStack = entity.getItemBySlot(EquipmentSlot.CHEST);

        if (chestStack.isEmpty() || !chestStack.is(KeyItems.LIKOS_PENDANT)) {
            return;
        }

        poseStack.pushPose();

        poseStack.translate(0.0F, 0.3F, -0.15F);
        poseStack.scale(0.58F, 0.58F, 0.58F);

        if (entity.isCrouching()) {
            poseStack.mulPose(Axis.XP.rotationDegrees(30));
            poseStack.translate(0.0F, 0.4F, 0.07F);
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        Minecraft.getInstance().getItemRenderer().renderStatic(
                chestStack,
                ItemDisplayContext.HEAD,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                entity.level(),
                entity.getId()
        );

        poseStack.popPose();
    }
}
