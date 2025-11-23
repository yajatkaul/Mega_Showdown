package com.github.yajatkaul.mega_showdown.networking.server;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.codec.Effect;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.gimmick.UltraGimmick;
import com.github.yajatkaul.mega_showdown.networking.server.packet.MegaEvoPacket;
import com.github.yajatkaul.mega_showdown.networking.server.packet.SecretSwordMoveSwapPacket;
import com.github.yajatkaul.mega_showdown.networking.server.packet.UltraBurstPacket;
import com.github.yajatkaul.mega_showdown.utils.PlayerUtils;
import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class MegaShowdownNetworkHandlerServer {
    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, MegaEvoPacket.TYPE, MegaEvoPacket.STREAM_CODEC, (buf, context) -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            Pokemon pokemon = PlayerUtils.getPartyPokemonFromUUID(player, buf.pokemonId());

            if (pokemon != null) {
                MegaGimmick.megaToggle(pokemon.getEntity());
            }
        });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, UltraBurstPacket.TYPE, UltraBurstPacket.STREAM_CODEC, (buf, context) -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            Pokemon pokemon = PlayerUtils.getPartyPokemonFromUUID(player, buf.pokemonId());

            if (pokemon != null) {
                UltraGimmick.ultraBurstToggle(pokemon);
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
