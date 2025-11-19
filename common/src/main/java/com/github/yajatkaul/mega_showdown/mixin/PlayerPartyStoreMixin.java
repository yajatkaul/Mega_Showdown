package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerPartyStore.class, remap = false)
public class PlayerPartyStoreMixin {
    @Inject(method = "initialize", at = @At("TAIL"))
    private void pokemonInit(CallbackInfo ci) {
        PlayerPartyStore playerPartyStore = (PlayerPartyStore) (Object) this;

        AspectUtils.revertPokemonsIfRequired(playerPartyStore);
    }
}
