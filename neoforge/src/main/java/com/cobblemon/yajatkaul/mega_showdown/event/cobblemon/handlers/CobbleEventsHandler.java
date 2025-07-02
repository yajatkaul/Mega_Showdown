package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.battles.model.actor.ActorType;
import com.cobblemon.mod.common.api.drop.ItemDropEntry;
import com.cobblemon.mod.common.api.events.battles.instruction.FormeChangeEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.MegaEvolutionEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.TerastallizationEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.ZMoveUsedEvent;
import com.cobblemon.mod.common.api.events.drops.LootDroppedEvent;
import com.cobblemon.mod.common.api.events.pokeball.ThrownPokeballHitEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.events.pokemon.PokemonCapturedEvent;
import com.cobblemon.mod.common.api.events.pokemon.PokemonSentPostEvent;
import com.cobblemon.mod.common.api.events.pokemon.healing.PokemonHealedEvent;
import com.cobblemon.mod.common.api.events.storage.ReleasePokemonEvent;
import com.cobblemon.mod.common.api.item.HealingSource;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.FormChangeData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.handler.HandlerUtils;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.EventUtils;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.tera.TeraOrb;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.GlowHandler;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import com.cobblemon.yajatkaul.mega_showdown.utility.tera.TeraAccessor;
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Random;

import static com.cobblemon.yajatkaul.mega_showdown.utility.tera.TeraTypeHelper.getTeraShardForType;

public class CobbleEventsHandler {
    public static Unit onHeldItemChange(HeldItemEvent.Pre event) {
        if (event.getReceiving() == event.getReturning() || event.getPokemon().getOwnerPlayer() == null) {
            return Unit.INSTANCE;
        }

        PokemonEntity pokemonEntity = event.getPokemon().getEntity();
        if (pokemonEntity != null && pokemonEntity.getEntityData().get(PokemonEntity.getEVOLUTION_STARTED())) {
            event.cancel();
            return Unit.INSTANCE;
        }

        HeldItemChangeFormes.primalEvent(event);
        HeldItemChangeFormes.genesectChange(event);
        HeldItemChangeFormes.silvallyChange(event);
        HeldItemChangeFormes.arcuesChange(event);
        HeldItemChangeFormes.ultraEvent(event);
        HeldItemChangeFormes.crownedEvent(event);
        HeldItemChangeFormes.ogerponChange(event);
        HeldItemChangeFormes.eternamaxChange(event);
        HeldItemChangeFormes.originChange(event);
        HeldItemChangeFormes.customEvents(event);

        if (MegaShowdownConfig.battleModeOnly) {
            return Unit.INSTANCE;
        }

        HeldItemChangeFormes.megaEvent(event);

        return Unit.INSTANCE;
    }

    public static Unit onReleasePokemon(ReleasePokemonEvent.Post post) {
        Pokemon released = post.getPokemon();
        ServerPlayer player = post.getPlayer();

        if (released.getSpecies().getName().equals("Meltan")) {
            player.addItem(new ItemStack(FormeChangeItems.MELTAN.get()));
        }

        return Unit.INSTANCE;
    }

    public static Unit megaEvolution(MegaEvolutionEvent megaEvolutionEvent) {
        PokemonBattle battle = megaEvolutionEvent.getBattle();
        Pokemon pokemon = megaEvolutionEvent.getPokemon().getEffectedPokemon();

        battle.dispatchWaitingToFront(5.9F, () -> Unit.INSTANCE);

        if (pokemon.getOwnerPlayer() == null) {
            MegaLogic.NPCEvolve(pokemon.getEntity(), megaEvolutionEvent.getPokemon(), battle);
        } else {
            MegaLogic.Evolve(pokemon.getEntity(), pokemon.getOwnerPlayer(), megaEvolutionEvent.getPokemon(), battle);
        }

        return Unit.INSTANCE;
    }

    public static Unit zMovesUsed(ZMoveUsedEvent zMoveUsedEvent) {
        PokemonEntity pokemon = zMoveUsedEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = zMoveUsedEvent.getPokemon().getEffectedPokemon();

        AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "z/z_moves");

