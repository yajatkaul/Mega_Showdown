package com.cobblemon.yajatkaul.mega_showdown.advancement;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AdvancementHelper {
    public static void grantAdvancement(ServerPlayerEntity player, String advancementId) {
        Identifier advancementIdentifier = Identifier.of(MegaShowdown.MOD_ID, "mega_showdown/" + advancementId);

        if(player.getServer() == null){
            return;
        }
        AdvancementEntry advancement = player.getServer().getAdvancementLoader().get(advancementIdentifier);

        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            if (!progress.isDone()) {
                for (String criterion : progress.getUnobtainedCriteria()) {
                    player.getAdvancementTracker().grantCriterion(advancement, criterion);
                }
            }
        } else {
            MegaShowdown.LOGGER.info("Advancement {} not found!", advancementId);
        }
    }
}