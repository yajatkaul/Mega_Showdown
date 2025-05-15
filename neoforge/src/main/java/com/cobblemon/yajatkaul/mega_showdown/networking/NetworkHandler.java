package com.cobblemon.yajatkaul.mega_showdown.networking;

import com.cobblemon.yajatkaul.mega_showdown.config.structure.CustomConfig;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.UltraLogic;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MSDCustomPacket;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MegaEvo;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.UltraTrans;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkHandler {
    public static void megaOverServer(final MegaEvo data, final IPayloadContext context) {
        MegaLogic.EvoLogic(context.player());
    }

    public static void ulraOverServer(final UltraTrans data, final IPayloadContext context) {
        UltraLogic.ultraTransform(context.player());
    }

    public static void syncCustomConfigPacket(final MSDCustomPacket data, final IPayloadContext context) {
        CustomConfig.fusionItems = data.fusionItems();
        CustomConfig.formeChange = data.formeChanges();
        CustomConfig.heldItems = data.heldItems();
        CustomConfig.megaItems = data.megaItems();
        CustomConfig.gmax = data.gmaxItems();
        CustomConfig.keyItems = data.keyItems();
    }

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar serverRegistrar = event.registrar("1")
                .executesOn(HandlerThread.NETWORK);

        serverRegistrar.playToServer(
                MegaEvo.TYPE,
                MegaEvo.STREAM_CODEC,
                NetworkHandler::megaOverServer
        );

        serverRegistrar.playToServer(
                UltraTrans.TYPE,
                UltraTrans.STREAM_CODEC,
                NetworkHandler::ulraOverServer
        );

        //CLIENT
        final PayloadRegistrar clientRegistrar = event.registrar("1")
                .executesOn(HandlerThread.MAIN);

        clientRegistrar.playToClient(
                MSDCustomPacket.TYPE,
                MSDCustomPacket.STREAM_CODEC,
                NetworkHandler::syncCustomConfigPacket
        );
    }
}
