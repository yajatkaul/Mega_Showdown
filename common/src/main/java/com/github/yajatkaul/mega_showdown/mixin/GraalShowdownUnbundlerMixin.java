package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownUnbundler;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.utils.LoadShowdown;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GraalShowdownUnbundler.class, remap = false)
public class GraalShowdownUnbundlerMixin {
    @Unique
    private boolean neoForge$loaded = false;

    @Inject(method = "attemptUnbundle", at = @At("TAIL"))
    private void beforeShowdownStarts(CallbackInfo ci) {
        if (!MegaShowdownConfig.loaded) {
            MegaShowdownConfig.load();
        }
        if (!neoForge$loaded) {
            new LoadShowdown().load();
            neoForge$loaded = true;
        }
    }
}