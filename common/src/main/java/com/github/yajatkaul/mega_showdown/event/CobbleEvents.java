package com.github.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.drop.ItemDropEntry;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent;
import com.cobblemon.mod.common.api.events.battles.BattleStartedEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.MegaEvolutionEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.TerastallizationEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.ZMoveUsedEvent;
import com.cobblemon.mod.common.api.events.drops.LootDroppedEvent;
import com.cobblemon.mod.common.api.events.pokeball.ThrownPokeballHitEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.events.pokemon.PokemonCapturedEvent;
import com.cobblemon.mod.common.api.events.pokemon.PokemonSentEvent;
import com.cobblemon.mod.common.api.events.pokemon.healing.PokemonHealedEvent;
import com.cobblemon.mod.common.api.item.HealingSource;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.player.GeneralPlayerData;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.mod.common.battles.dispatch.UntilDispatch;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.github.yajatkaul.mega_showdown.api.event.DynamaxEndCallback;
import com.github.yajatkaul.mega_showdown.api.event.DynamaxStartCallback;
import com.github.yajatkaul.mega_showdown.api.event.UltraBurstCallback;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.gimmick.UltraGimmick;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import com.github.yajatkaul.mega_showdown.item.custom.form_change.FormChangeItem;
import com.github.yajatkaul.mega_showdown.item.custom.mega.MegaStone;
import com.github.yajatkaul.mega_showdown.tag.ModTags;
import com.github.yajatkaul.mega_showdown.utils.*;
import kotlin.Unit;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("SameReturnValue")
public class CobbleEvents {
    public static void register() {
        CobblemonEvents.HELD_ITEM_PRE.subscribe(Priority.NORMAL, CobbleEvents::heldItemChange);
        CobblemonEvents.MEGA_EVOLUTION.subscribe(Priority.NORMAL, CobbleEvents::megaEvolution);
        CobblemonEvents.BATTLE_STARTED_POST.subscribe(Priority.NORMAL, CobbleEvents::hookBattleEnded);
        CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, CobbleEvents::battleStarting);
        CobblemonEvents.TERASTALLIZATION.subscribe(Priority.NORMAL, CobbleEvents::terrastallizationUsed);
        CobblemonEvents.POKEMON_HEALED.subscribe(Priority.NORMAL, CobbleEvents::healedPokemons);
        CobblemonEvents.ZPOWER_USED.subscribe(Priority.NORMAL, CobbleEvents::zMovesUsed);
        CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, CobbleEvents::devolveFainted);
        CobblemonEvents.POKEMON_SENT_POST.subscribe(Priority.NORMAL, CobbleEvents::pokemonSent);
        CobblemonEvents.THROWN_POKEBALL_HIT.subscribe(Priority.NORMAL, CobbleEvents::pokeballHit);
        CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, CobbleEvents::fixTera);
        CobblemonEvents.LOOT_DROPPED.subscribe(Priority.NORMAL, CobbleEvents::dropShardPokemon);

        DynamaxStartCallback.EVENT.register(CobbleEvents::dynamaxStarted);
        DynamaxEndCallback.EVENT.register(CobbleEvents::dynamaxEnded);
        UltraBurstCallback.EVENT.register(CobbleEvents::ultraBurst);
    }

    private static Unit dropShardPokemon(LootDroppedEvent event) {
        if (!MegaShowdownConfig.teralization) {
            return Unit.INSTANCE;
        }

        if (event.getEntity() instanceof  PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            Item correspondingTeraShard = TeraHelper.getTeraShardForType(pokemon.getTypes());
            ItemDropEntry teraShardDropEntry = new ItemDropEntry();
            teraShardDropEntry.setItem(BuiltInRegistries.ITEM.getKey(correspondingTeraShard));

            Random random = new Random();

            boolean otherSuccess = random.nextDouble() < (MegaShowdownConfig.teraShardDropRate / 100.0);
            boolean stellarSuccess = random.nextDouble() < (MegaShowdownConfig.stellarShardDropRate / 100.0);

            if (otherSuccess) {
                event.getDrops().add(teraShardDropEntry);
            } else if (stellarSuccess) {
                teraShardDropEntry.setItem(BuiltInRegistries.ITEM.getKey(MegaShowdownItems.STELLAR_TERA_SHARD.get()));
                event.getDrops().add(teraShardDropEntry);
            }
        }

        return Unit.INSTANCE;
    }

    private static Unit fixTera(PokemonCapturedEvent event) {
        Pokemon pokemon = event.getPokemon();

        if (pokemon.getSpecies().getName().equals("Ogerpon")) {
            if (pokemon.getAspects().contains("teal-mask")) {
                pokemon.setTeraType(TeraTypes.getGRASS());
            } else if (pokemon.getAspects().contains("wellspring-mask")) {
                pokemon.setTeraType(TeraTypes.getWATER());
            } else if (pokemon.getAspects().contains("hearthflame-mask")) {
                pokemon.setTeraType(TeraTypes.getFIRE());
            } else if (pokemon.getAspects().contains("cornerstone-mask")) {
                pokemon.setTeraType(TeraTypes.getROCK());
            } else {
                pokemon.setTeraType(TeraHelper.getTeraFromElement(pokemon.getPrimaryType()));
            }
        } else if (pokemon.getSpecies().getName().equals("Terapagos")) {
            pokemon.setTeraType(TeraTypes.getSTELLAR());
        }

        return Unit.INSTANCE;
    }

    private static Unit pokeballHit(ThrownPokeballHitEvent event) {
        if (event.getPokemon().getAspects().contains("core-percent")) {
            event.cancel();
        }

        return Unit.INSTANCE;
    }

    private static Unit pokemonSent(PokemonSentEvent.Post event) {
        PokemonEntity pokemon = event.getPokemonEntity();

        if (pokemon.getPokemon().getPersistentData().getBoolean("is_tera")) {
            GlowHandler.applyTeraGlow(pokemon);
        }

        return Unit.INSTANCE;
    }

    private static Unit devolveFainted(BattleFaintedEvent event) {
        Pokemon pokemon = event.getKilled().getEffectedPokemon();

        if (pokemon.getPersistentData().contains("battle_end_revert")) {
            AspectUtils.applyAspects(
                    pokemon,
                    AspectUtils.decodeNbt(pokemon.getPersistentData().getList("battle_end_revert", 8))
            );
        }

        if (pokemon.getPersistentData().getBoolean("is_tera")) {
            pokemon.getPersistentData().putBoolean("is_tera", false);
        }

        return Unit.INSTANCE;
    }

    private static void ultraBurst(PokemonBattle battle, BattlePokemon battlePokemon) {
        Pokemon pokemon = battlePokemon.getEntity().getPokemon();
        UltraGimmick.ultraBurstInBattle(pokemon, battlePokemon);
    }

    private static void dynamaxEnded(PokemonBattle battle, BattlePokemon battlePokemon) {
        Pokemon pokemon = battlePokemon.getEffectedPokemon();
        AspectUtils.scaleDownDynamax(pokemon.getEntity());

        if (battlePokemon.getEntity().getPokemon().getAspects().contains("gmax")) {
            battle.dispatchToFront(() -> {
                new StringSpeciesFeature("dynamax_form", "none").apply(pokemon);
                AspectUtils.updatePackets(battlePokemon);
                return new UntilDispatch(() -> true);
            });
        }
    }

    private static void dynamaxStarted(PokemonBattle battle, BattlePokemon battlePokemon, Boolean gmax) {
        Pokemon pokemon = battlePokemon.getEffectedPokemon();
        if (gmax) {
            pokemon.getPersistentData().put("battle_end_revert",
                    AspectUtils.makeNbt(List.of("dynamax_form=none")));
            battle.dispatchToFront(() -> {
                new StringSpeciesFeature("dynamax_form", "gmax").apply(pokemon);
                AspectUtils.updatePackets(battlePokemon);
                return new UntilDispatch(() -> true);
            });
        }

        pokemon.getPersistentData().putBoolean("is_max", true);

        AspectUtils.scaleUpDynamax(pokemon.getEntity());
    }

    private static Unit zMovesUsed(ZMoveUsedEvent event) {
        PokemonEntity pokemonEntity = event.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pokemon = pokemonEntity.getPokemon();

        AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "z/z_moves");

        if (pokemon.getSpecies().getName().equals("Pikachu") && pokemon.getAspects().contains("partner-cap")) {
            AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "bond/ash_pikachu");
        }

        GlowHandler.applyZGlow(pokemonEntity);

        ParticlesList.zMoves.applyEffectsBattle(pokemonEntity, List.of(), null, event.getPokemon());

        return Unit.INSTANCE;
    }

    private static Unit healedPokemons(PokemonHealedEvent pokemonHealedEvent) {
        ServerPlayer player = pokemonHealedEvent.getPokemon().getOwnerPlayer();
        if (player == null || pokemonHealedEvent.getSource() != HealingSource.Force.INSTANCE) {
            return Unit.INSTANCE;
        }

        ItemStack teraOrb = AccessoriesUtils.findFirstItemWithTag(player, ModTags.Items.TERA_ORB);
        if (teraOrb != ItemStack.EMPTY) {
            teraOrb.setDamageValue(0);
        }

        return Unit.INSTANCE;
    }

    private static Unit terrastallizationUsed(TerastallizationEvent event) {
        PokemonEntity pokemonEntity = event.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pokemon = pokemonEntity.getPokemon();

        AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "tera/terastallized");

        if (pokemon.getSpecies().getName().equals("Terapagos")) {
            new StringSpeciesFeature("tera_form", "stellar").apply(pokemon);
            AspectUtils.updatePackets(event.getPokemon());
            ParticlesList.endRodParticles.apply(pokemonEntity);
        } else if (pokemon.getSpecies().getName().equals("Ogerpon")) {
            new FlagSpeciesFeature("embody-aspect", true).apply(pokemon);
            AspectUtils.updatePackets(event.getPokemon());
        }

        pokemon.getPersistentData().putBoolean("is_tera", true);
        GlowHandler.applyTeraGlow(pokemonEntity);

        ServerPlayer player = pokemon.getOwnerPlayer();
        if (!PlayerUtils.hasPokemon(player, "Terapagos")) {
            ItemStack teraOrb = AccessoriesUtils.findFirstItemWithTag(player, ModTags.Items.TERA_ORB);
            if (teraOrb != ItemStack.EMPTY) {
                teraOrb.setDamageValue(teraOrb.getDamageValue() + 10);
            }
        }

        event.getBattle().dispatchWaitingToFront(2f, () -> {
            PokemonBehaviourHelper.Companion.playAnimation(pokemonEntity, Set.of("cry"), List.of());
            return Unit.INSTANCE;
        });
        //TODO add sounds

        return Unit.INSTANCE;
    }

    public static boolean hasGimmick(ShowdownMoveset.Gimmick gimmick, ServerPlayer player) {
        if (gimmick == ShowdownMoveset.Gimmick.DYNAMAX) {
            if (!MegaShowdownConfig.dynamax) {
                return false;
            }

            boolean hasDMaxItemAccessory = AccessoriesUtils.checkTagInAccessories(player, ModTags.Items.DYNAMAX_BAND);

            return player.getOffhandItem().is(ModTags.Items.DYNAMAX_BAND)
                    || player.getMainHandItem().is(ModTags.Items.DYNAMAX_BAND)
                    || hasDMaxItemAccessory;
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
                AspectUtils.revertPokemonsIfRequired(pokemon);
            }

            ItemStack teraOrb = AccessoriesUtils.findFirstItemWithTag(player, ModTags.Items.TERA_ORB);

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

            boolean hasZPowerItemAccessory = AccessoriesUtils.checkTagInAccessories(player, ModTags.Items.Z_RING);

            return player.getOffhandItem().is(ModTags.Items.Z_RING)
                    || player.getMainHandItem().is(ModTags.Items.Z_RING)
                    || hasZPowerItemAccessory;
        } else if (gimmick == ShowdownMoveset.Gimmick.MEGA_EVOLUTION) {
            if (!MegaShowdownConfig.mega) {
                return false;
            }

            boolean hasKeystoneItemAccessory = AccessoriesUtils.checkTagInAccessories(player, ModTags.Items.MEGA_BRACELET);

            return (player.getOffhandItem().is(ModTags.Items.MEGA_BRACELET)
                    || player.getMainHandItem().is(ModTags.Items.MEGA_BRACELET)
                    || hasKeystoneItemAccessory)
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
                AspectUtils.revertPokemonsIfRequired(playerPartyStore);
            });

            return Unit.INSTANCE;
        }));

        return Unit.INSTANCE;
    }

    private static Unit megaEvolution(MegaEvolutionEvent event) {
        Pokemon pokemon = event.getPokemon().getEntity().getPokemon();
        if (pokemon.heldItem().getItem() instanceof MegaStone megaStone) {
            if (megaStone.getMegaProps().canMega(pokemon)) {
                MegaGimmick.megaEvolveInBattle(
                        pokemon,
                        event.getPokemon(),
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
