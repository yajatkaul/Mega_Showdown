package com.github.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.battles.BattleStartedEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.MegaEvolutionEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.item.custom.FormChangeItem;
import com.github.yajatkaul.mega_showdown.item.custom.mega.MegaStone;
import com.github.yajatkaul.mega_showdown.tag.ModTags;
import com.github.yajatkaul.mega_showdown.utils.AccessoriesUtils;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import com.github.yajatkaul.mega_showdown.utils.PlayerUtils;
import io.wispforest.accessories.Accessories;
import io.wispforest.accessories.api.AccessoriesCapability;
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

@SuppressWarnings("SameReturnValue")
public class CobbleEvents {
    public static void register() {
        CobblemonEvents.HELD_ITEM_PRE.subscribe(Priority.NORMAL, CobbleEvents::heldItemChange);
        CobblemonEvents.MEGA_EVOLUTION.subscribe(Priority.NORMAL, CobbleEvents::megaEvolution);
        CobblemonEvents.BATTLE_STARTED_POST.subscribe(Priority.NORMAL, CobbleEvents::hookBattleEnded);
        CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, CobbleEvents::battleStarting);
    }

    public static boolean hasGimmick(ShowdownMoveset.Gimmick gimmick, ServerPlayer player) {
        if (gimmick == ShowdownMoveset.Gimmick.DYNAMAX) {
            if (!MegaShowdownConfig.dynamax) {
                return false;
            }

            boolean hasDMaxItemTrinkets = AccessoriesUtils.checkTagInAccessories(player, ModTags.Items.DYNAMAX_BANDS);

            return player.getOffhandItem().is(ModTags.Items.DYNAMAX_BANDS)
                    || player.getMainHandItem().is(ModTags.Items.DYNAMAX_BANDS)
                    || hasDMaxItemTrinkets;
        } else if (gimmick == ShowdownMoveset.Gimmick.TERASTALLIZATION) {
            if (!MegaShowdownConfig.teralization) {
                return false;
            }

            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

            boolean hasTerapagos = false;
            for (Pokemon pokemon : playerPartyStore) {
                if (pokemon.getSpecies().getName().equals("Terapagos")) {
                    hasTerapagos = true;
                }
                revertPokemonsIfRequired(pokemon);
            }

            ItemStack teraOrb = AccessoriesUtils.findFirstItemWithTag(player, ModTags.Items.TERA_ORBS);

            if (teraOrb == null) {
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

            boolean hasZPowerItemTrinkets = AccessoriesUtils.checkTagInAccessories(player, ModTags.Items.Z_RINGS);

            return player.getOffhandItem().is(ModTags.Items.Z_RINGS)
                    || player.getMainHandItem().is(ModTags.Items.Z_RINGS)
                    || hasZPowerItemTrinkets;
        } else if (gimmick == ShowdownMoveset.Gimmick.MEGA_EVOLUTION) {
            if (!MegaShowdownConfig.mega) {
                return false;
            }

            boolean hasKeystoneItemTrinkets = AccessoriesUtils.checkTagInAccessories(player, ModTags.Items.MEGA_BRACELETS);

            return (player.getOffhandItem().is(ModTags.Items.MEGA_BRACELETS)
                    || player.getMainHandItem().is(ModTags.Items.MEGA_BRACELETS)
                    || hasKeystoneItemTrinkets)
                    && !MegaGimmick.hasMega(player);
        }

        return false;
    }

    private static Unit battleStarting(BattleStartedEvent.Pre event) {
        for (ServerPlayer player : event.getBattle().getPlayers()) {
            GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);

            if (PlayerUtils.isBlockNearby(player, ModTags.Blocks.POWER_SPOT, MegaShowdownConfig.powerSpotRange)
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

    private static Unit hookBattleEnded(BattleStartedEvent.Post event) {
        event.getBattle().getOnEndHandlers().add((battle -> {
            battle.getPlayers().forEach(serverPlayer -> {
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
                revertPokemonsIfRequired(playerPartyStore);
            });

            return Unit.INSTANCE;
        }));

        return Unit.INSTANCE;
    }

    private static void revertPokemonsIfRequired(Pokemon pokemon) {
        if (pokemon.getPersistentData().contains("battle_end_revert")) {
            AspectUtils.applyAspects(
                    pokemon,
                    AspectUtils.decodeNbt(pokemon.getPersistentData().getList("battle_end_revert", 8))
            );
        }

        if (pokemon.getPersistentData().contains("apply_aspects")) {
            List<String> aspects =
                    AspectUtils.decodeNbt(pokemon.getPersistentData().getList("apply_aspects", 8));

            AspectUtils.applyAspects(pokemon, aspects);
        }

        if (pokemon.getEntity() != null) {
            pokemon.getEntity().removeEffect(MobEffects.GLOWING);
        }
    }
    private static void revertPokemonsIfRequired(PlayerPartyStore playerPartyStore) {
        for (Pokemon pokemon : playerPartyStore) {
            revertPokemonsIfRequired(pokemon);
        }
    }

    private static Unit megaEvolution(MegaEvolutionEvent event) {
        Pokemon pokemon = event.getPokemon().getEntity().getPokemon();
        if (pokemon.heldItem().getItem() instanceof MegaStone megaStone) {
            if (megaStone.getMegaProps().canMega(pokemon)) {
                MegaGimmick.megaEvolveInBattle(
                        pokemon,
                        event.getBattle(),
                        megaStone.getMegaProps().aspect_conditions().apply_aspects(),
                        megaStone.getMegaProps().aspect_conditions().revert_aspects()
                );
            }
        }

        return Unit.INSTANCE;
    }

    private static Unit heldItemChange(HeldItemEvent.Pre event) {
        if (event.getPokemon().getPersistentData().getBoolean("form_changing")) {
            event.cancel();
            return Unit.INSTANCE;
        }

        if (event.getReturning().getItem() instanceof FormChangeItem formChangeItem) {
            formChangeItem.revert(event.getPokemon());
        }

        if (event.getReceiving().getItem() instanceof FormChangeItem formChangeItem) {
            formChangeItem.apply(event.getPokemon());
        }

        return Unit.INSTANCE;
    }
}
