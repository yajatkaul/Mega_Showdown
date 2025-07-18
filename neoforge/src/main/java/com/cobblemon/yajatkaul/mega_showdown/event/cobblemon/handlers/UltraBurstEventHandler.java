package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.UltraBurstEventStart;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.EventUtils;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.UltraLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.List;
import java.util.Set;

public class UltraBurstEventHandler {
    @SubscribeEvent
    public void onUltra(UltraBurstEventStart event) {
        BattlePokemon pokemon = event.getPokemon();
        PokemonBattle battle = event.getBattle();

        Pokemon pokemon2 = pokemon.getEntity().getPokemon();
        if (pokemon2.getAspects().contains("dawn-fusion")) {
            pokemon2.getPersistentData().putString("fusion_form", "dawn");
        } else {
            pokemon2.getPersistentData().putString("fusion_form", "dusk");
        }

        new StringSpeciesFeature("prism_fusion", "ultra").apply(pokemon.getEffectedPokemon());
        UltraLogic.ultraAnimation(event.getPokemon().getEntity());

        EventUtils.updatePackets(battle, pokemon);

        event.getBattle().dispatchWaitingToFront(3F, () -> {
            SnowStormHandler.Companion.playAnimation(event.getPokemon().getEffectedPokemon().getEntity(), Set.of("cry"), List.of());
            return Unit.INSTANCE;
        });
    }
}
