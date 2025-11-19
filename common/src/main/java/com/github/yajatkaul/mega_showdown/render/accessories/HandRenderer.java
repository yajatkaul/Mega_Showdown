package com.github.yajatkaul.mega_showdown.render.accessories;

import com.github.yajatkaul.mega_showdown.tag.MegaShowdownTags;
import com.github.yajatkaul.mega_showdown.utils.AccessoriesUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HandRenderer implements AccessoryRenderer {
    @Override
    public <M extends LivingEntity> void render(
            ItemStack stack,
            SlotReference reference,
            PoseStack poseStack,
            EntityModel<M> model,
            MultiBufferSource multiBufferSource,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        LivingEntity entity = reference.entity();

        ItemStack handStack = entity.getItemInHand(entity.getUsedItemHand());
        boolean hasOmniRing = AccessoriesUtils.checkTagInAccessories(entity, MegaShowdownTags.Items.OMNI_RING);

        if ((hasOmniRing && stack.is(MegaShowdownTags.Items.DYNAMAX_BAND)) || handStack.is(MegaShowdownTags.Items.MEGA_BRACELET) || handStack.is(MegaShowdownTags.Items.Z_RING) || handStack.is(MegaShowdownTags.Items.DYNAMAX_BAND) || handStack.is(MegaShowdownTags.Items.OMNI_RING)) {
            return;
        }

        poseStack.pushPose();

        // Determine which arm is the offhand
        HumanoidArm mainHandArm = (entity.getMainArm() == HumanoidArm.RIGHT) ? HumanoidArm.RIGHT : HumanoidArm.LEFT;

        if (model instanceof ArmedModel armedModel) {
            armedModel.translateToHand(mainHandArm, poseStack);

            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));

            if (mainHandArm == HumanoidArm.RIGHT) {
                poseStack.translate(-1.0F / 16.0F, 1.0F / 8.0F, 3.0F / 16.0F);
            } else {
                poseStack.translate(1.0F / 16.0F, 1.0F / 8.0F, 3.0F / 16.0F);
            }
        }

        ItemDisplayContext context = (mainHandArm == HumanoidArm.RIGHT)
                ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
                : ItemDisplayContext.THIRD_PERSON_LEFT_HAND;

        Minecraft.getInstance().getItemRenderer().renderStatic(
                entity,
                stack,
                context,
                mainHandArm == HumanoidArm.RIGHT,
                poseStack,
                multiBufferSource,
                entity.level(),
                light,
                OverlayTexture.NO_OVERLAY,
                0
        );

        poseStack.popPose();
    }
}
