package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
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
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datapack.handler.HeldItemHandler;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.EventUtils;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.tera.TeraItem;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.GlowHandler;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import com.cobblemon.yajatkaul.mega_showdown.utility.tera.TeraAccessor;
import dev.emi.trinkets.api.TrinketsApi;
import kotlin.Unit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

import static com.cobblemon.yajatkaul.mega_showdown.utility.tera.TeraTypeHelper.getTeraShardForType;

public class CobbleEventsHandler {
    public static Unit onHeldItemChange(HeldItemEvent.Pre event) {
        if (event.getReceiving() == event.getReturning() || event.getPokemon().getOwnerPlayer() == null) {
            return Unit.INSTANCE;
        }

        PokemonEntity pokemonEntity = event.getPokemon().getEntity();
        if (pokemonEntity != null && pokemonEntity.getDataTracker().get(PokemonEntity.getEVOLUTION_STARTED())) {
            event.cancel();
            return Unit.INSTANCE;
        }

        HeldItemChangeHandler.genesectChange(event);
        HeldItemChangeHandler.silvallyChange(event);
        HeldItemChangeHandler.arcuesChange(event);
        HeldItemChangeHandler.ultraEvent(event);
        HeldItemChangeHandler.crownedEvent(event);
        HeldItemChangeHandler.ogerponChange(event);
        HeldItemChangeHandler.eternamaxChange(event);
        HeldItemChangeHandler.originChange(event);
        HeldItemHandler.customEvents(event);
        HeldItemChangeHandler.primalEvent(event);

        if (MegaShowdownConfig.battleModeOnly.get()) {
            return Unit.INSTANCE;
        }
        HeldItemChangeHandler.megaEvent(event);

        return Unit.INSTANCE;
    }

    public static Unit onReleasePokemon(ReleasePokemonEvent.Post post) {
        ServerPlayerEntity player = post.getPlayer();
        Pokemon released = post.getPokemon();

        if (released.getSpecies().getName().equals("Meltan")) {
            player.giveItemStack(new ItemStack(FormeChangeItems.MELTAN));
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
        zMoveUsedEvent.getBattle().dispatchWaitingToFront(4F, () -> Unit.INSTANCE);

        BlockPos entityPos = pokemon.getBlockPos();
        pokemon.getWorld().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                ModSounds.ZMOVE,
                SoundCategory.PLAYERS, 0.2f, 0.7f
        );


        pk.getEntity().after(2.5f, () -> {
            SnowStormHandler.Companion.cryAnimation(pk.getEntity());
            return Unit.INSTANCE;
        });

        return Unit.INSTANCE;
    }

    public static Unit terrastallizationUsed(TerastallizationEvent terastallizationEvent) {
        PokemonEntity pokemon = terastallizationEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = terastallizationEvent.getPokemon().getEffectedPokemon();

        AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "tera/terastallized");

        Vec3d entityPos = pokemon.getPos();

        pokemon.getWorld().playSound(
                null, entityPos.x, entityPos.y, entityPos.z,
                ModSounds.TERASTALLIZATION,
                SoundCategory.PLAYERS, 0.2f, 1f
        );

        if (pk.getSpecies().getName().equals("Terapagos")) {
            new StringSpeciesFeature("tera_form", "stellar").apply(pk);
            pk.updateAspects();
        } else if (pk.getSpecies().getName().equals("Ogerpon")) {
            new FlagSpeciesFeature("embody-aspect", true).apply(pk);
            pk.updateAspects();
        }

        if (pk instanceof TeraAccessor accessor) {
            accessor.setTeraEnabled(true);
        }

        GlowHandler.applyTeraGlow(pokemon);

        PlayerEntity player = terastallizationEvent.getPokemon().getEffectedPokemon().getOwnerPlayer();

        TrinketsApi.getTrinketComponent(player)
                .flatMap(component -> component.getAllEquipped().stream()
                        .map(Pair::getRight)
                        .filter(stack -> !stack.isEmpty() && (
                                stack.getItem() instanceof TeraItem
                        ))
                        .findFirst()
                ).ifPresent(teraOrb -> teraOrb.setDamage(teraOrb.getDamage() + 10));

        PokemonBattle battle = terastallizationEvent.getBattle();

        battle.dispatchWaitingToFront(3F, () -> {
            SnowStormHandler.Companion.cryAnimation(pokemon);
            return Unit.INSTANCE;
        });

