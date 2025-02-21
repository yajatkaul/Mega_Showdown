package com.cobblemon.yajatkaul.mega_showdown.curios;

import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class ChestRenderer {
    public static void onRenderPlayer(RenderLivingEvent.Pre<?, ?> event) {
        LivingEntity entity = event.getEntity();
        ItemStack chestItem = entity.getItemBySlot(EquipmentSlot.CHEST);

        if (chestItem.getItem() instanceof MegaBraceletItem) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();

            // Apply entity transformations to make it rotate/move with the player
            poseStack.translate(0.0, entity.getBbHeight() - (entity.getBbHeight() * 0.09), 0.0); // Move to the chest position
            poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-entity.yBodyRot)); // Rotate with player
            poseStack.mulPose(Axis.YP.rotationDegrees(180));

            poseStack.scale(0.58f, 0.58f, 0.58f); // Scale item

            // Render the item
            Minecraft mc = Minecraft.getInstance();
            MultiBufferSource buffer = event.getMultiBufferSource();
            mc.getItemRenderer().renderStatic(
                    chestItem, ItemDisplayContext.HEAD, event.getPackedLight(), event.getPackedLight(), poseStack, buffer, entity.level(), 0
            );

            poseStack.popPose();
        }
    }
}

