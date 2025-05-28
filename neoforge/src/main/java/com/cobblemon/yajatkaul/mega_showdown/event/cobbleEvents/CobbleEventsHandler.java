package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.drop.ItemDropEntry;
import com.cobblemon.mod.common.api.events.battles.instruction.FormeChangeEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.MegaEvolutionEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.TerastallizationEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.ZMoveUsedEvent;
import com.cobblemon.mod.common.api.events.drops.LootDroppedEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.events.pokemon.PokemonCapturedEvent;
import com.cobblemon.mod.common.api.events.pokemon.PokemonSentPostEvent;
import com.cobblemon.mod.common.api.events.pokemon.healing.PokemonHealedEvent;
import com.cobblemon.mod.common.api.events.storage.ReleasePokemonEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.Config;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.FormChangeData;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.configActions.ConfigResults;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.TeraItem;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.LazyLib;
import com.cobblemon.yajatkaul.mega_showdown.utility.TeraAccessor;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import kotlin.Unit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.neoforged.neoforge.registries.DeferredItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Random;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.utility.TeraTypeHelper.*;

public class CobbleEventsHandler {
    public static Unit onHeldItemChange(HeldItemEvent.Post event) {
        if (event.getReturned() == event.getReceived() || event.getPokemon().getOwnerPlayer() == null) {
            return Unit.INSTANCE;
        }

        HeldItemChangeFormes.genesectChange(event);
        HeldItemChangeFormes.silvallyChange(event);
        HeldItemChangeFormes.arcuesChange(event);
        HeldItemChangeFormes.ultraEvent(event);
        HeldItemChangeFormes.crownedEvent(event);
        HeldItemChangeFormes.ogerponChange(event);
        HeldItemChangeFormes.eternamaxChange(event);
        HeldItemChangeFormes.originChange(event);
        HeldItemChangeFormes.customEvents(event);

        if (Config.battleModeOnly) {
            return Unit.INSTANCE;
        }

        HeldItemChangeFormes.megaEvent(event);

        return Unit.INSTANCE;
    }

    public static Unit onHeldItemChangePrimals(HeldItemEvent.Pre event) {
        if (event.getReceiving() == event.getReturning() || event.getPokemon().getOwnerPlayer() == null) {
            return Unit.INSTANCE;
        }

        PokemonEntity pokemonEntity = event.getPokemon().getEntity();
        if ( pokemonEntity != null &&  pokemonEntity.getEntityData().get(PokemonEntity.getEVOLUTION_STARTED())) {
            event.cancel();
            return Unit.INSTANCE;
        }

        HeldItemChangeFormes.primalEvent(event);

        return Unit.INSTANCE;
    }

    public static Unit onReleasePokemon(ReleasePokemonEvent.Post post) {
        Pokemon released = post.getPokemon();
        ServerPlayer player = post.getPlayer();

        if (player.getData(DataManage.MEGA_POKEMON).getPokemon() == released) {
            player.setData(DataManage.MEGA_DATA, false);
            player.removeData(DataManage.MEGA_POKEMON);
        }

        if (player.getData(DataManage.PRIMAL_POKEMON).getPokemon() == released) {
            player.setData(DataManage.PRIMAL_DATA, false);
            player.removeData(DataManage.PRIMAL_POKEMON);
        }

        return Unit.INSTANCE;
    }

    public static Unit megaEvolution(MegaEvolutionEvent megaEvolutionEvent) {
        PokemonBattle battle = megaEvolutionEvent.getBattle();
        Pokemon pokemon = megaEvolutionEvent.getPokemon().getEffectedPokemon();

        battle.dispatchWaitingToFront(5.9F, () -> Unit.INSTANCE);

        MegaLogic.Evolve(pokemon.getEntity(), pokemon.getOwnerPlayer(), megaEvolutionEvent.getPokemon(), battle);

        return Unit.INSTANCE;
    }

    public static Unit zMovesUsed(ZMoveUsedEvent zMoveUsedEvent) {
        LivingEntity pokemon = zMoveUsedEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = zMoveUsedEvent.getPokemon().getEffectedPokemon();

        AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "z/z_moves");

