package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.drop.ItemDropEntry;
import com.cobblemon.mod.common.api.events.battles.instruction.FormeChangeEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.MegaEvolutionEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.TerastallizationEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.ZMoveUsedEvent;
import com.cobblemon.mod.common.api.events.drops.LootDroppedEvent;
import com.cobblemon.mod.common.api.events.pokemon.*;
import com.cobblemon.mod.common.api.events.pokemon.healing.PokemonHealedEvent;
import com.cobblemon.mod.common.api.events.storage.ReleasePokemonEvent;
import com.cobblemon.mod.common.api.pokemon.feature.*;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import kotlin.Unit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.PlayerTeam;
import net.neoforged.neoforge.registries.DeferredItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.*;

import static com.cobblemon.yajatkaul.mega_showdown.utility.TeraTypeHelper.*;

public class CobbleEventsHandler {
    public static Unit onHeldItemChange(HeldItemEvent.Post event) {
        if(event.getReturned() == event.getReceived() || event.getPokemon().getOwnerPlayer() == null){
            return Unit.INSTANCE;
        }

        HeldItemChangeFormes.genesectChange(event);
        HeldItemChangeFormes.silvallyChange(event);
        HeldItemChangeFormes.arcuesChange(event);
        HeldItemChangeFormes.ultraEvent(event);
        HeldItemChangeFormes.primalEvent(event);
        HeldItemChangeFormes.crownedEvent(event);
        HeldItemChangeFormes.ogerponChange(event);
        HeldItemChangeFormes.eternamaxChange(event);
        HeldItemChangeFormes.originChange(event);

        if(Config.battleModeOnly){
            return Unit.INSTANCE;
        }

        HeldItemChangeFormes.megaEvent(event);

        return Unit.INSTANCE;
    }

    public static Unit onReleasePokemon(ReleasePokemonEvent.Post post) {
        if(post.getPlayer().level().isClientSide){
            return Unit.INSTANCE;
        }

        if(!post.getPlayer().level().isClientSide && post.getPlayer().getData(DataManage.MEGA_POKEMON) == post.getPokemon()){
            post.getPlayer().setData(DataManage.MEGA_DATA, false);
            post.getPlayer().removeData(DataManage.MEGA_POKEMON);
        }

        if(!post.getPlayer().level().isClientSide && post.getPlayer().getData(DataManage.PRIMAL_POKEMON) == post.getPokemon()){
            post.getPlayer().setData(DataManage.PRIMAL_DATA, false);
            post.getPlayer().removeData(DataManage.PRIMAL_POKEMON);
        }

        return Unit.INSTANCE;
    }

    public static Unit megaEvolution(MegaEvolutionEvent megaEvolutionEvent) {
        PokemonBattle battle = megaEvolutionEvent.getBattle();
        Pokemon pokemon = megaEvolutionEvent.getPokemon().getEffectedPokemon();

        ServerPlayer player = megaEvolutionEvent.getPokemon().getOriginalPokemon().getOwnerPlayer();

        if(player == null){
            return Unit.INSTANCE;
        }

        MegaLogic.Evolve(pokemon.getEntity(), player, true);

        battle.sendUpdate(new AbilityUpdatePacket(megaEvolutionEvent.getPokemon()::getEffectedPokemon, pokemon.getAbility().getTemplate()));
        battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon));

