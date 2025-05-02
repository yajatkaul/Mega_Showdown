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
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.TeraItem;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.LazyLib;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import com.cobblemon.yajatkaul.mega_showdown.utility.TeraAccessor;
import dev.emi.trinkets.api.TrinketsApi;
import kotlin.Unit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static com.cobblemon.yajatkaul.mega_showdown.utility.TeraTypeHelper.*;

public class CobbleEventHandler {
    public static Unit onHeldItemChange(HeldItemEvent.Post post) {
        if(post.getReturned() == post.getReceived() || post.getPokemon().getOwnerPlayer() == null){
            return Unit.INSTANCE;
        }

        HeldItemChangeFormes.genesectChange(post);
        HeldItemChangeFormes.silvallyChange(post);
        HeldItemChangeFormes.arcuesChange(post);
        HeldItemChangeFormes.ultraEvent(post);
        HeldItemChangeFormes.crownedEvent(post);
        HeldItemChangeFormes.ogerponChange(post);
        HeldItemChangeFormes.eternamaxChange(post);
        HeldItemChangeFormes.originChange(post);

        if(ShowdownConfig.battleModeOnly.get()){
            return Unit.INSTANCE;
        }

        HeldItemChangeFormes.megaEvent(post);

        return Unit.INSTANCE;
    }

    public static Unit onHeldItemChangePrimals(HeldItemEvent.Pre event) {
        if(event.getReceiving() == event.getReturning() || event.getPokemon().getOwnerPlayer() == null){
            return Unit.INSTANCE;
        }

        HeldItemChangeFormes.primalEvent(event);

        return Unit.INSTANCE;
    }

    public static Unit onReleasePokemon(ReleasePokemonEvent.Post post) {
        if(post.getPlayer().getAttached(DataManage.MEGA_POKEMON).getPokemon() == post.getPokemon()){
            post.getPlayer().removeAttached(DataManage.MEGA_DATA);
            post.getPlayer().removeAttached(DataManage.MEGA_POKEMON);
        }

        if(post.getPlayer().getAttached(DataManage.PRIMAL_POKEMON).getPokemon() == post.getPokemon()){
            post.getPlayer().removeAttached(DataManage.PRIMAL_DATA);
            post.getPlayer().removeAttached(DataManage.PRIMAL_POKEMON);
        }

        return Unit.INSTANCE;
    }

    public static Unit megaEvolution(MegaEvolutionEvent megaEvolutionEvent) {
        PokemonBattle battle = megaEvolutionEvent.getBattle();
        Pokemon pokemon = megaEvolutionEvent.getPokemon().getEffectedPokemon();

        ServerPlayerEntity player = megaEvolutionEvent.getPokemon().getOriginalPokemon().getOwnerPlayer();

        if(player == null){
            return Unit.INSTANCE;
        }

        battle.dispatchWaitingToFront(3F, () -> {
            LazyLib.Companion.cryAnimation(pokemon.getEntity());
            return Unit.INSTANCE;
        });

        MegaLogic.Evolve(pokemon.getEntity(), player, true);

        updatePackets(battle, megaEvolutionEvent.getPokemon(), true);

        return Unit.INSTANCE;
    }

