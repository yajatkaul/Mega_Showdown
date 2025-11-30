package com.github.yajatkaul.mega_showdown.gimmick;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.tag.MegaShowdownTags;
import com.github.yajatkaul.mega_showdown.utils.AccessoriesUtils;
import com.github.yajatkaul.mega_showdown.utils.PlayerUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class GimmickTurnCheck {
    public static void check(ServerPlayer player) {
        GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);

        if (PlayerUtils.isBlockNearby(player, MegaShowdownTags.Blocks.POWER_SPOT, MegaShowdownConfig.powerSpotRange)
                || MegaShowdownConfig.dynamaxAnywhere) {
            if (hasGimmick(ShowdownMoveset.Gimmick.DYNAMAX, player)) {
                data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon", "dynamax_band"));
            } else {
                data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon", "dynamax_band"));
            }
        } else {
            data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon", "dynamax_band"));
        }

        if (hasGimmick(ShowdownMoveset.Gimmick.TERASTALLIZATION, player)) {
            data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon", "tera_orb"));
        } else {
            data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon", "tera_orb"));
        }

        if (hasGimmick(ShowdownMoveset.Gimmick.MEGA_EVOLUTION, player)) {
            data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon", "key_stone"));
        } else {
            data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon", "key_stone"));
        }

        if (hasGimmick(ShowdownMoveset.Gimmick.Z_POWER, player)) {
            data.getKeyItems().add(ResourceLocation.fromNamespaceAndPath("cobblemon", "z_ring"));
        } else {
            data.getKeyItems().remove(ResourceLocation.fromNamespaceAndPath("cobblemon", "z_ring"));
        }
    }

    public static boolean hasGimmick(ShowdownMoveset.Gimmick gimmick, ServerPlayer player) {
        boolean hasOmniRing = AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.OMNI_RING);

        switch (gimmick) {
            case DYNAMAX -> {
                if (!MegaShowdownConfig.dynamax) return false;
                if (hasOmniRing) return true;

                return AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.DYNAMAX_BAND);
            }
            case TERASTALLIZATION -> {
                if (!MegaShowdownConfig.teralization) return false;
                if (hasOmniRing) return true;

                boolean hasTerapagos = PlayerUtils.hasPokemon(player, "Terapagos");
                ItemStack teraOrb = AccessoriesUtils.findFirstItemWithTag(player, MegaShowdownTags.Items.TERA_ORB);

                if (teraOrb.isEmpty()) return false;

                if (hasTerapagos) {
                    teraOrb.setDamageValue(0);
                }

                return teraOrb.getDamageValue() < 100;
            }
            case Z_POWER -> {
                if (!MegaShowdownConfig.zMoves) return false;
                if (hasOmniRing) return true;

                return AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.Z_RING);
            }
            case MEGA_EVOLUTION -> {
                if (!MegaShowdownConfig.mega) return false;

                boolean hasMegaAccess = AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.MEGA_BRACELET);

                return (hasMegaAccess || hasOmniRing) && !MegaGimmick.hasMega(player);
            }
        }

        return false;
    }
}
