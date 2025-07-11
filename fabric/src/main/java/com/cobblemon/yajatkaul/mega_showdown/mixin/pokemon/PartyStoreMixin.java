package com.cobblemon.yajatkaul.mega_showdown.mixin.pokemon;

import com.cobblemon.mod.common.api.storage.PokemonStore;
import com.cobblemon.mod.common.api.storage.party.PartyPosition;
import com.cobblemon.mod.common.api.storage.party.PartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.features.GlobalFeatureManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = PartyStore.class, remap = false)
public abstract class PartyStoreMixin extends PokemonStore<PartyPosition> {
    @Shadow
    @Final
    private List<Pokemon> slots;

    @Inject(method = "sendTo", at = @At("TAIL"))
    private void updateGlobalFeatures(ServerPlayerEntity player, CallbackInfo ci) {
        this.slots.forEach(pokemon -> {
            if (pokemon != null) {
                GlobalFeatureManager.update(pokemon, player);
            }
        });
    }

    @Inject(method = "set(Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", at = @At("TAIL"))
    private void updateGlobalsOnSet(PartyPosition position, Pokemon pokemon, CallbackInfo ci) {
        this.getObservingPlayers().forEach(player -> GlobalFeatureManager.update(pokemon, player));
    }
}