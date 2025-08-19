package com.cobblemon.yajatkaul.mega_showdown.mixin.client;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.*;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel renderItem(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {
        if (stack.getItem() == ModItems.MEGA_BRACELET && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet")));
        } else if (stack.getItem() == ModItems.MEGA_RED_BRACELET && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_red")));
        } else if (stack.getItem() == ModItems.MEGA_PINK_BRACELET && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_pink")));
        } else if (stack.getItem() == ModItems.MEGA_YELLOW_BRACELET && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_yellow")));
        } else if (stack.getItem() == ModItems.MEGA_GREEN_BRACELET && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_green")));
        } else if (stack.getItem() == ModItems.MEGA_BLUE_BRACELET && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_blue")));
        } else if (stack.getItem() == ModItems.MEGA_BLACK_BRACELET && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_black")));
        } else if (stack.getItem() == ModItems.MEGA_RING && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megaring")));
        } else if (stack.getItem() == ModItems.BRENDAN_MEGA_CUFF && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "brendan_mega_cuff")));
        } else if (stack.getItem() == ModItems.LYSANDRE_RING && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "lysandre_ring")));
        } else if (stack.getItem() == ModItems.KORRINA_GLOVE && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "korrina_glove")));
        } else if (stack.getItem() == ModItems.MAXIE_GLASSES && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND || renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND || renderMode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND || renderMode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "maxie_glasses")));
        } else if (stack.getItem() == ModItems.ARCHIE_ANCHOR && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND || renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND || renderMode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND || renderMode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "archie_anchor")));
        } else if (stack.getItem() == ZCrystals.Z_RING && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring")));
        } else if (stack.getItem() == TeraMoves.TERA_ORB && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "tera_orb")));
        } else if (stack.getItem() == ModItems.MAY_BRACELET && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "may_bracelet")));
        } else if (stack.getItem() == ZCrystals.Z_RING_BLACK && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_black")));
        } else if (stack.getItem() == ZCrystals.Z_RING_POWER && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-power_ring")));
        } else if (stack.getItem() == DynamaxItems.DYNAMAX_BAND && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "dynamax_band")));
        } else if (stack.getItem() == FormeChangeItems.ZYGARDE_CUBE && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "zygarde_cube")));
        } else if (stack.getItem() == ModItems.LISIA_MEGA_TIARA && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND || renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND || renderMode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND || renderMode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "lisia_mega_tiara")));
        } else if (stack.getItem() == KeyItems.LIKOS_PENDANT && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND || renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND || renderMode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND || renderMode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "likos_pendant")));
        } else if (stack.getItem() == ZCrystals.Z_RING_BLUE && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_blue")));
        } else if (stack.getItem() == ZCrystals.Z_RING_GREEN && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_green")));
        } else if (stack.getItem() == ZCrystals.Z_RING_PINK && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_pink")));
        } else if (stack.getItem() == ZCrystals.Z_RING_YELLOW && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_yellow")));
        } else if (stack.getItem() == ZCrystals.Z_RING_RED && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_red")));
        } else if (stack.getItem() == ZCrystals.OLIVIAS_Z_RING && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "olivias_z-ring")));
        } else if (stack.getItem() == ZCrystals.HAPUS_Z_RING && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "hapus_z-ring")));
        } else if (stack.getItem() == ZCrystals.OLIVIA_Z_POWER_RING && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "olivia_z-power_ring")));
        } else if (stack.getItem() == ZCrystals.HAPU_Z_POWER_RING && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "hapu_z-power_ring")));
        } else if (stack.getItem() == ZCrystals.ROCKET_Z_POWER_RING && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "rocket_z-power_ring")));
        } else if (stack.getItem() == ZCrystals.GLADION_Z_POWER_RING && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "gladion_z-power_ring")));
        } else if (stack.getItem() == ZCrystals.NANU_Z_POWER_RING && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "nanu_z-power_ring")));
        } else if (stack.getItem() == ModItems.OMNI_RING && (renderMode == ModelTransformationMode.GUI)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "omni_ring")));
        }
        return bakedModel;
    }

    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    public BakedModel getHeldItemModelMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getItem() == ModItems.MEGA_BRACELET) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_3d")));
        } else if (stack.getItem() == ModItems.MEGA_RED_BRACELET) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_red_3d")));
        } else if (stack.getItem() == ModItems.MEGA_BLUE_BRACELET) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_blue_3d")));
        } else if (stack.getItem() == ModItems.MEGA_BLACK_BRACELET) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_black_3d")));
        } else if (stack.getItem() == ModItems.MEGA_PINK_BRACELET) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_pink_3d")));
        } else if (stack.getItem() == ModItems.MEGA_GREEN_BRACELET) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_green_3d")));
        } else if (stack.getItem() == ModItems.MEGA_YELLOW_BRACELET) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_yellow_3d")));
        } else if (stack.getItem() == ModItems.MEGA_RING) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megaring_3d")));
        } else if (stack.getItem() == ModItems.BRENDAN_MEGA_CUFF) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "brendan_mega_cuff_3d")));
        } else if (stack.getItem() == ModItems.LYSANDRE_RING) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "lysandre_ring_3d")));
        } else if (stack.getItem() == ModItems.KORRINA_GLOVE) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "korrina_glove_3d")));
        } else if (stack.getItem() == ModItems.MAXIE_GLASSES) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "maxie_glasses_3d")));
        } else if (stack.getItem() == ModItems.ARCHIE_ANCHOR) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "archie_anchor_3d")));
        } else if (stack.getItem() == ZCrystals.Z_RING) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_3d")));
        } else if (stack.getItem() == TeraMoves.TERA_ORB) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "tera_orb_3d")));
        } else if (stack.getItem() == ModItems.MAY_BRACELET) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "may_bracelet_3d")));
        } else if (stack.getItem() == ZCrystals.Z_RING_BLACK) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_black_3d")));
        } else if (stack.getItem() == ZCrystals.Z_RING_POWER) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-power_ring_3d")));
        } else if (stack.getItem() == DynamaxItems.DYNAMAX_BAND) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "dynamax_band_3d")));
        } else if (stack.getItem() == FormeChangeItems.ZYGARDE_CUBE) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "zygarde_cube_3d")));
        } else if (stack.getItem() == ModItems.LISIA_MEGA_TIARA) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "lisia_mega_tiara_3d")));
        } else if (stack.getItem() == KeyItems.LIKOS_PENDANT) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "likos_pendant_3d")));
        } else if (stack.getItem() == ZCrystals.Z_RING_YELLOW) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_yellow_3d")));
        } else if (stack.getItem() == ZCrystals.Z_RING_BLUE) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_blue_3d")));
        } else if (stack.getItem() == ZCrystals.Z_RING_GREEN) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_green_3d")));
        } else if (stack.getItem() == ZCrystals.Z_RING_PINK) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_pink_3d")));
        } else if (stack.getItem() == ZCrystals.Z_RING_RED) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_red_3d")));
        } else if (stack.getItem() == ZCrystals.OLIVIAS_Z_RING) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "olivias_z-ring_3d")));
        } else if (stack.getItem() == ZCrystals.HAPUS_Z_RING) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "hapus_z-ring_3d")));
        } else if (stack.getItem() == ZCrystals.OLIVIA_Z_POWER_RING) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "olivia_z-power_ring_3d")));
        } else if (stack.getItem() == ZCrystals.HAPU_Z_POWER_RING) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "hapu_z-power_ring_3d")));
        } else if (stack.getItem() == ZCrystals.ROCKET_Z_POWER_RING) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "rocket_z-power_ring_3d")));
        } else if (stack.getItem() == ZCrystals.GLADION_Z_POWER_RING) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "gladion_z-power_ring_3d")));
        } else if (stack.getItem() == ZCrystals.NANU_Z_POWER_RING) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "nanu_z-power_ring_3d")));
        } else if (stack.getItem() == ModItems.OMNI_RING) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "omni_ring_3d")));
        }
        return bakedModel;
    }
}