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
import com.cobblemon.mod.common.api.events.pokemon.healing.PokemonHealedEvent;
import com.cobblemon.mod.common.api.events.storage.ReleasePokemonEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.client.gui.battle.BattleGUI;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleGimmickButton;
import com.cobblemon.mod.common.client.gui.battle.subscreen.DynamaxButton;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import dev.emi.trinkets.api.TrinketsApi;
import kotlin.Unit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;

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
        HeldItemChangeFormes.primalEvent(post);
        HeldItemChangeFormes.crownedEvent(post);
        HeldItemChangeFormes.ogerponChange(post);
        HeldItemChangeFormes.eternamaxChange(post);
        HeldItemChangeFormes.originChange(post);

        // Battle mode only
        if(ShowdownConfig.battleModeOnly.get()){
            return Unit.INSTANCE;
        }

        HeldItemChangeFormes.megaEvent(post);

        return Unit.INSTANCE;
    }

    public static Unit onReleasePokemon(ReleasePokemonEvent.Post post) {
        if(post.getPlayer().getWorld().isClient){
            return Unit.INSTANCE;
        }

        if(!post.getPlayer().hasAttached(DataManage.PRIMAL_POKEMON)){
            post.getPlayer().setAttached(DataManage.PRIMAL_POKEMON, new Pokemon());
        }

        if(!post.getPlayer().hasAttached(DataManage.MEGA_POKEMON)){
            post.getPlayer().setAttached(DataManage.MEGA_POKEMON, new Pokemon());
        }

        if(post.getPlayer().getAttached(DataManage.MEGA_POKEMON) == post.getPokemon()){
            post.getPlayer().removeAttached(DataManage.MEGA_DATA);
            post.getPlayer().removeAttached(DataManage.MEGA_POKEMON);
        }

        if(post.getPlayer().getAttached(DataManage.PRIMAL_POKEMON) == post.getPokemon()){
            post.getPlayer().setAttached(DataManage.PRIMAL_DATA, false);
            post.getPlayer().setAttached(DataManage.PRIMAL_POKEMON, new Pokemon());
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

        MegaLogic.Evolve(pokemon.getEntity(), player, true);

        battle.sendUpdate(new AbilityUpdatePacket(megaEvolutionEvent.getPokemon()::getEffectedPokemon, pokemon.getAbility().getTemplate()));
        battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon));

        for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()){
            if(activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == player){
                battle.sendUpdate(new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), megaEvolutionEvent.getPokemon(), true));
            }
        }

        return Unit.INSTANCE;
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
        return Unit.INSTANCE;
    }

    public static Unit terrastallizationUsed(TerastallizationEvent terastallizationEvent) {
        LivingEntity pokemon = terastallizationEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = terastallizationEvent.getPokemon().getEffectedPokemon();

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

            if(pk.getSpecies().getName().equals("Ogerpon")){
                new FlagSpeciesFeature("embody_aspect", true).apply(pk);
            }

            scoreboard.addScoreHolderToTeam(pokemon.getUuid().toString(), team);
        }

        PlayerEntity player = terastallizationEvent.getPokemon().getEffectedPokemon().getOwnerPlayer();
        AtomicReference<ItemStack> teraOrb = new AtomicReference<>(ItemStack.EMPTY); // Default to empty stack if not found

        TrinketsApi.getTrinketComponent(player).ifPresent(trinketComponent -> {
            trinketComponent.getEquipped(TeraMoves.TERA_ORB).forEach(pair -> {
                ItemStack stack = pair.getRight(); // The ItemStack of the equipped trinket
                if (!stack.isEmpty()) {
                    teraOrb.set(stack); // Assign the found stack
                }
            });
        });

        if (teraOrb.get() != null) {
            // Reduce durability by a specific amount (e.g., 10 points)
            teraOrb.get().setDamage(teraOrb.get().getDamage() + 10);
        }

        return Unit.INSTANCE;
    }

    public static Unit dropShardPokemon(LootDroppedEvent lootDroppedEvent) {
        if (!ShowdownConfig.teralization.get() || !(lootDroppedEvent.getEntity() instanceof PokemonEntity)){
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
        AtomicReference<ItemStack> teraOrb = new AtomicReference<>(ItemStack.EMPTY); // Default to empty stack if not found

        TrinketsApi.getTrinketComponent(player).ifPresent(trinketComponent -> {
            trinketComponent.getEquipped(TeraMoves.TERA_ORB).forEach(pair -> {
                ItemStack stack = pair.getRight(); // The ItemStack of the equipped trinket
                if (!stack.isEmpty()) {
                    teraOrb.set(stack); // Assign the found stack
                }
            });
        });

        if (teraOrb != null) {
            teraOrb.get().setDamage(0);
        }

        return Unit.INSTANCE;
    }

    public static Unit formeChanges(FormeChangeEvent formeChangeEvent) {
        Pokemon pokemon = formeChangeEvent.getPokemon().getEffectedPokemon();
        PokemonBattle battle = formeChangeEvent.getBattle();

        if(pokemon.getSpecies().getName().equals("Aegislash")){
            if(formeChangeEvent.getFormeName().equals("blade")){
                new StringSpeciesFeature("stance_forme", "blade").apply(pokemon);
            } else if (formeChangeEvent.getFormeName().equals("aegislash")) {
                new StringSpeciesFeature("stance_forme", "shield").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Arceus")) {
            EventUtils.playFormeChangeAnimation(pokemon.getEntity());
            new StringSpeciesFeature("multitype", formeChangeEvent.getFormeName()).apply(pokemon);
        }else if (pokemon.getSpecies().getName().equals("Minior") && formeChangeEvent.getFormeName().equals("meteor")) {
            EventUtils.playFormeChangeAnimation(pokemon.getEntity());
            new StringSpeciesFeature("meteor_shield", "core").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Castform")) {
            if(formeChangeEvent.getFormeName().equals("sunny")){
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("forecast_form", "sunny").apply(pokemon);
            } else if (formeChangeEvent.getFormeName().equals("rainy")) {
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("forecast_form", "rainy").apply(pokemon);
            } else if (formeChangeEvent.getFormeName().equals("snowy")){
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("forecast_form", "snowy").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Wishiwashi")) {
            EventUtils.playFormeChangeAnimation(pokemon.getEntity());
            if(formeChangeEvent.getFormeName().equals("school")){
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("schooling_form", "school").apply(pokemon);
            } else if (formeChangeEvent.getFormeName().equals("wishiwashi")) {
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("schooling_form", "solo").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Mimikyu")) {
            if(formeChangeEvent.getFormeName().equals("busted")){
                new StringSpeciesFeature("disguise_form", "busted").apply(pokemon);
            }
        }
        else if (pokemon.getSpecies().getName().equals("Greninja")) {
            if(formeChangeEvent.getFormeName().equals("ash")){
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("battle_bond", "ash").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Cherrim")) {
            if(formeChangeEvent.getFormeName().equals("sunshine")){
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("blossom_form", "sunshine").apply(pokemon);
            }else{
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("blossom_form", "overcast").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Palafin")) {
            if(formeChangeEvent.getFormeName().equals("hero")){
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("dolphin_form", "hero").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Morpeko")) {
            if(formeChangeEvent.getFormeName().equals("hangry")){
                EventUtils.playFormeChangeAngryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("hunger_mode", "hangry").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Eiscue")) {
            if(formeChangeEvent.getFormeName().equals("noice")){
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("penguin_head", "noice_face").apply(pokemon);
            }else {
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("penguin_head", "ice_face").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Cramorant")) {
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
        }else if (pokemon.getSpecies().getName().equals("Darmanitan")) {
            if(formeChangeEvent.getFormeName().equals("zen")){
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("blazing_mode", "zen").apply(pokemon);
            }else{
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("blazing_mode", "standard").apply(pokemon);
            }
        }

        battle.sendUpdate(new AbilityUpdatePacket(formeChangeEvent.getPokemon()::getEffectedPokemon, pokemon.getAbility().getTemplate()));
        battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon));

        for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()){
            if(activeBattlePokemon.getBattlePokemon() != null && activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == formeChangeEvent.getPokemon().getEffectedPokemon().getOwnerPlayer()){
                battle.sendUpdate(new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), formeChangeEvent.getPokemon(), true));
            }
        }

        return Unit.INSTANCE;
    }

    public static Unit fixOgerTera(PokemonCapturedEvent pokemonCapturedEvent) {
        Pokemon pokemon = pokemonCapturedEvent.getPokemon();

        if(pokemon.getSpecies().getName().equals("Ogerpon")){
            pokemon.setTeraType(TeraTypes.getGRASS());
        }

        return Unit.INSTANCE;
    }
}