    public static void updatePackets(PokemonBattle battle, BattlePokemon pk, boolean abilities){
        Pokemon pokemon = pk.getEntity().getPokemon();

        if(abilities){
            battle.sendUpdate(new AbilityUpdatePacket(pk::getEffectedPokemon, pokemon.getAbility().getTemplate()));
            battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon));
        }

        for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()){
            if(activeBattlePokemon.getBattlePokemon() != null &&
                    activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pk.getEffectedPokemon().getOwnerPlayer()
                    && activeBattlePokemon.getBattlePokemon() == pk){
                battle.sendSidedUpdate(activeBattlePokemon.getActor(),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pk, true),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pk, false),
                        false);
            }
        }
    }

    public static Unit zMovesUsed(ZMoveUsedEvent zMoveUsedEvent) {
        LivingEntity pokemon = zMoveUsedEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = zMoveUsedEvent.getPokemon().getEffectedPokemon();

        pokemon.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 115, 0,false, false));

        if (pokemon.getWorld() instanceof ServerWorld serverLevel) {
            AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "z_moves");
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);

            Team team = scoreboard.getTeam(teamName);
            if (team == null) {
                team = scoreboard.addTeam(teamName);
                team.setColor(getGlowColorForType(zMoveUsedEvent.getPokemon().getOriginalPokemon()));
            }

            scoreboard.addScoreHolderToTeam(pokemon.getUuid().toString(), team);
        }

        zMoveUsedEvent.getBattle().dispatchWaitingToFront(3F, () -> {
            LazyLib.Companion.cryAnimation(pokemon);
            return Unit.INSTANCE;
        });

        return Unit.INSTANCE;
    }

    public static Unit terrastallizationUsed(TerastallizationEvent terastallizationEvent) {
        LivingEntity pokemon = terastallizationEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = terastallizationEvent.getPokemon().getEffectedPokemon();

        Vec3d entityPos = pokemon.getPos();

        pokemon.getWorld().playSound(
                null, entityPos.x, entityPos.y, entityPos.z,
                ModSounds.TERASTALLIZATION,
                SoundCategory.PLAYERS, 0.4f, 1f
        );

        if(pk.getSpecies().getName().equals("Terapagos")){
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

        pokemon.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE, 0,false, false));

        if (pokemon.getWorld() instanceof ServerWorld serverLevel) {
            AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "terastallized");
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);

            Team team = scoreboard.getTeam(teamName);

            Formatting color = getGlowColorForTeraType(terastallizationEvent.getPokemon().getEffectedPokemon().getTeraType());
            if (team == null) {
                team = scoreboard.addTeam(teamName);
                team.setColor(color);
            }

            scoreboard.addScoreHolderToTeam(pokemon.getUuid().toString(), team);
        }

        PlayerEntity player = terastallizationEvent.getPokemon().getEffectedPokemon().getOwnerPlayer();

        ItemStack teraOrb = TrinketsApi.getTrinketComponent(player)
                .flatMap(component -> component.getAllEquipped().stream()
                        .map(Pair::getRight)
                        .filter(stack -> !stack.isEmpty() && (
                                stack.getItem() instanceof TeraItem
                        ))
                        .findFirst()
                ).orElse(null);

        if (teraOrb != null) {
            // Reduce durability by a specific amount (e.g., 10 points)
            teraOrb.setDamage(teraOrb.getDamage() + 10);
        }

        PokemonBattle battle = terastallizationEvent.getBattle();

        battle.dispatchWaitingToFront(3F, () -> {
            LazyLib.Companion.cryAnimation(pokemon);
            return Unit.INSTANCE;
        });

        return Unit.INSTANCE;
    }

    public static Unit dropShardPokemon(LootDroppedEvent lootDroppedEvent) {
        if (!ShowdownConfig.teralization.get() || ShowdownConfig.disableTeraShardDrop.get() || !(lootDroppedEvent.getEntity() instanceof PokemonEntity)){
            return Unit.INSTANCE;
        }
        Pokemon pokemon = ((PokemonEntity) lootDroppedEvent.getEntity()).getPokemon();

        Item correspondingTeraShard = getTeraShardForType(pokemon.getTypes());

        ItemDropEntry teraShardDropEntry = new ItemDropEntry();
        teraShardDropEntry.setItem(Registries.ITEM.getId(correspondingTeraShard));

        int randomValue = new Random().nextInt(101);
        if(randomValue >= 10 && randomValue <= 20){
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
        } else if (randomValue == 33) {
            teraShardDropEntry.setItem(Registries.ITEM.getId(TeraMoves.STELLAR_TERA_SHARD));
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
        }

        return Unit.INSTANCE;
    }

    public static Unit healedPokemons(PokemonHealedEvent pokemonHealedEvent) {
        if(pokemonHealedEvent.getPokemon().getOwnerPlayer() == null){
            return Unit.INSTANCE;
        }

        PlayerEntity player = pokemonHealedEvent.getPokemon().getOwnerPlayer();

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
        if(formeChangeEvent.getFormeName().equals("x") || formeChangeEvent.getFormeName().equals("y")
                || formeChangeEvent.getFormeName().equals("mega") || formeChangeEvent.getFormeName().equals("tera")){
            return Unit.INSTANCE;
        }

        Pokemon pokemon = formeChangeEvent.getPokemon().getEffectedPokemon();
        PokemonBattle battle = formeChangeEvent.getBattle();

        switch (pokemon.getSpecies().getName()) {
            case "Aegislash" -> {
                if (formeChangeEvent.getFormeName().equals("blade")) {
                    new StringSpeciesFeature("stance_forme", "blade").apply(pokemon);
                } else if (formeChangeEvent.getFormeName().equals("aegislash")) {
                    new StringSpeciesFeature("stance_forme", "shield").apply(pokemon);
                }
            }
            case "Arceus" -> {
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", formeChangeEvent.getFormeName()).apply(pokemon);
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
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("battle_bond", "ash").apply(pokemon);
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
            case "Xerneas" -> {
                if (formeChangeEvent.getFormeName().equals("active")) {
                    EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                    new StringSpeciesFeature("life_mode", "active").apply(pokemon);
                }
            }
            case "Terapagos" -> {
                if(formeChangeEvent.getFormeName().equals("terastal")){
                    new StringSpeciesFeature("tera_form", "terastal").apply(pokemon);
                    EventUtils.playEvolveAnimation(pokemon.getEntity());
                }
            }
            case "Meloetta" -> {
                if(formeChangeEvent.getFormeName().equals("pirouette")){
                    new StringSpeciesFeature("song_forme", "pirouette").apply(pokemon);
                    EventUtils.playEvolveAnimation(pokemon.getEntity());
                }else {
                    new StringSpeciesFeature("song_forme", "aria").apply(pokemon);
                }
            }
            case "Zygarde" -> {
                if(formeChangeEvent.getFormeName().equals("complete")){
                    new FlagSpeciesFeature("complete-percent", true).apply(pokemon);
                    playZygardeTransformation(pokemon.getEntity());
                }
            }
        }

        updatePackets(formeChangeEvent.getBattle(), formeChangeEvent.getPokemon(), false);

        battle.dispatchWaitingToFront(3F, () -> {
            LazyLib.Companion.cryAnimation(pokemon.getEntity());
            return Unit.INSTANCE;
        });

        return Unit.INSTANCE;
    }

    public static void playZygardeTransformation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth() + 1;
            double entityHeight = context.getHeight() + 4;

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverWorld.spawnParticles(
                        ParticleTypes.END_ROD, // Same particle type
                        entityPos.x + xOffset,
                        entityPos.y + yOffset,
                        entityPos.z + zOffset,
                        1, // One particle per call for better spread
                        0, 0, 0, // No movement velocity
                        0.1 // Slight motion
                );
            }
        }
    }

    public static Unit fixTeraTyping(PokemonCapturedEvent pokemonCapturedEvent) {
        Pokemon pokemon = pokemonCapturedEvent.getPokemon();

        if(pokemon.getSpecies().getName().equals("Ogerpon")){
            pokemon.setTeraType(TeraTypes.getGRASS());
        } else if (pokemon.getSpecies().getName().equals("Terapagos")) {
            pokemon.setTeraType(TeraTypes.getSTELLAR());
        }

        return Unit.INSTANCE;
    }

    public static Unit pokemonSent(PokemonSentPostEvent pokemonSentPostEvent) {
        PokemonEntity pokemon = pokemonSentPostEvent.getPokemonEntity();
        Pokemon pk = pokemonSentPostEvent.getPokemon();

        if(pk instanceof TeraAccessor accessor && accessor.isTeraEnabled()){
            if (pokemon.getWorld() instanceof ServerWorld serverLevel) {
                pokemon.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE, 0,false, false));
                ServerScoreboard scoreboard = serverLevel.getScoreboard();
                String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);

                Team team = scoreboard.getTeam(teamName);

                Formatting color = getGlowColorForTeraType(pk.getTeraType());
                if (team == null) {
                    team = scoreboard.addTeam(teamName);
                    team.setColor(color);
                }

                scoreboard.addScoreHolderToTeam(pokemon.getUuid().toString(), team);
            }
        }

        return Unit.INSTANCE;
    }
}
