package com.github.yajatkaul.mega_showdown.event;

import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.datapack.MegaShowdownDatapackRegister;
import com.github.yajatkaul.mega_showdown.gimmick.MaxGimmick;
import com.github.yajatkaul.mega_showdown.networking.client.packet.ConfigSyncPacket;
import com.github.yajatkaul.mega_showdown.utils.ShowdownItemsLoad;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;

public class MinecraftEvents {
    public static void register() {
        LifecycleEvent.SERVER_STARTING.register((minecraftServer) -> {
            MegaShowdownDatapackRegister.registerShowdownDatapackItems(minecraftServer);
            ShowdownItemsLoad.load();
        });

        TickEvent.SERVER_PRE.register(MaxGimmick::updateScalingAnimations);

        PlayerEvent.PLAYER_JOIN.register((ServerPlayer player) -> NetworkManager.sendToPlayer(player, new ConfigSyncPacket(MegaShowdownConfig.outSideMega, MegaShowdownConfig.outSideUltraBurst)));
    }
}
