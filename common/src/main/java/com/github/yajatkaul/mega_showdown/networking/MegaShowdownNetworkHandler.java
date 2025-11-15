package com.github.yajatkaul.mega_showdown.networking;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.gimmick.UltraGimmick;
import com.github.yajatkaul.mega_showdown.networking.packets.MegaEvo;
import com.github.yajatkaul.mega_showdown.networking.packets.UltraBurst;
import com.github.yajatkaul.mega_showdown.utils.PlayerUtils;
import dev.architectury.networking.NetworkManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;

public class MegaShowdownNetworkHandler {
    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, MegaEvo.TYPE, MegaEvo.STREAM_CODEC, (buf, context) -> {
            Player player = context.getPlayer();

            EntityHitResult hitResult = PlayerUtils.getEntityLookingAt(player, 4.5f);
            Entity entity = null;
            if (hitResult != null) {
                entity = hitResult.getEntity();
            }

            if (entity instanceof PokemonEntity pokemonEntity) {
                MegaGimmick.megaToggle(pokemonEntity);
            }
        });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, UltraBurst.TYPE, UltraBurst.STREAM_CODEC, (buf, context) -> {
            Player player = context.getPlayer();

            EntityHitResult hitResult = PlayerUtils.getEntityLookingAt(player, 4.5f);
            Entity entity = null;
            if (hitResult != null) {
                entity = hitResult.getEntity();
            }

            if (entity instanceof PokemonEntity pokemonEntity) {
                UltraGimmick.ultraBurst(pokemonEntity.getPokemon());
            }
        });
    }
}
