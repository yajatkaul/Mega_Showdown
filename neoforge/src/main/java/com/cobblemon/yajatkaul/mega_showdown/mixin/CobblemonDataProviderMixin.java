package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.api.data.DataRegistry;
import com.cobblemon.mod.common.api.scripting.CobblemonScripts;
import com.cobblemon.mod.common.data.CobblemonDataProvider;
import com.cobblemon.yajatkaul.mega_showdown.datapack.showdown.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = CobblemonDataProvider.class, remap = false)
public class CobblemonDataProviderMixin {
    @Redirect(
            method = "registerDefaults",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/cobblemon/mod/common/data/CobblemonDataProvider;register(Lcom/cobblemon/mod/common/api/data/DataRegistry;)Lcom/cobblemon/mod/common/api/data/DataRegistry;"
            )
    )
    private DataRegistry injectBeforeSpeciesRegister(CobblemonDataProvider instance, DataRegistry registry) {
        if (registry instanceof CobblemonScripts) {
            if (registry == CobblemonScripts.INSTANCE) {
                instance.register(Abilities.INSTANCE);
                instance.register(Moves.INSTANCE);
                instance.register(Conditions.INSTANCE);
                instance.register(HeldItems.INSTANCE);
                instance.register(TypeCharts.INSTANCE);
            }
            return instance.register(registry);
        }
        return instance.register(registry);
    }
}