        if (pk.getSpecies().getName().equals("Pikachu") && pk.getAspects().contains("partner-cap")) {
            AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "bond/ash_pikachu");
        }

        if (pokemon != null) {
            GlowHandler.applyZGlow(pokemon);
        }

        SnowStormHandler.Companion.snowStormPartileSpawner(pk.getEntity(), "z_moves", "target");

        BlockPos entityPos = pokemon.getOnPos();
        pokemon.level().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                ModSounds.ZMOVE.get(),
                SoundSource.PLAYERS, 0.2f, 0.7f
        );

        zMoveUsedEvent.getBattle().dispatchWaitingToFront(4F, () -> Unit.INSTANCE);
        pk.getEntity().after(2.5f, () -> {
            SnowStormHandler.Companion.cryAnimation(pk.getEntity());
            return Unit.INSTANCE;
        });

        return Unit.INSTANCE;
    }

    public static Unit terrastallizationUsed(TerastallizationEvent terastallizationEvent) {
        PokemonEntity pokemon = terastallizationEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = terastallizationEvent.getPokemon().getEffectedPokemon();
        Vec3 entityPos = pokemon.position();

        AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "tera/terastallized");

        pokemon.level().playSound(
                null, entityPos.x, entityPos.y, entityPos.z,
                ModSounds.TERASTALLIZATION.get(),
                SoundSource.PLAYERS, 0.4f, 1f
        );

        if (pk.getSpecies().getName().equals("Terapagos")) {
            new StringSpeciesFeature("tera_form", "stellar").apply(pk);
            updatePackets(terastallizationEvent.getBattle(), terastallizationEvent.getPokemon(), false);
            EventUtils.playEvolveAnimation(pokemon);
        } else if (pk.getSpecies().getName().equals("Ogerpon")) {
            new FlagSpeciesFeature("embody-aspect", true).apply(pk);
            updatePackets(terastallizationEvent.getBattle(), terastallizationEvent.getPokemon(), false);
        }

        if (pk instanceof TeraAccessor accessor) {
            accessor.setTeraEnabled(true);
        }

        GlowHandler.applyTeraGlow(pokemon);

        Player player = terastallizationEvent.getPokemon().getEffectedPokemon().getOwnerPlayer();

        CuriosApi.getCuriosInventory(player)
                .flatMap(curiosInventory -> curiosInventory.findFirstCurio(
                        stack -> (stack.getItem() instanceof TeraOrb)
                ))
                .map(SlotResult::stack).ifPresent(teraOrb -> teraOrb.setDamageValue(teraOrb.getDamageValue() + 10));

        terastallizationEvent.getBattle().dispatchWaitingToFront(3F, () -> {
            SnowStormHandler.Companion.cryAnimation(pokemon);
            return Unit.INSTANCE;
        });

        return Unit.INSTANCE;
    }

    public static Unit healedPokemons(PokemonHealedEvent pokemonHealedEvent) {
        ServerPlayer player = pokemonHealedEvent.getPokemon().getOwnerPlayer();
        if (player == null || pokemonHealedEvent.getSource() != HealingSource.Force.INSTANCE) {
            return Unit.INSTANCE;
        }
        ItemStack teraOrb = CuriosApi.getCuriosInventory(pokemonHealedEvent.getPokemon().getOwnerPlayer())
                .flatMap(curiosInventory -> curiosInventory.findFirstCurio(
                        stack -> (stack.getItem() instanceof TeraOrb)
                ))
                .map(SlotResult::stack)
                .orElse(null);

        if (teraOrb != null) {
            teraOrb.setDamageValue(0);
        }

        return Unit.INSTANCE;
    }

    public static Unit dropShardPokemon(LootDroppedEvent lootDroppedEvent) {
        if (!MegaShowdownConfig.teralization || MegaShowdownConfig.disableTeraShardDrop || !(lootDroppedEvent.getEntity() instanceof PokemonEntity)) {
            return Unit.INSTANCE;
        }
        Pokemon pokemon = ((PokemonEntity) lootDroppedEvent.getEntity()).getPokemon();

        Item correspondingTeraShard = getTeraShardForType(pokemon.getTypes());

        ItemDropEntry teraShardDropEntry = new ItemDropEntry();
        teraShardDropEntry.setItem(BuiltInRegistries.ITEM.getKey(correspondingTeraShard));

        int randomValue = new Random().nextInt(101);
        if (randomValue >= 10 && randomValue <= 20) {
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
        } else if (randomValue == 33) {
            teraShardDropEntry.setItem(BuiltInRegistries.ITEM.getKey(TeraMoves.STELLAR_TERA_SHARD.get()));
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
        }

        return Unit.INSTANCE;
    }

    public static Unit formeChanges(FormeChangeEvent formeChangeEvent) {
        if (formeChangeEvent.getFormeName().equals("x") || formeChangeEvent.getFormeName().equals("y")
                || formeChangeEvent.getFormeName().equals("mega") || formeChangeEvent.getFormeName().equals("tera")) {
            return Unit.INSTANCE;
        }

        Pokemon pokemon = formeChangeEvent.getPokemon().getEffectedPokemon();
        PokemonBattle battle = formeChangeEvent.getBattle();
        PokemonEntity pokemonEntity = pokemon.getEntity();
        BlockPos entityPos = pokemonEntity.getOnPos();

        switch (pokemon.getSpecies().getName()) {
            case "Aegislash" -> {
                if (formeChangeEvent.getFormeName().equals("blade")) {
                    new StringSpeciesFeature("stance_forme", "blade").apply(pokemon);
                } else if (formeChangeEvent.getFormeName().equals("aegislash")) {
                    new StringSpeciesFeature("stance_forme", "shield").apply(pokemon);
                }
            }
            case "Minior" -> {
                if (formeChangeEvent.getFormeName().equals("meteor")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("meteor_shield", "meteor").apply(pokemon);
                } else {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("meteor_shield", "core").apply(pokemon);
                }
            }
            case "Castform" -> {
                switch (formeChangeEvent.getFormeName()) {
                    case "sunny" -> {
                        EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                        new StringSpeciesFeature("forecast_form", "sunny").apply(pokemon);
                    }
                    case "rainy" -> {
                        EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                        new StringSpeciesFeature("forecast_form", "rainy").apply(pokemon);
                    }
                    case "snowy" -> {
                        EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                        new StringSpeciesFeature("forecast_form", "snowy").apply(pokemon);
                    }
                }
            }
            case "Wishiwashi" -> {
                if (formeChangeEvent.getFormeName().equals("school")) {
                    battle.dispatchWaitingToFront(4.5F, () -> {
                        SnowStormHandler.Companion.snowStormPartileSpawner(pokemonEntity, "wishiwashi_effect", "target");
                        pokemonEntity.level().playSound(
                                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                                ModSounds.FORM_CHANGE_BASIC.get(),
                                SoundSource.PLAYERS, 0.2f, 1.3f
                        );

                        return Unit.INSTANCE;
                    });

                    pokemonEntity.after(5F, () -> {
                        new StringSpeciesFeature("schooling_form", "school").apply(pokemon);
                        SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                        updatePackets(formeChangeEvent.getBattle(), formeChangeEvent.getPokemon(), false);
                        return Unit.INSTANCE;
                    });
                } else if (formeChangeEvent.getFormeName().equals("wishiwashi")) {
                    new StringSpeciesFeature("schooling_form", "solo").apply(pokemon);
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                }
            }
            case "Mimikyu" -> {
                if (formeChangeEvent.getFormeName().equals("busted")) {
                    new StringSpeciesFeature("disguise_form", "busted").apply(pokemon);
                }
            }
            case "Greninja" -> {
                if (formeChangeEvent.getFormeName().equals("ash")) {
                    pokemonEntity.level().playSound(
                            null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                            ModSounds.FORM_CHANGE_BASIC.get(),
                            SoundSource.PLAYERS, 0.2f, 1.3f
                    );
                    battle.dispatchWaitingToFront(4.5F, () -> {
                        SnowStormHandler.Companion.snowStormPartileSpawner(pokemonEntity, "battlebond_effect", "root");
                        return Unit.INSTANCE;
                    });
                    pokemonEntity.after(4F, () -> {
                        new StringSpeciesFeature("battle_bond", "ash").apply(pokemon);
                        SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                        updatePackets(formeChangeEvent.getBattle(), formeChangeEvent.getPokemon(), false);
                        return Unit.INSTANCE;
                    });
                    AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "bond/ash_greninja");
                } else {
                    AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "bond/ash_battle_bond");
                }
            }
            case "Cherrim" -> {
                if (formeChangeEvent.getFormeName().equals("sunshine")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("blossom_form", "sunshine").apply(pokemon);
                } else {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("blossom_form", "overcast").apply(pokemon);
                }
            }
            case "Palafin" -> {
                if (formeChangeEvent.getFormeName().equals("hero")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("dolphin_form", "hero").apply(pokemon);
                }
            }
            case "Morpeko" -> {
                if (formeChangeEvent.getFormeName().equals("hangry")) {
                    EventUtils.playFormeChangeAngryAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("hunger_mode", "hangry").apply(pokemon);
                } else {
                    new StringSpeciesFeature("hunger_mode", "full_belly").apply(pokemon);
                }
            }
            case "Eiscue" -> {
                if (formeChangeEvent.getFormeName().equals("noice")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("penguin_head", "noice_face").apply(pokemon);
                } else {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("penguin_head", "ice_face").apply(pokemon);
                }
            }
            case "Cramorant" -> {
                switch (formeChangeEvent.getFormeName()) {
                    case "gulping" -> {
                        EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                        new StringSpeciesFeature("missile_form", "gulping").apply(pokemon);
                    }
                    case "cramorant" -> {
                        EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                        new StringSpeciesFeature("missile_form", "none").apply(pokemon);
                    }
                    case "gorging" -> {
                        EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                        new StringSpeciesFeature("missile_form", "gorging").apply(pokemon);
                    }
                }
            }
            case "Darmanitan" -> {
                if (formeChangeEvent.getFormeName().equals("zen")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("blazing_mode", "zen").apply(pokemon);
                } else {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("blazing_mode", "standard").apply(pokemon);
                }
            }
            case "Arceus" -> {
                battle.dispatchWaitingToFront(4.5F, () -> {
                    SnowStormHandler.Companion.snowStormPartileSpawner(pokemonEntity,
                            "arceus_" + formeChangeEvent.getFormeName(), "target");
                    pokemonEntity.level().playSound(
                            null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                            ModSounds.ARCEUS_MULTITYPE.get(),
                            SoundSource.PLAYERS, 0.2f, 1.3f
                    );
                    return Unit.INSTANCE;
                });
                pokemonEntity.after(4F, () -> {
                    new StringSpeciesFeature("multitype", formeChangeEvent.getFormeName()).apply(pokemon);
                    SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                    updatePackets(formeChangeEvent.getBattle(), formeChangeEvent.getPokemon(), false);
                    return Unit.INSTANCE;
                });
            }
            case "Xerneas" -> {
                if (formeChangeEvent.getFormeName().equals("active")) {
                    new StringSpeciesFeature("life_mode", "active").apply(pokemon);
                }
            }
            case "Terapagos" -> {
                if (formeChangeEvent.getFormeName().equals("terastal")) {
                    battle.dispatchWaitingToFront(4.5F, () -> {
                        SnowStormHandler.Companion.snowStormPartileSpawner(pokemonEntity, "terapagos_effect", "target");
                        pokemonEntity.level().playSound(
                                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                                ModSounds.FORM_CHANGE_BASIC.get(),
                                SoundSource.PLAYERS, 0.2f, 2.1f
                        );

                        return Unit.INSTANCE;
                    });

                    pokemonEntity.after(3.9F, () -> {
                        new StringSpeciesFeature("tera_form", "terastal").apply(pokemon);
                        SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                        updatePackets(formeChangeEvent.getBattle(), formeChangeEvent.getPokemon(), false);
                        return Unit.INSTANCE;
                    });
                }
            }
            case "Meloetta" -> {
                if (formeChangeEvent.getFormeName().equals("pirouette")) {
                    new StringSpeciesFeature("song_forme", "pirouette").apply(pokemon);
                    EventUtils.playEvolveAnimation(pokemon.getEntity());
                } else {
                    new StringSpeciesFeature("song_forme", "aria").apply(pokemon);
                }
            }
            case "Zygarde" -> {
                if (formeChangeEvent.getFormeName().equals("complete")) {
                    pokemonEntity.level().playSound(
                            null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                            ModSounds.POWER_CONSTRUCT.get(),
                            SoundSource.PLAYERS, 0.2f, 0.8f
                    );
                    battle.dispatchWaitingToFront(4.5F, () -> {
                        SnowStormHandler.Companion.snowStormPartileSpawner(pokemonEntity, "power_construct_event", "root");
                        return Unit.INSTANCE;
                    });
                    pokemonEntity.after(4F, () -> {
                        if (pokemon.getAspects().contains("10-percent")) {
                            pokemon.getPersistentData().putString("zygarde_form", "10");
                        } else {
                            pokemon.getPersistentData().putString("zygarde_form", "50");
                        }
                        new StringSpeciesFeature("percent_cells", "complete").apply(pokemon);
                        SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                        updatePackets(formeChangeEvent.getBattle(), formeChangeEvent.getPokemon(), false);
                        return Unit.INSTANCE;
                    });
                }
            }
            case "Shaymin" -> {
                if (formeChangeEvent.getFormeName().equals("shaymin")) {
                    new StringSpeciesFeature("gracidea_forme", "land").apply(pokemon);
                    EventUtils.playEvolveAnimation(pokemon.getEntity());
                }
            }
        }

        for (FormChangeData forme : Utils.formChangeRegistry) {
            if (forme.battle_mode_only()) {

                if (forme.pokemons().contains(formeChangeEvent.getPokemon().getEffectedPokemon().getSpecies().getName())
                        && formeChangeEvent.getFormeName().equals(forme.form_name())) {
                    for (String aspects : forme.aspects()) {
                        String[] aspectsDiv = aspects.split("=");
                        if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                            new FlagSpeciesFeature(aspectsDiv[0], Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                        } else {
                            new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                        }
                    }
                    HandlerUtils.particleEffect(pokemon.getEntity(), forme.effects(), true);
                    break;
                }
            }
        }

        updatePackets(battle, formeChangeEvent.getPokemon(), false);

        return Unit.INSTANCE;
    }

    public static Unit fixTera(PokemonCapturedEvent pokemonCapturedEvent) {
        Pokemon pokemon = pokemonCapturedEvent.getPokemon();

        if (pokemon.getSpecies().getName().equals("Ogerpon")) {
            pokemon.setTeraType(TeraTypes.getGRASS());
        } else if (pokemon.getSpecies().getName().equals("Terapagos")) {
            pokemon.setTeraType(TeraTypes.getSTELLAR());
        }

        return Unit.INSTANCE;
    }

    public static Unit pokemonSent(PokemonSentPostEvent pokemonSentPostEvent) {
        PokemonEntity pokemon = pokemonSentPostEvent.getPokemonEntity();
        Pokemon pk = pokemonSentPostEvent.getPokemon();

        if ((pk instanceof TeraAccessor accessor) && accessor.isTeraEnabled()) {
            GlowHandler.applyTeraGlow(pokemon);
        }

        return Unit.INSTANCE;
    }

    public static void updatePackets(PokemonBattle battle, BattlePokemon pk, boolean abilities) {
        Pokemon pokemon = pk.getEntity().getPokemon();

        if (abilities) {
            if (pk.actor.getType().equals(ActorType.PLAYER)) {
                battle.sendUpdate(new AbilityUpdatePacket(pk::getEffectedPokemon, pokemon.getAbility().getTemplate()));
                battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon));
            }
        }

        for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()) {
            if (!pk.actor.getType().equals(ActorType.PLAYER)) {
                continue;
            }
            if (activeBattlePokemon.getBattlePokemon() != null &&
                    activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pk.getEffectedPokemon().getOwnerPlayer()
                    && activeBattlePokemon.getBattlePokemon() == pk) {
                battle.sendSidedUpdate(activeBattlePokemon.getActor(),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pk, true),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pk, false),
                        false);
            }
        }
    }

    public static Unit pokeballHit(ThrownPokeballHitEvent thrownPokeballHitEvent) {
        if (thrownPokeballHitEvent.getPokemon().getAspects().contains("core-percent")) {
            thrownPokeballHitEvent.cancel();
        }

        return Unit.INSTANCE;
    }
}
