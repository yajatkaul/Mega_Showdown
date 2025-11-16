package com.github.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.BattleRegistry;
import com.github.yajatkaul.mega_showdown.tag.ModTags;
import io.wispforest.accessories.api.events.CanUnequipCallback;
import io.wispforest.accessories.api.slot.SlotReference;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class AccessoriesEvent {
    public static void register() {
        CanUnequipCallback.EVENT.register(AccessoriesEvent::onUnequip);
    }

    private static TriState onUnequip(ItemStack itemStack, SlotReference reference) {
        LivingEntity entity = reference.entity();
        if (entity instanceof ServerPlayer player) {
            PokemonBattle battle = BattleRegistry.getBattleByParticipatingPlayer(player);

            if (battle != null && itemStack.is(ModTags.Items.TERA_ORB)) {
                return TriState.FALSE;
            }
        }

        return TriState.DEFAULT;
    }
}
