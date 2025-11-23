package com.github.yajatkaul.mega_showdown.gimmick;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.mod.common.pokemon.Pokemon;
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
        if (hasOmniRing) {
            return true;
        } else if (gimmick == ShowdownMoveset.Gimmick.DYNAMAX) {
            if (!MegaShowdownConfig.dynamax) {
                return false;
            }

            return AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.DYNAMAX_BAND);
        } else if (gimmick == ShowdownMoveset.Gimmick.TERASTALLIZATION) {
            if (!MegaShowdownConfig.teralization) {
                return false;
            }

            boolean hasTerapagos = PlayerUtils.hasPokemon(player, "Terapagos");

            ItemStack teraOrb = AccessoriesUtils.findFirstItemWithTag(player, MegaShowdownTags.Items.TERA_ORB);
            if (teraOrb == ItemStack.EMPTY) {
                return false;
            }

            if (hasTerapagos) {
                teraOrb.setDamageValue(0);
            }

            return teraOrb.getDamageValue() < 100;
        } else if (gimmick == ShowdownMoveset.Gimmick.Z_POWER) {
            if (!MegaShowdownConfig.zMoves) {
                return false;
            }

            return AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.Z_RING);
        } else if (gimmick == ShowdownMoveset.Gimmick.MEGA_EVOLUTION) {
            if (!MegaShowdownConfig.mega) {
                return false;
            }

            return AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.MEGA_BRACELET) &&
                    !MegaGimmick.hasMega(player);
        }

        return false;
    }
}
