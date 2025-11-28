package com.github.yajatkaul.mega_showdown.entitiy.model;// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

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

public class WaterTeraHatModel extends EntityModel<TeraHatEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "water_tera_hat"), "main");
    private final ModelPart crown;
    private final ModelPart bigwater;
    private final ModelPart water2r3_3;
    private final ModelPart water2r3;
    private final ModelPart water2r2_2;
    private final ModelPart water2r5;
    private final ModelPart water2r2;
    private final ModelPart water2r25;
    private final ModelPart water2r1_1;
    private final ModelPart water2r6;
    private final ModelPart water2r1;
    private final ModelPart water2r45;
    private final ModelPart awater2;
    private final ModelPart water4_4;
    private final ModelPart water4;
    private final ModelPart water5_5;
    private final ModelPart waterz2;
    private final ModelPart water5;
    private final ModelPart waterz;
    private final ModelPart water6_6;
    private final ModelPart water9;
    private final ModelPart water6;
    private final ModelPart water7;
    private final ModelPart awater1;
    private final ModelPart water1_1;
    private final ModelPart water1;
    private final ModelPart water2_2;
    private final ModelPart water2;
    private final ModelPart water3_3;
    private final ModelPart water3;
    private final ModelPart crystal;

	public WaterTeraHatModel(ModelPart root) {
        this.crown = root.getChild("crown");
        this.bigwater = this.crown.getChild("bigwater");
        this.water2r3_3 = this.bigwater.getChild("water2r3_3");
        this.water2r3 = this.bigwater.getChild("water2r3");
        this.water2r2_2 = this.bigwater.getChild("water2r2_2");
        this.water2r5 = this.water2r2_2.getChild("water2r5");
        this.water2r2 = this.bigwater.getChild("water2r2");
        this.water2r25 = this.water2r2.getChild("water2r25");
        this.water2r1_1 = this.bigwater.getChild("water2r1_1");
        this.water2r6 = this.water2r1_1.getChild("water2r6");
        this.water2r1 = this.bigwater.getChild("water2r1");
        this.water2r45 = this.water2r1.getChild("water2r45");
        this.awater2 = this.crown.getChild("awater2");
        this.water4_4 = this.awater2.getChild("water4_4");
        this.water4 = this.awater2.getChild("water4");
        this.water5_5 = this.awater2.getChild("water5_5");
        this.waterz2 = this.water5_5.getChild("waterz2");
        this.water5 = this.awater2.getChild("water5");
        this.waterz = this.water5.getChild("waterz");
        this.water6_6 = this.awater2.getChild("water6_6");
        this.water9 = this.water6_6.getChild("water9");
        this.water6 = this.awater2.getChild("water6");
        this.water7 = this.water6.getChild("water7");
        this.awater1 = this.crown.getChild("awater1");
        this.water1_1 = this.awater1.getChild("water1_1");
        this.water1 = this.awater1.getChild("water1");
        this.water2_2 = this.awater1.getChild("water2_2");
        this.water2 = this.awater1.getChild("water2");
        this.water3_3 = this.awater1.getChild("water3_3");
        this.water3 = this.awater1.getChild("water3");
        this.crystal = this.crown.getChild("crystal");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition crown = partdefinition.addOrReplaceChild("crown", CubeListBuilder.create().texOffs(30, 70).addBox(-9.0F, -4.1F, -7.0F, 16.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(-8.0F, -6.1F, -6.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(24, 84).addBox(-8.0F, -6.1F, 8.0F, 14.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(78, 83).addBox(-8.0F, -6.1F, -6.0F, 14.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(28, 52).addBox(6.0F, -6.1F, -6.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-9.0F, -4.1F, -6.0F, 1.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(64, 70).addBox(-9.0F, -4.1F, 8.0F, 16.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 16).addBox(6.0F, -4.1F, -6.0F, 1.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(88, 85).addBox(-3.0F, -12.1F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 87).addBox(-2.0F, -8.1F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 104).addBox(-2.0F, -14.1F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 98).addBox(-3.0F, -17.1F, -1.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 80).addBox(-4.0F, -15.1F, -2.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(60, 24).addBox(-5.0F, -18.1F, -3.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -2.1F, -6.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(86, 57).addBox(-3.0F, -23.1F, -1.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(9, 96).addBox(-3.25F, -23.1F, 3.25F, 4.5F, 5.0F, -4.5F, new CubeDeformation(0.0F))
                .texOffs(30, 75).addBox(-4.0F, -25.1F, -2.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(68, 89).addBox(-4.25F, -25.35F, 4.5F, 6.5F, 3.25F, -6.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -8.4F, -1.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r1 = crown.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 34).addBox(0.0259F, -4.0966F, -7.0F, 0.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -6.0F, 1.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r2 = crown.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(78, 79).addBox(-7.0F, -4.0966F, 0.0259F, 14.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -6.0F, -6.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r3 = crown.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 34).addBox(-0.0259F, -4.0966F, -7.0F, 0.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -6.0F, 1.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r4 = crown.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(78, 75).addBox(-7.0F, -4.0966F, -0.0259F, 14.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -6.0F, 8.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition bigwater = crown.addOrReplaceChild("bigwater", CubeListBuilder.create(), PartPose.offset(-1.0F, -25.0F, 1.0F));

        PartDefinition water2r3_3 = bigwater.addOrReplaceChild("water2r3_3", CubeListBuilder.create().texOffs(31, 93).addBox(-3.25F, -7.3266F, 2.9357F, 6.5F, 9.25F, -3.25F, new CubeDeformation(0.0F))
                .texOffs(75, 77).addBox(-3.25F, -7.3266F, 12.1857F, 6.5F, 3.5F, -9.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 1.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r5 = water2r3_3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(109, 94).addBox(-1.2045F, -2.3266F, -4.2955F, 2.5F, 2.5F, -2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.85F, -4.5F, 17.15F, 0.0F, 0.7854F, 0.0F));

        PartDefinition water2r3 = bigwater.addOrReplaceChild("water2r3", CubeListBuilder.create().texOffs(52, 84).addBox(-3.0F, -7.0F, 0.0F, 6.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(56, 0).addBox(-3.0F, -7.0766F, 2.9357F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 1.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r6 = water2r3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(104, 49).addBox(-0.9545F, -2.0766F, -1.0455F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5F, 13.25F, 0.0F, 0.7854F, 0.0F));

        PartDefinition water2r2_2 = bigwater.addOrReplaceChild("water2r2_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition water2r5 = water2r2_2.addOrReplaceChild("water2r5", CubeListBuilder.create().texOffs(93, 40).addBox(-3.25F, -7.3266F, 2.9357F, 6.5F, 9.25F, -3.25F, new CubeDeformation(0.0F))
                .texOffs(79, 31).addBox(-3.25F, -7.3266F, 12.1857F, 6.5F, 3.5F, -9.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 1.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r7 = water2r5.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(109, 98).addBox(-1.2045F, -2.3266F, -1.2955F, 2.5F, 2.5F, -2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, -4.5F, 15.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition water2r2 = bigwater.addOrReplaceChild("water2r2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition water2r25 = water2r2.addOrReplaceChild("water2r25", CubeListBuilder.create().texOffs(70, 85).addBox(-3.0F, -7.0766F, -0.0643F, 6.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(56, 34).addBox(-3.0F, -7.0766F, 2.9357F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 1.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r8 = water2r25.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(104, 53).addBox(-0.9545F, -2.0766F, -1.0455F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5F, 13.25F, 0.0F, 0.7854F, 0.0F));

        PartDefinition water2r1_1 = bigwater.addOrReplaceChild("water2r1_1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition water2r6 = water2r1_1.addOrReplaceChild("water2r6", CubeListBuilder.create().texOffs(93, 52).addBox(-3.25F, -7.3266F, 2.9357F, 6.5F, 9.25F, -3.25F, new CubeDeformation(0.0F))
                .texOffs(19, 87).addBox(-3.25F, -7.3266F, 12.1857F, 6.5F, 3.5F, -9.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 1.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r9 = water2r6.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(109, 102).addBox(-1.2045F, -2.3266F, -1.2955F, 2.5F, 2.5F, -2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, -4.5F, 15.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition water2r1 = bigwater.addOrReplaceChild("water2r1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition water2r45 = water2r1.addOrReplaceChild("water2r45", CubeListBuilder.create().texOffs(86, 0).addBox(-3.0F, -7.0766F, -0.0643F, 6.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(56, 46).addBox(-3.0F, -7.0766F, 2.9357F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 1.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r10 = water2r45.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(74, 104).addBox(-0.9545F, -2.0766F, -1.0455F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5F, 13.25F, 0.0F, 0.7854F, 0.0F));

        PartDefinition awater2 = crown.addOrReplaceChild("awater2", CubeListBuilder.create(), PartPose.offset(-1.0F, -18.0F, 1.0F));

        PartDefinition water4_4 = awater2.addOrReplaceChild("water4_4", CubeListBuilder.create().texOffs(11, 107).addBox(-1.75F, -6.3293F, -1.4391F, 3.5F, 3.5F, -5.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r11 = water4_4.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(109, 33).addBox(-2.25F, -5.3293F, -4.4391F, 3.5F, 6.25F, -2.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.0F, 5.25F, 0.0F, 0.0F, 0.0F));

        PartDefinition water4 = awater2.addOrReplaceChild("water4", CubeListBuilder.create().texOffs(90, 12).addBox(-1.5F, -6.0793F, -6.4391F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r12 = water4.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(42, 86).addBox(-2.0F, -5.0793F, -1.4391F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition water5_5 = awater2.addOrReplaceChild("water5_5", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 0.0F, 1.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition waterz2 = water5_5.addOrReplaceChild("waterz2", CubeListBuilder.create().texOffs(53, 107).addBox(-1.75F, -6.3293F, -1.4391F, 3.5F, 3.5F, -5.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r13 = waterz2.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(109, 41).addBox(-2.25F, -5.3293F, -4.4391F, 3.5F, 6.25F, -2.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.0F, 5.25F, 0.0F, 0.0F, 0.0F));

        PartDefinition water5 = awater2.addOrReplaceChild("water5", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 0.0F, 1.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition waterz = water5.addOrReplaceChild("waterz", CubeListBuilder.create().texOffs(92, 20).addBox(-1.5F, -6.0793F, -6.4391F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r14 = waterz.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(32, 98).addBox(-2.0F, -5.0793F, -1.4391F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition water6_6 = awater2.addOrReplaceChild("water6_6", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition water9 = water6_6.addOrReplaceChild("water9", CubeListBuilder.create().texOffs(69, 108).addBox(-1.75F, -6.3293F, -1.4391F, 3.5F, 3.5F, -5.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r15 = water9.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(47, 109).addBox(-2.25F, -5.3293F, -4.4391F, 3.5F, 6.25F, -2.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.0F, 5.25F, 0.0F, 0.0F, 0.0F));

        PartDefinition water6 = awater2.addOrReplaceChild("water6", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition water7 = water6.addOrReplaceChild("water7", CubeListBuilder.create().texOffs(88, 93).addBox(-1.5F, -6.0793F, -6.4391F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r16 = water7.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(14, 104).addBox(-2.0F, -5.0793F, -1.4391F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition awater1 = crown.addOrReplaceChild("awater1", CubeListBuilder.create(), PartPose.offset(-1.0F, -14.5F, 2.5F));

        PartDefinition water1_1 = awater1.addOrReplaceChild("water1_1", CubeListBuilder.create().texOffs(113, 68).addBox(-1.25F, -4.3293F, -0.4391F, 2.5F, 2.5F, -5.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -2.0F, 0.6545F, -0.7854F, 0.0F));

        PartDefinition cube_r17 = water1_1.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(67, 108).addBox(-1.25F, -4.0793F, -2.4391F, 2.5F, 4.0F, -1.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.25F, 0.0F, 0.0F, 0.0F));

        PartDefinition water1 = awater1.addOrReplaceChild("water1", CubeListBuilder.create().texOffs(74, 97).addBox(-1.0F, -4.0793F, -5.4391F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -2.0F, 0.6545F, -0.7854F, 0.0F));

        PartDefinition cube_r18 = water1.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(52, 104).addBox(-1.0F, -4.0793F, -0.4391F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition water2_2 = awater1.addOrReplaceChild("water2_2", CubeListBuilder.create().texOffs(113, 112).addBox(-1.1F, -4.4293F, -0.5391F, 2.5F, 2.5F, -5.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -2.0F, 0.6545F, 0.7854F, 0.0F));

        PartDefinition cube_r19 = water2_2.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(109, 15).addBox(-1.25F, -4.0793F, -2.4391F, 2.5F, 4.0F, -1.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.15F, 0.0F, 3.1F, 0.0F, 0.0F, 0.0F));

        PartDefinition water2 = awater1.addOrReplaceChild("water2", CubeListBuilder.create().texOffs(98, 66).addBox(-1.0F, -4.0793F, -5.4391F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -2.25F, 0.6545F, 0.7854F, 0.0F));

        PartDefinition cube_r20 = water2.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(82, 104).addBox(-1.0F, -4.0793F, -0.4391F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition water3_3 = awater1.addOrReplaceChild("water3_3", CubeListBuilder.create().texOffs(11, 115).addBox(-1.25F, -4.3293F, 5.6391F, 2.5F, 2.5F, -5.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r21 = water3_3.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(35, 109).addBox(-1.25F, -4.3293F, -2.5609F, 2.5F, 4.25F, -1.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.95F, 0.0F, 0.0F, 0.0F));

        PartDefinition water3 = awater1.addOrReplaceChild("water3", CubeListBuilder.create().texOffs(88, 101).addBox(-1.0F, -4.0793F, 0.4391F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r22 = water3.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(58, 105).addBox(-1.0F, -4.0793F, -0.5609F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition crystal = crown.addOrReplaceChild("crystal", CubeListBuilder.create().texOffs(109, 5).addBox(-2.25F, -0.6F, 1.25F, 4.5F, 4.0F, -2.5F, new CubeDeformation(0.0F))
                .texOffs(56, 12).addBox(0.5F, 0.9F, -1.01F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(56, 13).addBox(-1.5F, 0.9F, -1.01F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(104, 6).addBox(-2.0F, -0.6F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -5.5F, -8.25F));

        PartDefinition cube_r23 = crystal.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(104, 85).addBox(-1.8707F, -1.8707F, -0.98F, 2.8F, 2.8F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.1F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r24 = crystal.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(109, 49).addBox(-2.1207F, -2.1207F, -4.23F, 3.05F, 3.05F, -2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.3F, 5.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r25 = crystal.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(97, 33).addBox(-2.1207F, -2.1207F, -4.23F, 3.05F, 3.05F, -2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2F, 5.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r26 = crystal.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(86, 66).addBox(-1.8707F, -1.8707F, -0.99F, 2.8F, 2.8F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(TeraHatEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, int k) {
        crown.render(poseStack, vertexConsumer, i, j, k);
    }
}