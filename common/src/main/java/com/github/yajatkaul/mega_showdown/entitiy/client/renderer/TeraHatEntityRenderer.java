package com.github.yajatkaul.mega_showdown.entitiy.client.renderer;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.entitiy.TeraHatEntity;
import com.github.yajatkaul.mega_showdown.entitiy.TeraHatModel;
import com.github.yajatkaul.mega_showdown.entitiy.client.MegaShowdownLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TeraHatEntityRenderer extends EntityRenderer<TeraHatEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/entity/tera_hat.png");

    private final TeraHatModel model;

    public TeraHatEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new TeraHatModel(context.bakeLayer(MegaShowdownLayers.TERA_HAT_LAYER_LOCATION));
    }

    @Override
    public void render(TeraHatEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        poseStack.pushPose();

        model.renderToBuffer(
                poseStack,
                buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity))),
                light,
                OverlayTexture.NO_OVERLAY,
                0xFFFFFFFF  // Use the int color format, or the 4 float RGBA values depending on your MC version
        );

        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(TeraHatEntity entity) {
        return TEXTURE;
    }
}
