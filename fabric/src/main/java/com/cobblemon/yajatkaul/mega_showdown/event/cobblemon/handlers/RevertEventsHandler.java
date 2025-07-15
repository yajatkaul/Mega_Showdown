package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent;
import com.cobblemon.mod.common.api.events.battles.BattleStartedPostEvent;
import com.cobblemon.mod.common.api.events.battles.BattleStartedPreEvent;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
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
    public static Unit battleStarted(BattleStartedPreEvent battleEvent) {
        for (BattleActor pokemon : battleEvent.getBattle().getActors()) {
            for (BattlePokemon pk : pokemon.getPokemonList()) {
                if (pk.getEffectedPokemon().getAspects().contains("core-percent")) {
                    battleEvent.cancel();
                    return Unit.INSTANCE;
                }
            }
        }

        for (ServerPlayerEntity player : battleEvent.getBattle().getPlayers()) {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
            checkKeldeo(playerPartyStore);
            boolean hasTerapagos = false;
            for (Pokemon pokemon : playerPartyStore) {
                if(pokemon.getSpecies().getName().equals("Terapagos")){
                    hasTerapagos = true;
                }
                EventUtils.revertFormesEnd(pokemon);
            }

            GeneralPlayerData data = Cobblemon.INSTANCE.getPlayerDataManager().getGenericData(player);

            boolean hasDMaxItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> item.isIn(ModTags.Items.DYNAMAX_BAND))).orElse(false);

            if (isBlockNearby(player, ModBlocks.POWER_SPOT, MegaShowdownConfig.powerSpotRange.get()) || MegaShowdownConfig.dynamaxAnywhere.get()) {
                if ((player.getOffHandStack().getItem() instanceof Dynamax
                        || player.getOffHandStack().isIn(ModTags.Items.DYNAMAX_BAND)
                        || hasDMaxItemTrinkets) && MegaShowdownConfig.dynamax.get()) {
                    data.getKeyItems().add(Identifier.of("cobblemon", "dynamax_band"));
                } else {
                    data.getKeyItems().remove(Identifier.of("cobblemon", "dynamax_band"));
                }
            } else {
                data.getKeyItems().remove(Identifier.of("cobblemon", "dynamax_band"));
            }

            boolean hasTeraItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> (item.getItem() instanceof TeraItem))).orElse(false);

            ItemStack teraOrb = TrinketsApi.getTrinketComponent(player)
                    .flatMap(component -> component.getAllEquipped().stream()
                            .map(Pair::getRight)
                            .filter(stack -> !stack.isEmpty() && (
                                    stack.getItem() instanceof TeraItem
                            ))
                            .findFirst()
                    ).orElse(null);

            if(teraOrb != null && hasTerapagos){
                teraOrb.setDamage(0);
            }

            if (teraOrb == null || teraOrb.getDamage() >= 100) {
                hasTeraItemTrinkets = false;
            }

            if (hasTeraItemTrinkets && MegaShowdownConfig.teralization.get()) {
                data.getKeyItems().add(Identifier.of("cobblemon", "tera_orb"));
            } else {
                data.getKeyItems().remove(Identifier.of("cobblemon", "tera_orb"));
            }

            if (MegaShowdownConfig.revertMegas.get() && MegaShowdownConfig.mega.get() && MegaLogic.Possible(player, true)
                    && !FormChangeHelper.hasMega(player)) {
                data.getKeyItems().add(Identifier.of("cobblemon", "key_stone"));
            } else {
                data.getKeyItems().remove(Identifier.of("cobblemon", "key_stone"));
            }

            boolean hasZItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                    trinkets.isEquipped(item -> (item.getItem() instanceof ZRingItem
                            || item.isIn(ModTags.Items.Z_RINGS)))).orElse(false);

            if ((player.getOffHandStack().getItem() instanceof ZRingItem
                    || player.getOffHandStack().isIn(ModTags.Items.Z_RINGS)
                    || hasZItemTrinkets) && MegaShowdownConfig.zMoves.get()) {
                data.getKeyItems().add(Identifier.of("cobblemon", "z_ring"));
            } else {
                data.getKeyItems().remove(Identifier.of("cobblemon", "z_ring"));
            }
        }

        return Unit.INSTANCE;
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

        boolean isMega = pokemon.getAspects().stream()
                .anyMatch(aspect -> aspect.startsWith("mega"));

        if (isMega) {
            MegaLogic.Devolve(pokemon, true);
        }

        return Unit.INSTANCE;
    }

    private static void checkKeldeo(PlayerPartyStore pokemons) {
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getSpecies().getName().equals("Keldeo")) {
                boolean hasMove = false;

                for (Move move : pokemon.getMoveSet().getMoves()) {
                    if (move.getName().equals(Moves.INSTANCE.getByName("secretsword").getName())) {
                        hasMove = true;
                    }
                }

                if (pokemon.getAspects().contains("resolute-form")) {
                    if (!hasMove) {
                        new StringSpeciesFeature("sword_form", "ordinary").apply(pokemon);
                        if (pokemon.getEntity() != null) {
                            EventUtils.playEvolveAnimation(pokemon.getEntity());
                        }
                    }
                } else {
                    if (hasMove) {
                        new StringSpeciesFeature("sword_form", "resolute").apply(pokemon);
                        if (pokemon.getEntity() != null) {
                            EventUtils.playEvolveAnimation(pokemon.getEntity());
                        }
                    }
                }
            }
        }
    }

    public static Unit hookBattleEnded(BattleStartedPostEvent event) {
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
