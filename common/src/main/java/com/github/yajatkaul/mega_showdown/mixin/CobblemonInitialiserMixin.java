package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.Cobblemon;
import com.github.yajatkaul.mega_showdown.cobblemon.features.GlobalFeatureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Cobblemon.class, remap = false)
public abstract class CobblemonInitialiserMixin {
    @Inject(method = "initialize", at = @At("HEAD"))
    private void registerEarlyEvents (CallbackInfo ci) {
        GlobalFeatureManager.register();
    }
}