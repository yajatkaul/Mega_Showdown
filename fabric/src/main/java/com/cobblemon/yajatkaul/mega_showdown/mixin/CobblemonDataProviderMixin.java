package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.api.data.DataRegistry;
import com.cobblemon.mod.common.api.scripting.CobblemonScripts;
import com.cobblemon.mod.common.data.CobblemonDataProvider;
import com.cobblemon.yajatkaul.mega_showdown.datapack.showdown.Conditions;
import com.cobblemon.yajatkaul.mega_showdown.datapack.showdown.HeldItems;
import com.cobblemon.yajatkaul.mega_showdown.datapack.showdown.Scripts;
import com.cobblemon.yajatkaul.mega_showdown.datapack.showdown.TypeCharts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = CobblemonDataProvider.class, remap = false)
public class CobblemonDataProviderMixin {
    @Redirect(
            method = "registerDefaults",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/cobblemon/mod/common/data/CobblemonDataProvider;register(Lcom/cobblemon/mod/common/api/data/DataRegistry;Z)Lcom/cobblemon/mod/common/api/data/DataRegistry;"
            )
    )
    private DataRegistry injectBeforeSpeciesRegister(CobblemonDataProvider instance, DataRegistry registry, boolean reloadable) {
        if (registry instanceof CobblemonScripts) {
            if (registry == CobblemonScripts.INSTANCE) {
                instance.register(Conditions.INSTANCE, true);
                instance.register(HeldItems.INSTANCE, true);
                instance.register(TypeCharts.INSTANCE, true);
                instance.register(Scripts.INSTANCE, true);
            }
            return instance.register(registry, true);
        }
        return instance.register(registry, true);
    }
}