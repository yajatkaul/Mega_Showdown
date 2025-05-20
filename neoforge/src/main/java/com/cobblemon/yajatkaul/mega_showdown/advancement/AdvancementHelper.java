package com.cobblemon.yajatkaul.mega_showdown.advancement;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class AdvancementHelper {
    public static void grantAdvancement(ServerPlayer player, String advancementId) {
        ResourceLocation advancementLocation = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega_showdown/" + advancementId);

        if(player == null || player.getServer() == null){
            return;
        }
        AdvancementHolder advancementHolder = player.getServer().getAdvancements().get(advancementLocation);

        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancementHolder);
        if (!progress.isDone()) {
            for (String criterion : progress.getRemainingCriteria()) {
                player.getAdvancements().award(advancementHolder, criterion);
            }
        }
    }
}