//        for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()){
//            if(activeBattlePokemon.getBattlePokemon() != null && activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == megaEvolutionEvent.getPokemon().getEffectedPokemon().getOwnerPlayer() && activeBattlePokemon.getBattlePokemon() == megaEvolutionEvent.getPokemon()){
//                battle.sendUpdate(new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), megaEvolutionEvent.getPokemon(), true));
//            }
//        }

        return Unit.INSTANCE;
    }

    public static Unit zMovesUsed(ZMoveUsedEvent zMoveUsedEvent) {
        LivingEntity pokemon = zMoveUsedEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = zMoveUsedEvent.getPokemon().getEffectedPokemon();

        pokemon.addEffect(new MobEffectInstance(MobEffects.GLOWING, 115, 0,false, false));

        if (pokemon.level() instanceof ServerLevel serverLevel) {
            AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "z_moves");
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_" + UUID.randomUUID().toString().substring(0, 8);

            PlayerTeam team = scoreboard.getPlayerTeam(teamName);
            if (team == null) {
                team = scoreboard.addPlayerTeam(teamName);
                team.setColor(getGlowColorForType(zMoveUsedEvent.getPokemon().getOriginalPokemon()));
                team.setSeeFriendlyInvisibles(false);
                team.setAllowFriendlyFire(true);
            }

            scoreboard.addPlayerToTeam(pokemon.getScoreboardName(), team);
        }

        return Unit.INSTANCE;
    }

    public static Unit terrastallizationUsed(TerastallizationEvent terastallizationEvent) {
        LivingEntity pokemon = terastallizationEvent.getPokemon().getEffectedPokemon().getEntity();
        Pokemon pk = terastallizationEvent.getPokemon().getEffectedPokemon();

        pokemon.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0,false, false));

        if (pokemon.level() instanceof ServerLevel serverLevel) {
            AdvancementHelper.grantAdvancement(pk.getOwnerPlayer(), "terastallized");
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

            if(pk.getSpecies().getName().equals("Ogerpon")){
                new FlagSpeciesFeature("embody_aspect", true).apply(pk);
            }
        }

        ItemStack teraOrb = CuriosApi.getCuriosInventory(terastallizationEvent.getPokemon().getEffectedPokemon().getOwnerPlayer()).get().findFirstCurio(TeraMoves.TERA_ORB.get()).get().stack();

        if (teraOrb != null) {
            // Reduce durability by a specific amount (e.g., 10 points)
            teraOrb.setDamageValue(teraOrb.getDamageValue() + 10);
        }

        return Unit.INSTANCE;
    }

    public static Unit healedPokemons(PokemonHealedEvent pokemonHealedEvent) {
        if(pokemonHealedEvent.getPokemon().getOwnerPlayer() == null){
            return Unit.INSTANCE;
        }

        ItemStack teraOrb = CuriosApi.getCuriosInventory(pokemonHealedEvent.getPokemon().getOwnerPlayer())
                .flatMap(curiosInventory -> curiosInventory.findFirstCurio(TeraMoves.TERA_ORB.get()))
                .map(SlotResult::stack)  // Safely extract the stack if present
                .orElse(null);

        if (teraOrb != null) {
            teraOrb.setDamageValue(0);
        }

        return Unit.INSTANCE;
    }

    public static Unit dropShardPokemon(LootDroppedEvent lootDroppedEvent) {
        if (!Config.teralization || !(lootDroppedEvent.getEntity() instanceof PokemonEntity)){
            return Unit.INSTANCE;
        }
        Pokemon pokemon = ((PokemonEntity) lootDroppedEvent.getEntity()).getPokemon();

        DeferredItem<Item> correspondingTeraShard = getTeraShardForType(pokemon.getTypes());

        ItemDropEntry teraShardDropEntry = new ItemDropEntry();
        teraShardDropEntry.setItem(BuiltInRegistries.ITEM.getKey(correspondingTeraShard.get()));

        int randomValue = new Random().nextInt(101);
        if(randomValue >= 10 && randomValue <= 20){
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
        } else if (randomValue == 33) {
            teraShardDropEntry.setItem(BuiltInRegistries.ITEM.getKey(TeraMoves.STELLAR_TERA_SHARD.get()));
            lootDroppedEvent.getDrops().add(teraShardDropEntry);
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
        } else if (pokemon.getSpecies().getName().equals("Minior")) {
            if(formeChangeEvent.getFormeName().equals("meteor")){
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("meteor_shield", "core").apply(pokemon);
            }else {
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("meteor_shield", "meteor").apply(pokemon);
            }
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
        }else if (pokemon.getSpecies().getName().equals("Greninja")) {
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
            if (formeChangeEvent.getFormeName().equals("hero")) {
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
        } else if (pokemon.getSpecies().getName().equals("Arceus")) {
            EventUtils.playFormeChangeAnimation(pokemon.getEntity());
            new StringSpeciesFeature("multitype", formeChangeEvent.getFormeName()).apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Xerneas")) {
            if(formeChangeEvent.getFormeName().equals("active")){
                EventUtils.playFormeChangeAnimation(pokemon.getEntity());
                new StringSpeciesFeature("life_mode", "active").apply(pokemon);
            }
        }

        battle.sendUpdate(new AbilityUpdatePacket(formeChangeEvent.getPokemon()::getEffectedPokemon, pokemon.getAbility().getTemplate()));
        battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon));

//        for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()){
//            if(activeBattlePokemon.getBattlePokemon() != null && activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == formeChangeEvent.getPokemon().getEffectedPokemon().getOwnerPlayer() && activeBattlePokemon.getBattlePokemon() == formeChangeEvent.getPokemon()){
//                battle.sendUpdate(new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), formeChangeEvent.getPokemon(), true));
//            }
//        }

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
