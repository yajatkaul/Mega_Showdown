package com.cobblemon.yajatkaul.mega_showdown.mixin.pokemon;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.evolution.variants.LevelUpEvolution;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LevelUpEvolution.class, remap = false)
public class EvolutionMixin {

    @Shadow
    private boolean consumeHeldItem;

    // Since im a genius and use the evolution data sync on every form change I had this gross idea to fix it, i want to die
    @Inject(method = "forceEvolve", at = @At("TAIL"))
    private void forceEvolve(Pokemon pk, CallbackInfo ci) {
        if (consumeHeldItem) {
            pk.setHeldItem$common(ItemStack.EMPTY);
        }
    }
}
