package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownUnbundler;
import com.cobblemon.yajatkaul.mega_showdown.utility.showdown.LoadMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GraalShowdownUnbundler.class, remap = false)
public class GraalShowdownUnbundlerMixin {
    @Unique
    private boolean fabric$loaded = false;

    @Inject(method = "attemptUnbundle", at = @At("TAIL"), remap = false)
    private void beforeShowdownStarts(CallbackInfo ci) {
        if (!fabric$loaded) {
            new LoadMethods().load();
            fabric$loaded = true;
        }
    }
}