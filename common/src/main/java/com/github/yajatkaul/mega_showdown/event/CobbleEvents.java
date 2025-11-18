package com.github.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.drop.ItemDropEntry;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent;
import com.cobblemon.mod.common.api.events.battles.BattleStartedEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.FormeChangeEvent;
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
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.battles.dispatch.UntilDispatch;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.github.yajatkaul.mega_showdown.api.event.DynamaxEndCallback;
import com.github.yajatkaul.mega_showdown.api.event.DynamaxStartCallback;
import com.github.yajatkaul.mega_showdown.api.event.UltraBurstCallback;
import com.github.yajatkaul.mega_showdown.codec.BattleFormChange;
import com.github.yajatkaul.mega_showdown.codec.HeldItemFormChange;
import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.datapack.MegaShowdownDatapackRegister;
import com.github.yajatkaul.mega_showdown.gimmick.GimmickTurnCheck;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.gimmick.UltraGimmick;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import com.github.yajatkaul.mega_showdown.item.custom.form_change.FormChangeHeldItem;
import com.github.yajatkaul.mega_showdown.sound.MegaShowdownSounds;
import com.github.yajatkaul.mega_showdown.tag.ModTags;
import com.github.yajatkaul.mega_showdown.utils.*;
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
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
        CobblemonEvents.BATTLE_STARTED_POST.subscribe(Priority.NORMAL, CobbleEvents::hookBattleStarted);
        CobblemonEvents.TERASTALLIZATION.subscribe(Priority.NORMAL, CobbleEvents::terrastallizationUsed);
        CobblemonEvents.POKEMON_HEALED.subscribe(Priority.NORMAL, CobbleEvents::healedPokemons);
        CobblemonEvents.ZPOWER_USED.subscribe(Priority.NORMAL, CobbleEvents::zMovesUsed);
        CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, CobbleEvents::devolveFainted);
        CobblemonEvents.POKEMON_SENT_POST.subscribe(Priority.NORMAL, CobbleEvents::pokemonSent);
        CobblemonEvents.THROWN_POKEBALL_HIT.subscribe(Priority.NORMAL, CobbleEvents::pokeballHit);
        CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, CobbleEvents::fixTera);
        CobblemonEvents.LOOT_DROPPED.subscribe(Priority.NORMAL, CobbleEvents::dropShardPokemon);
        CobblemonEvents.FORME_CHANGE.subscribe(Priority.NORMAL, CobbleEvents::formChanged);

        DynamaxStartCallback.EVENT.register(CobbleEvents::dynamaxStarted);
        DynamaxEndCallback.EVENT.register(CobbleEvents::dynamaxEnded);
        UltraBurstCallback.EVENT.register(CobbleEvents::ultraBurst);
    }

    private static Unit formChanged(FormeChangeEvent formeChangeEvent) {
        if (formeChangeEvent.getFormeName().equals("x") || formeChangeEvent.getFormeName().equals("y")
                || formeChangeEvent.getFormeName().equals("mega") || formeChangeEvent.getFormeName().equals("tera")) {
            return Unit.INSTANCE;
        }
        BattlePokemon battlePokemon = formeChangeEvent.getPokemon();
        Pokemon pokemon = formeChangeEvent.getPokemon().getEffectedPokemon();

        for (BattleFormChange battleFormChange : MegaShowdownDatapackRegister.BATTLE_FORM_CHANGE_REGISTRY) {
            if (formeChangeEvent.getFormeName().equals(battleFormChange.showdownFormChangeId())
                    && battleFormChange.pokemons().contains(pokemon.getSpecies().getName())) {
                battleFormChange.effect().applyEffectsBattle(pokemon,
                        battleFormChange.aspects().apply_aspects(),
                        null,
                        battlePokemon
                );
                pokemon.getPersistentData().put("battle_end_revert", AspectUtils.makeNbt(battleFormChange.aspects().revert_aspects()));
                return Unit.INSTANCE;
            }
        }

        return Unit.INSTANCE;
    }

    private static Unit dropShardPokemon(LootDroppedEvent event) {
        if (!MegaShowdownConfig.teralization) {
            return Unit.INSTANCE;
        }

        if (event.getEntity() instanceof PokemonEntity pokemonEntity) {
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
        battle.dispatchWaitingToFront(MegaShowdownConfig.getDynamaxScaleDuration() / 20f, () -> Unit.INSTANCE);
        
        PokemonEntity pokemonEntity = pokemon.getEntity();

        if (pokemonEntity != null) {
            AspectUtils.scaleUpDynamax(pokemon.getEntity());

            //TODO make this a datapack thing
            BlockPos entityPos = pokemon.getEntity().getOnPos();
            pokemonEntity.level().playSound(
                    null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                    MegaShowdownSounds.TERASTALLIZATION.get(),
                    SoundSource.PLAYERS, 0.2f, 0.8f
            );
        }
    }

    private static Unit zMovesUsed(ZMoveUsedEvent event) {
        PokemonEntity pokemonEntity = event.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pokemon = pokemonEntity.getPokemon();

        AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "z/z_moves");

        if (pokemon.getSpecies().getName().equals("Pikachu") && pokemon.getAspects().contains("partner-cap")) {
            AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "bond/ash_pikachu");
        }

        GlowHandler.applyZGlow(pokemonEntity);

        ParticlesList.getEffect("mega_showdown:z_move").applyEffectsBattle(pokemon, List.of(), null, event.getPokemon());

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
            ParticlesList.getEffect("mega_showdown:terapagos_stellar").applyEffects(pokemon, List.of("tera_form=stellar"), null);
        } else if (pokemon.getSpecies().getName().equals("Ogerpon")) {
            ParticlesList.getEffect("mega_showdown:orgepon_embody").applyEffects(pokemon, List.of("embody-aspect=true"), null);
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

        //TODO make this a datapack thing

        BlockPos entityPos = pokemonEntity.getOnPos();
        pokemonEntity.level().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                MegaShowdownSounds.TERASTALLIZATION.get(),
                SoundSource.PLAYERS, 0.2f, 0.8f
        );

        return Unit.INSTANCE;
    }

    private static Unit hookBattleStarted(BattleStartedEvent.Post event) {
        event.getBattle().getPlayers().forEach(GimmickTurnCheck::check);

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
        MegaGimmick megaGimmick = pokemon.heldItem().get(MegaShowdownDataComponents.MEGA_STONE_COMPONENT.get());
        if (megaGimmick != null) {
            if (megaGimmick.canMega(pokemon)) {
                MegaGimmick.megaEvolveInBattle(
                        pokemon,
                        event.getPokemon(),
                        megaGimmick.aspect_conditions().apply_aspects(),
                        megaGimmick.aspect_conditions().revert_aspects()
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
        Pokemon pokemon = event.getPokemon();

        ItemStack itemReceiving = event.getReceiving();
        ItemStack itemReturning = event.getReturning();

        if (itemReturning.getItem() == event.getReceiving().getItem()) {
            return Unit.INSTANCE;
        }

        if (itemReturning.getItem() instanceof FormChangeHeldItem formChangeItem) {
            formChangeItem.revert(pokemon);
        }

        if (itemReceiving.getItem() instanceof FormChangeHeldItem formChangeItem) {
            formChangeItem.apply(pokemon);
        }

        HeldItemFormChange heldItemFormChangeReceiving = itemReceiving.get(MegaShowdownDataComponents.HELD_ITEM_FORM_CHANGE_COMPONENT.get());
        HeldItemFormChange heldItemFormChangeReturning = itemReturning.get(MegaShowdownDataComponents.HELD_ITEM_FORM_CHANGE_COMPONENT.get());

        if (heldItemFormChangeReturning != null) {
            heldItemFormChangeReturning.revert(pokemon);
        }
        if (heldItemFormChangeReceiving != null) {
            heldItemFormChangeReceiving.apply(pokemon);
        }

        MegaGimmick megaGimmick = event.getReturning().get(MegaShowdownDataComponents.MEGA_STONE_COMPONENT.get());
        if (megaGimmick != null
                && megaGimmick.pokemon().contains(pokemon.getSpecies().getName())
                && pokemon.getAspects().stream().anyMatch(MegaGimmick.getMegaAspects()::contains)) {
            MegaGimmick.megaToggle(pokemon.getEntity());
        }

        return Unit.INSTANCE;
    }
}
