package com.cobblemon.yajatkaul.mega_showdown.mixin.client;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 1))
    private void onInit(CallbackInfo ci) {
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_red_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_blue_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_black_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_pink_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_green_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megabracelet_yellow_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "megaring_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "brendan_mega_cuff_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "lysandre_ring_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "korrina_glove_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "maxie_glasses_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "archie_anchor_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "tera_orb_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_black_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-power_ring_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "may_bracelet_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "dynamax_band_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "zygarde_cube_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "lisia_mega_tiara_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "likos_pendant_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_yellow_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_green_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_pink_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_blue_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "z-ring_red_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "olivias_z-ring_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "hapus_z-ring_3d")));

        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "olivia_z-power_ring_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "hapu_z-power_ring_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "rocket_z-power_ring_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "gladion_z-power_ring_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MegaShowdown.MOD_ID, "nanu_z-power_ring_3d")));

    }
}