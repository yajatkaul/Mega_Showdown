package com.github.yajatkaul.mega_showdown.networking;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.codec.Effect;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.gimmick.UltraGimmick;
import com.github.yajatkaul.mega_showdown.networking.packets.MegaEvo;
import com.github.yajatkaul.mega_showdown.networking.packets.SecretSwordMoveSwapPacket;
import com.github.yajatkaul.mega_showdown.networking.packets.UltraBurst;
import com.github.yajatkaul.mega_showdown.utils.PlayerUtils;
import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

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

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, SecretSwordMoveSwapPacket.TYPE, SecretSwordMoveSwapPacket.STREAM_CODEC, (buf, context) -> {
            Player player = context.getPlayer();

            if (player instanceof ServerPlayer serverPlayer) {
                for (Pokemon pokemon : Cobblemon.INSTANCE.getStorage().getParty(serverPlayer)) {
                    if (!pokemon.getSpecies().getName().equals("Keldeo")) {
                        continue;
                    }

                    boolean hasSecretSword = pokemon.getMoveSet()
                            .getMoves()
                            .stream()
                            .anyMatch(move -> move.getTemplate().getName().equals("secretsword"));
                    boolean isResolute = pokemon.getAspects().contains("resolute-form");

                    if (!isResolute && hasSecretSword) {
                        Effect.getEffect("mega_showdown:keldeo_effect").applyEffects(pokemon, List.of("sword_form=resolute"), null);
                    } else if (isResolute && !hasSecretSword) {
                        Effect.getEffect("mega_showdown:keldeo_effect").revertEffects(pokemon, List.of("sword_form=ordinary"), null);
                    }
                }
            }
        });
    }
}
