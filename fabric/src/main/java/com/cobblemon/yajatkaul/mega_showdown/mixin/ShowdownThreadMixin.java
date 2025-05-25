package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.battles.ShowdownThread;
import com.cobblemon.yajatkaul.mega_showdown.utility.showdown.LoadMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ShowdownThread.class, remap = false)
public class ShowdownThreadMixin {
    @Inject(method = "run", at = @At("HEAD"), remap = false)
    private void beforeShowdownStarts(CallbackInfo ci) {
        new LoadMethods().load();
    }
}
