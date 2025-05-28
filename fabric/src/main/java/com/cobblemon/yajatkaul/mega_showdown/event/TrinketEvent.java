package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.BattleRegistry;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.TeraItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class TrinketEvent implements Trinket {
    private static void handleTrinketChange(LivingEntity entity) {
        if (entity instanceof ServerPlayerEntity player) {
            boolean hasTeraItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> item.getItem() instanceof TeraItem)).orElse(false);

            PokemonBattle battle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player);

            if (!hasTeraItemTrinkets && battle != null) {
                player.sendMessage(
                        Text.translatable("message.mega_showdown.you_aint_slick").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                battle.end();
            }
        }
    }

    public static void register() {
        TrinketsApi.registerTrinket(TeraMoves.TERA_ORB, new TrinketEvent());
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        handleTrinketChange(entity);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        handleTrinketChange(entity);
    }
}
