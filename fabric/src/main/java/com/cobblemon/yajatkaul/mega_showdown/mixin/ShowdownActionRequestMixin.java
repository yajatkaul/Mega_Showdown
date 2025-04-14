package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
import com.cobblemon.mod.common.battles.ShowdownActionRequest;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = ShowdownActionRequest.class, remap = false)
public class ShowdownActionRequestMixin {
    @Inject(method = "sanitize", at = @At("TAIL"), remap = false)
    private void afterSanitize(PokemonBattle battle, BattleActor battleActor, CallbackInfo ci) {
        Identifier dynamaxBandId = Identifier.of("cobblemon", "dynamax_band");

        for (ServerPlayerEntity player : battle.getPlayers()) {
            GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);
            boolean hasBand = data.getKeyItems().contains(dynamaxBandId);

            // Only modify the active Pokémon of the player who triggered this request
            if (player.getUuid().equals(battleActor.getUuid())) {
                List<ShowdownMoveset> activeMovesets = ((ShowdownActionRequestAccessor) this).getActive();
                if (!hasBand && activeMovesets != null) {
                    for (ShowdownMoveset moveset : activeMovesets) {
                        moveset.blockGimmick(ShowdownMoveset.Gimmick.DYNAMAX);
                        moveset.setMaxMoves(null);
                    }
                }
            }
        }
    }
}