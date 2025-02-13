package com.cobblemon.yajatkaul.mega_showdown.networking;

import com.cobblemon.yajatkaul.mega_showdown.battle.BattleHandling;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MegaEvo;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MegaEvoBattle;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkHandler {
    public static void megaOverServerInBattle(final MegaEvoBattle data, final IPayloadContext context) {
        BattleHandling.handleMegaEvolution(context);
    }

    public static void megaOverServer(final MegaEvo data, final IPayloadContext context) {
        MegaLogic.EvoLogic(context.player());
    }

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar mega_evo_battle = event.registrar("1")
                .executesOn(HandlerThread.NETWORK); // All subsequent payloads will register on the network thread
        mega_evo_battle.playToServer(
                MegaEvoBattle.TYPE,
                MegaEvoBattle.STREAM_CODEC,
                NetworkHandler::megaOverServerInBattle
        );

        final PayloadRegistrar mega_evo = event.registrar("1")
                .executesOn(HandlerThread.NETWORK); // All subsequent payloads will register on the network thread
        mega_evo.playToServer(
                MegaEvo.TYPE,
                MegaEvo.STREAM_CODEC,
                NetworkHandler::megaOverServer
        );
    }
}
