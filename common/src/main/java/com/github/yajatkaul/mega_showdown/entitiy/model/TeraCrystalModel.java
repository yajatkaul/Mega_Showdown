package com.github.yajatkaul.mega_showdown.entitiy.model;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.entitiy.client.animation.TeraCrystalAnimation;
import com.github.yajatkaul.mega_showdown.entitiy.custom.TeraCrystalEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TeraCrystalModel extends HierarchicalModel<TeraCrystalEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "tera_crystal"), "main");
    private final ModelPart root;

	public TeraCrystalModel(ModelPart root) {
        this.root = root.getChild("root");
	}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -43.0F, -8.0F, 16.0F, 43.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

	@Override
	public void setupAnim(TeraCrystalEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.idleAnimationState, TeraCrystalAnimation.IDLE, ageInTicks);
	}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, int k) {
        root.render(poseStack, vertexConsumer, i, j, k);
    }

    @Override
    public @NotNull ModelPart root() {
        return root;
    }
}