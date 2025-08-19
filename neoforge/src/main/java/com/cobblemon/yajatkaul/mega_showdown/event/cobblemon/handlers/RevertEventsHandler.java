package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent;
import com.cobblemon.mod.common.api.events.battles.BattleStartedPostEvent;
import com.cobblemon.mod.common.api.events.battles.BattleStartedPreEvent;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.EventUtils;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.FormChangeHelper;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class RevertEventsHandler {
    public static Unit devolveFainted(BattleFaintedEvent battleFaintedEvent) {
        Pokemon pokemon = battleFaintedEvent.getKilled().getOriginalPokemon();
        ServerPlayer serverPlayer = battleFaintedEvent.getKilled().getOriginalPokemon().getOwnerPlayer();

        if (serverPlayer == null) {
            return Unit.INSTANCE;
        }

        pokemon.getPersistentData().putBoolean("is_tera", false);

        boolean isMega = pokemon.getAspects().stream()
                .anyMatch(aspect -> aspect.startsWith("mega"));

        if (isMega) {
            MegaLogic.Devolve(pokemon, true);
        }

        return Unit.INSTANCE;
    }

    public static Unit battleStarted(@NotNull BattleStartedPreEvent battleEvent) {
        for (BattleActor pokemon : battleEvent.getBattle().getActors()) {
            for (BattlePokemon pk : pokemon.getPokemonList()) {
                if (pk.getEffectedPokemon().getAspects().contains("core-percent")) {
                    battleEvent.cancel();
                    return Unit.INSTANCE;
                }
            }
        }

        for (ServerPlayer player : battleEvent.getBattle().getPlayers()) {
            GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);

            if (isBlockNearby(player, ModBlocks.POWER_SPOT.get(), MegaShowdownConfig.powerSpotRange)
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

        return Unit.INSTANCE;
    }

    public static boolean hasGimmick(ShowdownMoveset.Gimmick gimmick, ServerPlayer player) {
        if(gimmick == ShowdownMoveset.Gimmick.DYNAMAX) {
            if(!MegaShowdownConfig.dynamax) {
                return false;
            }

            boolean hasDMaxItemTrinkets = CuriosApi.getCuriosInventory(player)
                    .map(inventory -> inventory
                            .isEquipped(stack -> stack.is(ModTags.Items.DYNAMAX_BAND)))
                    .orElse(false);

            return player.getOffhandItem().is(ModTags.Items.DYNAMAX_BAND)
                    || player.getMainHandItem().is(ModTags.Items.DYNAMAX_BAND)
                    || hasDMaxItemTrinkets;
        }

        else if(gimmick == ShowdownMoveset.Gimmick.TERASTALLIZATION) {
            if(!MegaShowdownConfig.teralization) {
                return false;
            }

            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

            boolean hasTerapagos = false;
            for (Pokemon pokemon : playerPartyStore) {
                if (pokemon.getSpecies().getName().equals("Terapagos")) {
                    hasTerapagos = true;
                }
                EventUtils.revertFormesEnd(pokemon);
            }

            ItemStack teraOrb = CuriosApi.getCuriosInventory(player)
                    .flatMap(curiosInventory -> curiosInventory.findFirstCurio(
                            stack -> (stack.is(ModTags.Items.TERA_ORBS))
                    ))
                    .map(SlotResult::stack)
                    .orElse(null);

            if(teraOrb == null) {
                return false;
            }

            if (hasTerapagos) {
                teraOrb.setDamageValue(0);
            }

            return teraOrb.getDamageValue() < 100;
        }

        else if(gimmick == ShowdownMoveset.Gimmick.Z_POWER) {
            if(!MegaShowdownConfig.zMoves) {
                return false;
            }

            boolean hasZPowerItemTrinkets = CuriosApi.getCuriosInventory(player)
                    .map(inventory -> inventory
                            .isEquipped(stack -> stack.is(ModTags.Items.Z_RINGS)))
                    .orElse(false);

            return player.getOffhandItem().is(ModTags.Items.Z_RINGS)
                    || player.getMainHandItem().is(ModTags.Items.Z_RINGS)
                    || hasZPowerItemTrinkets;
        }

        else if(gimmick == ShowdownMoveset.Gimmick.MEGA_EVOLUTION) {
            if(!MegaShowdownConfig.mega) {
                return false;
            }

            boolean hasKeystoneItemTrinkets = CuriosApi.getCuriosInventory(player)
                    .map(inventory -> inventory
                            .isEquipped(stack -> stack.is(ModTags.Items.MEGA_BRACELETS)))
                    .orElse(false);

            return (player.getOffhandItem().is(ModTags.Items.MEGA_BRACELETS)
                    || player.getMainHandItem().is(ModTags.Items.MEGA_BRACELETS)
                    || hasKeystoneItemTrinkets)
                    && !FormChangeHelper.hasMega(player);
        }

        return false;
    }

    public static boolean isBlockNearby(ServerPlayer player, Block targetBlock, int radius) {
        BlockPos playerPos = player.blockPosition();
        ServerLevel level = player.serverLevel();

        // scan a cube around the player
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos checkPos = playerPos.offset(dx, dy, dz);
                    if (level.getBlockState(checkPos).is(targetBlock)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static Unit hookBattleEnded(BattleStartedPostEvent event) {
        event.getBattle().getOnEndHandlers().add(battle -> {
            battle.getPlayers().forEach(serverPlayer -> {
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
                for (Pokemon pokemon : playerPartyStore) {

                    EventUtils.revertFormesEnd(pokemon);

                    if (pokemon.getEntity() != null) {
                        pokemon.getEntity().removeEffect(MobEffects.GLOWING);
                    }
                }
            });

            return Unit.INSTANCE;
        });

        return Unit.INSTANCE;
    }
}
