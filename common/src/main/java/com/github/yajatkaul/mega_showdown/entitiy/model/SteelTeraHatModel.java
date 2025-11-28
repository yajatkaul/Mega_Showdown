package com.github.yajatkaul.mega_showdown.entitiy.model;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.entitiy.custom.TeraHatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class SteelTeraHatModel extends EntityModel<TeraHatEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "steel_tera_hat"), "main");
    private final ModelPart tera_crown_steel;
    private final ModelPart axe;

	public SteelTeraHatModel(ModelPart root) {
        this.tera_crown_steel = root.getChild("tera_crown_steel");
        this.axe = this.tera_crown_steel.getChild("axe");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition tera_crown_steel = partdefinition.addOrReplaceChild("tera_crown_steel", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -7.0F, 11.0F, 5.0F, 7.0F, new CubeDeformation(0.01F))
                .texOffs(8, 55).addBox(-1.5F, -3.5F, -8.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 10).addBox(1.0F, -1.0F, -8.01F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(36, 10).addBox(-1.0F, -1.0F, -8.01F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(16, 83).addBox(-2.0F, -4.0F, -6.5F, 5.0F, 6.0F, -2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 79).addBox(-5.5F, -4.5F, 0.5F, 12.0F, 6.0F, -8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, -4.9F, 1.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r1 = tera_crown_steel.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(12, 82).addBox(-5.0F, 0.4F, 0.4F, 12.0F, 4.0F, -5.0F, new CubeDeformation(0.0F))
                .texOffs(54, 37).addBox(6.5F, 0.0F, -4.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(46, 30).addBox(-4.5F, 0.0F, -4.0F, 11.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 55).addBox(-4.5F, 0.0F, -4.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.0F, -3.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r2 = tera_crown_steel.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(17, 82).addBox(-6.0F, -4.0F, -2.0F, 11.0F, 5.0F, -4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, 5.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r3 = tera_crown_steel.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(14, 86).addBox(-8.0F, 0.2071F, 3.2071F, 11.0F, 4.0F, -3.0F, new CubeDeformation(0.0F))
                .texOffs(36, 5).addBox(-7.0F, 0.7071F, 0.7071F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(3.0F, 0.7071F, 0.7071F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 34).addBox(-7.0F, 0.7071F, 2.7071F, 10.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, 0.0F, 1.0472F, 0.0F, 0.0F));

        PartDefinition cube_r4 = tera_crown_steel.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(18, 33).addBox(-5.0F, -3.0F, -1.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.5F, -1.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition axe = tera_crown_steel.addOrReplaceChild("axe", CubeListBuilder.create().texOffs(0, 33).addBox(-1.0F, -25.1631F, -18.0809F, 2.0F, 15.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(0.0F, -33.1631F, -18.0809F, 0.0F, 11.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(20, 12).addBox(0.0F, -13.1631F, -18.0809F, 0.0F, 11.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(40, 0).addBox(-1.0F, -22.1631F, -11.0809F, 2.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(40, 16).addBox(-2.5F, -22.1631F, -4.0809F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.25F))
                .texOffs(18, 41).addBox(-2.0F, -13.1631F, -4.0809F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(18, 50).addBox(-1.5F, -9.1631F, -3.0809F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 41).addBox(-2.0F, -2.1631F, -3.0809F, 4.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F))
                .texOffs(16, 80).addBox(-1.5F, -25.6631F, -10.5809F, 3.0F, 16.0F, -8.0F, new CubeDeformation(0.0F))
                .texOffs(18, 78).addBox(-1.5F, -22.6631F, -4.5809F, 3.0F, 10.0F, -7.0F, new CubeDeformation(0.0F))
                .texOffs(15, 89).addBox(-3.0F, -22.6631F, 1.4191F, 6.0F, 10.0F, -6.0F, new CubeDeformation(0.25F))
                .texOffs(13, 84).addBox(-2.5F, -13.6631F, 1.4191F, 5.0F, 5.0F, -6.0F, new CubeDeformation(0.0F))
                .texOffs(11, 91).addBox(-2.0F, -8.6631F, 1.4191F, 4.0F, 6.0F, -5.0F, new CubeDeformation(0.0F))
                .texOffs(14, 80).addBox(-2.5F, -2.6631F, 2.4191F, 5.0F, 5.0F, -6.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.5F, -5.0F, 11.5F, 0.48F, 0.0F, 0.0F));

        PartDefinition cube_r5 = axe.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(44, 50).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7358F, -23.4131F, -2.038F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r6 = axe.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(44, 55).mirror().addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(44, 55).addBox(2.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -17.6631F, -13.5809F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r7 = axe.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(32, 50).mirror().addBox(-1.0F, -2.5F, -2.5F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(32, 50).addBox(2.0F, -2.5F, -2.5F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -18.1631F, -7.5809F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r8 = axe.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(44, 55).mirror().addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(44, 55).addBox(4.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -17.6631F, -1.5809F, -0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(TeraHatEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, int k) {
        tera_crown_steel.render(poseStack, vertexConsumer, i, j, k);
    }
}