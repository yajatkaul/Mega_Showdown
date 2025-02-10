package com.cobblemon.yajatkaul.mega_showdown.networking;

import com.cobblemon.yajatkaul.mega_showdown.battle.BattleHandling;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MegaEvo;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkHandler {
    public static void handleDataOnClient(final MegaEvo data, final IPayloadContext context) {
        //BattleHandling.handleMegaEvolution(context);
    }

    public static void handleDataOnServer(final MegaEvo data, final IPayloadContext context) {
        BattleHandling.handleMegaEvolution(context);
    }

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1")
                .executesOn(HandlerThread.NETWORK); // All subsequent payloads will register on the network thread
        registrar.playBidirectional(
                MegaEvo.TYPE,
                MegaEvo.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        NetworkHandler::handleDataOnClient,
                        NetworkHandler::handleDataOnServer
                )
        );
    }
}