        if (pk.getSpecies().getName().equals("Pikachu") && pk.getAspects().contains("partner-cap")) {
            AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "bond/ash_pikachu");
        }

        pokemon.addEffect(new MobEffectInstance(MobEffects.GLOWING, 140, 0, false, false));

        if (pokemon.level() instanceof ServerLevel serverLevel) {
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);

            PlayerTeam team = scoreboard.getPlayerTeam(teamName);
            if (team == null) {
                team = scoreboard.addPlayerTeam(teamName);
                team.setColor(getGlowColorForType(pk.getHeldItem$common()));
                team.setSeeFriendlyInvisibles(false);
                team.setAllowFriendlyFire(true);
            }

            scoreboard.addPlayerToTeam(pokemon.getScoreboardName(), team);
        }

        LazyLib.Companion.snowStormPartileSpawner(pk.getEntity(), "z_moves", "target");

        BlockPos entityPos = pokemon.getOnPos();
        pokemon.level().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                ModSounds.ZMOVE.get(),
                SoundSource.PLAYERS, 0.2f, 0.7f
        );

        zMoveUsedEvent.getBattle().dispatchWaitingToFront(4F, () -> Unit.INSTANCE);
        pk.getEntity().after(2.5f, () -> {
            LazyLib.Companion.cryAnimation(pk.getEntity());
            return Unit.INSTANCE;
        });

        return Unit.INSTANCE;
    }

    public static Unit terrastallizationUsed(TerastallizationEvent terastallizationEvent) {
        LivingEntity pokemon = terastallizationEvent.getPokemon().getEffectedPokemon().getEntity();
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
            new FlagSpeciesFeature("embody_aspect", true).apply(pk);
            updatePackets(terastallizationEvent.getBattle(), terastallizationEvent.getPokemon(), false);
        }

        if (pk instanceof TeraAccessor accessor) {
            accessor.setTeraEnabled(true);
        }

        pokemon.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));

        if (pokemon.level() instanceof ServerLevel serverLevel) {
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);

            PlayerTeam team = scoreboard.getPlayerTeam(teamName);

            TeraType teraType = terastallizationEvent.getPokemon().getEffectedPokemon().getTeraType();

            ChatFormatting color = getGlowColorForTeraType(teraType);
            if (team == null) {
                team = scoreboard.addPlayerTeam(teamName);
                team.setColor(color);
                team.setSeeFriendlyInvisibles(false);
                team.setAllowFriendlyFire(true);
            }

            scoreboard.addPlayerToTeam(pokemon.getScoreboardName(), team);
        }

        Player player = terastallizationEvent.getPokemon().getEffectedPokemon().getOwnerPlayer();

        ItemStack teraOrb = CuriosApi.getCuriosInventory(player)
                .flatMap(curiosInventory -> curiosInventory.findFirstCurio(
                        stack -> (stack.getItem() instanceof TeraItem)
                ))
                .map(SlotResult::stack)
                .orElse(null);

        if (teraOrb != null) {
            // Reduce durability by a specific amount (e.g., 10 points)
            teraOrb.setDamageValue(teraOrb.getDamageValue() + 10);
        }

        terastallizationEvent.getBattle().dispatchWaitingToFront(3F, () -> {
            LazyLib.Companion.cryAnimation(pokemon);
            return Unit.INSTANCE;
        });

        return Unit.INSTANCE;
    }

    public static Unit healedPokemons(PokemonHealedEvent pokemonHealedEvent) {
        if (pokemonHealedEvent.getPokemon().getOwnerPlayer() == null) {
            return Unit.INSTANCE;
        }

        ItemStack teraOrb = CuriosApi.getCuriosInventory(pokemonHealedEvent.getPokemon().getOwnerPlayer())
                .flatMap(curiosInventory -> curiosInventory.findFirstCurio(
                        stack -> (stack.getItem() instanceof TeraItem)
                ))
                .map(SlotResult::stack)
                .orElse(null);

        if (teraOrb != null) {
            teraOrb.setDamageValue(0);
        }

        return Unit.INSTANCE;
    }

    public static Unit dropShardPokemon(LootDroppedEvent lootDroppedEvent) {
        if (!Config.teralization || Config.disableTeraShardDrop || !(lootDroppedEvent.getEntity() instanceof PokemonEntity)) {
            return Unit.INSTANCE;
        }
        Pokemon pokemon = ((PokemonEntity) lootDroppedEvent.getEntity()).getPokemon();

        DeferredItem<Item> correspondingTeraShard = getTeraShardForType(pokemon.getTypes());

        ItemDropEntry teraShardDropEntry = new ItemDropEntry();
        teraShardDropEntry.setItem(BuiltInRegistries.ITEM.getKey(correspondingTeraShard.get()));

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
                if (formeChangeEvent.getFormeName().equals("sunny")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("forecast_form", "sunny").apply(pokemon);
                } else if (formeChangeEvent.getFormeName().equals("rainy")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("forecast_form", "rainy").apply(pokemon);
                } else if (formeChangeEvent.getFormeName().equals("snowy")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("forecast_form", "snowy").apply(pokemon);
                }
            }
            case "Wishiwashi" -> {
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                if (formeChangeEvent.getFormeName().equals("school")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("schooling_form", "school").apply(pokemon);
                } else if (formeChangeEvent.getFormeName().equals("wishiwashi")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("schooling_form", "solo").apply(pokemon);
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
                            ModSounds.ASH_GRENINJA.get(),
                            SoundSource.PLAYERS, 0.2f, 1.3f
                    );
                    battle.dispatchWaitingToFront(4.5F, () -> {
                        LazyLib.Companion.snowStormPartileSpawner(pokemonEntity, "battlebond_effect", "root");
                        return Unit.INSTANCE;
                    });
                    pokemonEntity.after(4F, () -> {
                        new StringSpeciesFeature("battle_bond", "ash").apply(pokemon);
                        LazyLib.Companion.cryAnimation(pokemon.getEntity());
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
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", formeChangeEvent.getFormeName()).apply(pokemon);
            }
            case "Xerneas" -> {
                if (formeChangeEvent.getFormeName().equals("active")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("life_mode", "active").apply(pokemon);
                }
            }
            case "Terapagos" -> {
                if (formeChangeEvent.getFormeName().equals("terastal")) {
                    new StringSpeciesFeature("tera_form", "terastal").apply(pokemon);
                    EventUtils.playEvolveAnimation(pokemon.getEntity());
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
                        LazyLib.Companion.snowStormPartileSpawner(pokemonEntity, "power_construct_event", "root");
                        return Unit.INSTANCE;
                    });
                    pokemonEntity.after(4F, () -> {
                        new FlagSpeciesFeature("complete-percent", true).apply(pokemon);
                        LazyLib.Companion.cryAnimation(pokemon.getEntity());
                        updatePackets(formeChangeEvent.getBattle(), formeChangeEvent.getPokemon(), false);
                        return Unit.INSTANCE;
                    });
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
                    ConfigResults.particleEffect(pokemon.getEntity(), forme.effects(), true);
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
            pokemon.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));

            if (pokemon.level() instanceof ServerLevel serverLevel) {
                ServerScoreboard scoreboard = serverLevel.getScoreboard();
                String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);

                PlayerTeam team = scoreboard.getPlayerTeam(teamName);

                TeraType teraType = pk.getTeraType();

                ChatFormatting color = getGlowColorForTeraType(teraType);
                if (team == null) {
                    team = scoreboard.addPlayerTeam(teamName);
                    team.setColor(color);
                    team.setSeeFriendlyInvisibles(false);
                    team.setAllowFriendlyFire(true);
                }

                scoreboard.addPlayerToTeam(pokemon.getScoreboardName(), team);
            }
        }

        return Unit.INSTANCE;
    }

    public static void updatePackets(PokemonBattle battle, BattlePokemon pk, boolean abilities) {
        Pokemon pokemon = pk.getEntity().getPokemon();

        if (abilities) {
            battle.sendUpdate(new AbilityUpdatePacket(pk::getEffectedPokemon, pokemon.getAbility().getTemplate()));
            battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon));
        }

        for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()) {
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
}
