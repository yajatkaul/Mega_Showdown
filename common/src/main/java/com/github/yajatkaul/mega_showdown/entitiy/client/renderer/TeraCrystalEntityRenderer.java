package com.github.yajatkaul.mega_showdown.entitiy.client.renderer;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.entitiy.custom.TeraCrystalEntity;
import com.github.yajatkaul.mega_showdown.entitiy.custom.TeraHatEntity;
import com.github.yajatkaul.mega_showdown.entitiy.model.TeraCrystalModel;
import com.github.yajatkaul.mega_showdown.entitiy.model.WaterTeraHatModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TeraCrystalEntityRenderer extends EntityRenderer<TeraCrystalEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/entity/tera_crystal.png");

    private final TeraCrystalModel model;

    public TeraCrystalEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new TeraCrystalModel(context.bakeLayer(TeraCrystalModel.LAYER_LOCATION));
    }

    @Override
    public void render(TeraCrystalEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        poseStack.pushPose();

        model.setupAnim(entity, 20, 20, 20, 20, 20);
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
    public @NotNull ResourceLocation getTextureLocation(TeraCrystalEntity entity) {
        return TEXTURE;
    }
}