        return Unit.INSTANCE;
    }

    public static Unit dropShardPokemon(LootDroppedEvent lootDroppedEvent) {
        if (!MegaShowdownConfig.teralization.get() || MegaShowdownConfig.disableTeraShardDrop.get() || !(lootDroppedEvent.getEntity() instanceof PokemonEntity)) {
            return Unit.INSTANCE;
        }
        Pokemon pokemon = ((PokemonEntity) lootDroppedEvent.getEntity()).getPokemon();

        Item correspondingTeraShard = getTeraShardForType(pokemon.getTypes());

        ItemDropEntry teraShardDropEntry = new ItemDropEntry();
        teraShardDropEntry.setItem(Registries.ITEM.getId(correspondingTeraShard));

        int randomValue = new Random().nextInt(101);
        if (randomValue >= 10 && randomValue <= 20) {
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
        } else if (randomValue == 33) {
            teraShardDropEntry.setItem(Registries.ITEM.getId(TeraMoves.STELLAR_TERA_SHARD));
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
        }

        return Unit.INSTANCE;
    }

    public static Unit healedPokemons(PokemonHealedEvent pokemonHealedEvent) {
        ServerPlayerEntity player = pokemonHealedEvent.getPokemon().getOwnerPlayer();
        if (player == null || pokemonHealedEvent.getSource() != HealingSource.Force.INSTANCE) {
            return Unit.INSTANCE;
        }

        ItemStack teraOrb = TrinketsApi.getTrinketComponent(player)
                .flatMap(component -> component.getAllEquipped().stream()
                        .map(Pair::getRight)
                        .filter(stack -> !stack.isEmpty() && (
                                stack.getItem() instanceof TeraItem
                        ))
                        .findFirst()
                ).orElse(null);

        if (teraOrb != null) {
            teraOrb.setDamage(0);
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
        BlockPos entityPos = pokemonEntity.getBlockPos();

        switch (pokemon.getSpecies().getName()) {
            case "Aegislash" -> {
                if (formeChangeEvent.getFormeName().equals("blade")) {
                    new StringSpeciesFeature("stance_forme", "blade").apply(pokemon);
                } else if (formeChangeEvent.getFormeName().equals("aegislash")) {
                    new StringSpeciesFeature("stance_forme", "shield").apply(pokemon);
                }
            }
            case "Arceus" -> {
                battle.dispatchWaitingToFront(4.5F, () -> {
                    SnowStormHandler.Companion.snowStormPartileSpawner(pokemonEntity,
                            "arceus_" + formeChangeEvent.getFormeName(), "target");
                    pokemonEntity.getWorld().playSound(
                            null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                            ModSounds.ARCEUS_MULTITYPE,
                            SoundCategory.PLAYERS, 0.2f, 1.3f
                    );
                    return Unit.INSTANCE;
                });
                pokemonEntity.after(4F, () -> {
                    new StringSpeciesFeature("multitype", formeChangeEvent.getFormeName()).apply(pokemon);
                    SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                    pokemon.updateAspects();
                    return Unit.INSTANCE;
                });
                return Unit.INSTANCE;
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
                        pokemonEntity.getWorld().playSound(
                                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                                ModSounds.FORM_CHANGE_BASIC,
                                SoundCategory.PLAYERS, 0.2f, 1.3f
                        );

                        return Unit.INSTANCE;
                    });

                    pokemonEntity.after(5F, () -> {
                        new StringSpeciesFeature("schooling_form", "school").apply(pokemon);
                        SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                        pokemon.updateAspects();
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
                    pokemonEntity.getWorld().playSound(
                            null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                            ModSounds.FORM_CHANGE_BASIC,
                            SoundCategory.PLAYERS, 0.2f, 1.3f
                    );

                    battle.dispatchWaitingToFront(4.5F, () -> {
                        SnowStormHandler.Companion.snowStormPartileSpawner(pokemonEntity, "battlebond_effect", "root");
                        return Unit.INSTANCE;
                    });
                    pokemonEntity.after(4F, () -> {
                        new StringSpeciesFeature("battle_bond", "ash").apply(pokemon);
                        SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                        pokemon.updateAspects();
                        return Unit.INSTANCE;
                    });

                    AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "bond/ash_greninja");
                    return Unit.INSTANCE;
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
            case "Xerneas" -> {
                if (formeChangeEvent.getFormeName().equals("active")) {
                    new StringSpeciesFeature("life_mode", "active").apply(pokemon);
                }
            }
            case "Terapagos" -> {
                if (formeChangeEvent.getFormeName().equals("terastal")) {
                    battle.dispatchWaitingToFront(4.5F, () -> {
                        SnowStormHandler.Companion.snowStormPartileSpawner(pokemonEntity, "terapagos_effect", "target");
                        pokemonEntity.getWorld().playSound(
                                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                                ModSounds.FORM_CHANGE_BASIC,
                                SoundCategory.PLAYERS, 0.2f, 2.1f
                        );

                        return Unit.INSTANCE;
                    });

                    pokemonEntity.after(3.9F, () -> {
                        new StringSpeciesFeature("tera_form", "terastal").apply(pokemon);
                        SnowStormHandler.Companion.cryAnimation(pokemon.getEntity());
                        pokemon.updateAspects();
                        return Unit.INSTANCE;
                    });
                    return Unit.INSTANCE;
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
                    pokemonEntity.getWorld().playSound(
                            null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                            ModSounds.POWER_CONSTRUCT,
                            SoundCategory.PLAYERS, 0.2f, 0.8f
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
                        pokemon.updateAspects();
                        return Unit.INSTANCE;
                    });

                    return Unit.INSTANCE;
                }
            }
            case "Shaymin" -> {
                if (formeChangeEvent.getFormeName().equals("shaymin")) {
                    new StringSpeciesFeature("gracidea_forme", "land").apply(pokemon);
                    EventUtils.playEvolveAnimation(pokemon.getEntity());
                }
            }
        }

        //DATAPACK
        HeldItemHandler.battleModeFormChange(formeChangeEvent);

        pokemon.updateAspects();

        return Unit.INSTANCE;
    }

    public static Unit fixTeraTyping(PokemonCapturedEvent pokemonCapturedEvent) {
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

        if (pk instanceof TeraAccessor accessor && accessor.isTeraEnabled()) {
            GlowHandler.applyTeraGlow(pokemon);
        }

        return Unit.INSTANCE;
    }

    public static Unit pokeballHit(ThrownPokeballHitEvent thrownPokeballHitEvent) {
        if (thrownPokeballHitEvent.getPokemon().getAspects().contains("core-percent")) {
            thrownPokeballHitEvent.cancel();
        }

        return Unit.INSTANCE;
    }
}
