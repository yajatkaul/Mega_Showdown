package com.github.yajatkaul.mega_showdown.gimmick;

import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.codec.Effect;
import com.github.yajatkaul.mega_showdown.codec.ZCrystal;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.tag.MegaShowdownTags;
import com.github.yajatkaul.mega_showdown.utils.AccessoriesUtils;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import com.github.yajatkaul.mega_showdown.utils.RegistryLocator;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class UltraGimmick {
    public static void ultraBurstToggle(Pokemon pokemon) {
        if (!MegaShowdownConfig.outSideUltraBurst) {
            return;
        }

        ServerPlayer player = pokemon.getOwnerPlayer();

        if (canUltraBurst(pokemon)) {
            if (player != null && !AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.Z_RING) && !AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.OMNI_RING)) {
                player.displayClientMessage(Component.translatable("message.mega_showdown.no_z_ring")
                        .withStyle(ChatFormatting.RED), true);
                return;
            } else if (pokemon.getAspects().contains("dawn-fusion")) {
                pokemon.getPersistentData().putString("necrozma_form", "prism_fusion=dawn");
                AspectUtils.appendRevertDataPokemon(
                        Effect.getEffect("mega_showdown:ultra_burst"),
                        List.of("prism_fusion=dawn"),
                        pokemon,
                        "revert_aspects"
                );
            } else {
                pokemon.getPersistentData().putString("necrozma_form", "prism_fusion=dawn");
                AspectUtils.appendRevertDataPokemon(
                        Effect.getEffect("mega_showdown:ultra_burst"),
                        List.of("prism_fusion=dawn"),
                        pokemon,
                        "revert_aspects"
                );
            }

            Effect.getEffect("mega_showdown:ultra_burst").applyEffects(pokemon, List.of("prism_fusion=ultra"), null);
            pokemon.setTradeable(false);
        } else if (pokemon.getAspects().contains("ultra-fusion")) {
            String org_form = pokemon.getPersistentData().getString("necrozma_form");
            pokemon.getPersistentData().remove("necrozma_form");

            Effect.getEffect("mega_showdown:ultra_burst").revertEffects(pokemon, List.of(org_form), null);
            pokemon.setTradeable(true);
        }
    }

    public static void ultraBurstInBattle(Pokemon pokemon, BattlePokemon battlePokemon) {
        if (pokemon.getAspects().contains("dawn-fusion")) {
            AspectUtils.appendRevertDataPokemon(
                    Effect.getEffect("mega_showdown:ultra_burst"),
                    List.of("prism_fusion=dawn"),
                    pokemon,
                    "battle_end_revert"
            );
        } else {
            AspectUtils.appendRevertDataPokemon(
                    Effect.getEffect("mega_showdown:ultra_burst"),
                    List.of("prism_fusion=dusk"),
                    pokemon,
                    "battle_end_revert"
            );
        }

        Effect.getEffect("mega_showdown:ultra_burst").applyEffectsBattle(pokemon, List.of("prism_fusion=ultra"), null, battlePokemon);
    }

    public static boolean canUltraBurst(Pokemon pokemon) {
        ItemStack heldItem = pokemon.heldItem();
        ZCrystal zCrystal = RegistryLocator.getComponent(ZCrystal.class, heldItem);
        if (zCrystal == null) return false;

        if (pokemon.getSpecies().getName().equals("Necrozma") && zCrystal.showdown_item_id().equals("ultranecroziumz")) {
            return pokemon.getAspects().contains("dawn-fusion") || pokemon.getAspects().contains("dusk-fusion");
        }
        return false;
    }
}
