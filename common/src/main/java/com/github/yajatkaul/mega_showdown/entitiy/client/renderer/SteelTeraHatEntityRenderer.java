package com.github.yajatkaul.mega_showdown.entitiy.client.renderer;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.entitiy.custom.TeraHatEntity;
import com.github.yajatkaul.mega_showdown.entitiy.model.SteelTeraHatModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SteelTeraHatEntityRenderer extends EntityRenderer<TeraHatEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/entity/steel_tera_hat.png");

    private final SteelTeraHatModel model;

    public SteelTeraHatEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SteelTeraHatModel(context.bakeLayer(SteelTeraHatModel.LAYER_LOCATION));
    }

    @Override
    public void render(TeraHatEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        poseStack.pushPose();

        model.renderToBuffer(
                poseStack,
                buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity))),
                light,
                OverlayTexture.NO_OVERLAY,
                0xFFFFFFFF
        );

        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(TeraHatEntity entity) {
        return TEXTURE;
    }
}
