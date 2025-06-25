package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.BattleRegistry;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.TriState;
import top.theillusivec4.curios.api.event.CurioCanUnequipEvent;

public class CurioEvents {
    @SubscribeEvent
    private static void onCurioChange(CurioCanUnequipEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PokemonBattle battle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player);

            if (battle != null && event.getStack().is(TeraMoves.TERA_ORB)) {
                event.setUnequipResult(TriState.FALSE);
            }
        }
    }
}
