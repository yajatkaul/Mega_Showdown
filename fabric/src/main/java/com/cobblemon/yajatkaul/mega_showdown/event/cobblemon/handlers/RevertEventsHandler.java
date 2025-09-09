package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent;
import com.cobblemon.mod.common.api.events.battles.BattleStartedEvent;
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
import com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax.Dynamax;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.tera.TeraItem;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove.ZRingItem;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import dev.emi.trinkets.api.TrinketsApi;
import kotlin.Unit;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

public class RevertEventsHandler {
    public static Unit battleStarted(BattleStartedEvent.Pre battleEvent) {
        for (BattleActor pokemon : battleEvent.getBattle().getActors()) {
            for (BattlePokemon pk : pokemon.getPokemonList()) {
                if (pk.getEffectedPokemon().getAspects().contains("core-percent")) {
                    battleEvent.cancel();
                    return Unit.INSTANCE;
                }
            }
        }

        for (ServerPlayerEntity player : battleEvent.getBattle().getPlayers()) {
            GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);

            if (isBlockNearby(player, ModBlocks.POWER_SPOT, MegaShowdownConfig.powerSpotRange.get())
                    || MegaShowdownConfig.dynamaxAnywhere.get()) {
                if (hasGimmick(ShowdownMoveset.Gimmick.DYNAMAX, player)) {
                    data.getKeyItems().add(Identifier.of("cobblemon", "dynamax_band"));
                } else {
                    data.getKeyItems().remove(Identifier.of("cobblemon", "dynamax_band"));
                }
            } else {
                data.getKeyItems().remove(Identifier.of("cobblemon", "dynamax_band"));
            }

            if (hasGimmick(ShowdownMoveset.Gimmick.TERASTALLIZATION, player)) {
                data.getKeyItems().add(Identifier.of("cobblemon", "tera_orb"));
            } else {
                data.getKeyItems().remove(Identifier.of("cobblemon", "tera_orb"));
            }

            if (hasGimmick(ShowdownMoveset.Gimmick.MEGA_EVOLUTION, player)) {
                data.getKeyItems().add(Identifier.of("cobblemon", "key_stone"));
            } else {
                data.getKeyItems().remove(Identifier.of("cobblemon", "key_stone"));
            }

            if (hasGimmick(ShowdownMoveset.Gimmick.Z_POWER, player)) {
                data.getKeyItems().add(Identifier.of("cobblemon", "z_ring"));
            } else {
                data.getKeyItems().remove(Identifier.of("cobblemon", "z_ring"));
            }
        }

        return Unit.INSTANCE;
    }

    public static boolean hasGimmick(ShowdownMoveset.Gimmick gimmick, ServerPlayerEntity player) {
        if(gimmick == ShowdownMoveset.Gimmick.DYNAMAX) {
            if(!MegaShowdownConfig.dynamax.get()) {
                return false;
            }

            boolean hasDMaxItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> item.isIn(ModTags.Items.DYNAMAX_BAND))).orElse(false);

            return player.getOffHandStack().isIn(ModTags.Items.DYNAMAX_BAND)
                    || player.getMainHandStack().isIn(ModTags.Items.DYNAMAX_BAND)
                    || hasDMaxItemTrinkets;
        }

        else if(gimmick == ShowdownMoveset.Gimmick.TERASTALLIZATION) {
            if(!MegaShowdownConfig.teralization.get()) {
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

            ItemStack teraOrb = TrinketsApi.getTrinketComponent(player)
                    .flatMap(component -> component.getAllEquipped().stream()
                            .map(Pair::getRight)
                            .filter(stack -> !stack.isEmpty() && (
                                    stack.isIn(ModTags.Items.TERA_ORBS)
                            ))
                            .findFirst()
                    ).orElse(null);

            if(teraOrb == null) {
                return false;
            }

            if (hasTerapagos) {
                teraOrb.setDamage(0);
            }

            return teraOrb.getDamage() < 100;
        }

        else if(gimmick == ShowdownMoveset.Gimmick.Z_POWER) {
            if(!MegaShowdownConfig.zMoves.get()) {
                return false;
            }

            boolean hasZPowerItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> item.isIn(ModTags.Items.Z_RINGS))).orElse(false);

            return player.getOffHandStack().isIn(ModTags.Items.Z_RINGS)
                    || player.getMainHandStack().isIn(ModTags.Items.Z_RINGS)
                    || hasZPowerItemTrinkets;
        }

        else if(gimmick == ShowdownMoveset.Gimmick.MEGA_EVOLUTION) {
            if(!MegaShowdownConfig.mega.get()) {
                return false;
            }

            boolean hasKeystoneItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> item.isIn(ModTags.Items.MEGA_BRACELETS))).orElse(false);

            return (player.getOffHandStack().isIn(ModTags.Items.MEGA_BRACELETS)
                    || player.getMainHandStack().isIn(ModTags.Items.MEGA_BRACELETS)
                    || hasKeystoneItemTrinkets)
                    && !FormChangeHelper.hasMega(player);
        }

        return false;
    }

    public static boolean isBlockNearby(ServerPlayerEntity player, Block targetBlock, int radius) {
        BlockPos playerPos = player.getBlockPos();
        ServerWorld world = player.getServerWorld();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos checkPos = playerPos.add(dx, dy, dz);
                    if (world.getBlockState(checkPos).isOf(targetBlock)) {
                        return true; // Found the block
                    }
                }
            }
        }

        return false; // Not found
    }

    public static Unit devolveFainted(BattleFaintedEvent battleFaintedEvent) {
        Pokemon pokemon = battleFaintedEvent.getKilled().getOriginalPokemon();
        ServerPlayerEntity serverPlayer = battleFaintedEvent.getKilled().getOriginalPokemon().getOwnerPlayer();

        if (serverPlayer == null || serverPlayer.getWorld().isClient) {
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

    public static Unit hookBattleEnded(BattleStartedEvent.Post event) {
        event.getBattle().getOnEndHandlers().add(battle -> {
            battle.getPlayers().forEach(serverPlayer -> {
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
                for (Pokemon pokemon : playerPartyStore) {

                    EventUtils.revertFormesEnd(pokemon);

                    if (pokemon.getEntity() != null) {
                        pokemon.getEntity().removeStatusEffect(StatusEffects.GLOWING);
                    }
                }
            });

            return Unit.INSTANCE;
        });

        return Unit.INSTANCE;
    }
}
