package com.github.yajatkaul.mega_showdown.mixin.client.ui;

import com.cobblemon.mod.common.client.gui.summary.Summary;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.cobblemon.features.GlobalFeatureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(value = Summary.Companion.class)
public class SummaryCompanionMixin {
    @Inject(
            method = "open",
            at = @At("TAIL"),
            remap = false
    )
    private void onOpen(Collection<? extends Pokemon> party, boolean editable, int selection, CallbackInfo ci) {
        party.forEach((GlobalFeatureManager::update));
    }
}
