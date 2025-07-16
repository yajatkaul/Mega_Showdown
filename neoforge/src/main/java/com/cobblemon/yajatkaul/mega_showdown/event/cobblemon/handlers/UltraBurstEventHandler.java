package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.UltraBurstEventStart;
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

        new StringSpeciesFeature("prism_fusion", "ultra").apply(pokemon.getEffectedPokemon());
        UltraLogic.ultraAnimation(event.getPokemon().getEntity());

        for (ActiveBattlePokemon activeBattlePokemon : event.getBattle().getActivePokemon()) {
            if (activeBattlePokemon.getBattlePokemon() != null &&
                    activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pokemon.getEffectedPokemon().getOwnerPlayer()
                    && activeBattlePokemon.getBattlePokemon() == pokemon) {
                event.getBattle().sendSidedUpdate(activeBattlePokemon.getActor(),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, true),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, false),
                        false);

            }
        }

        event.getBattle().dispatchWaitingToFront(3F, () -> {
            SnowStormHandler.Companion.playAnimation(event.getPokemon().getEffectedPokemon().getEntity(), Set.of("cry"), List.of());
            return Unit.INSTANCE;
        });

        battle.sendUpdate(new AbilityUpdatePacket(pokemon::getEffectedPokemon, pokemon.getEffectedPokemon().getAbility().getTemplate()));
        battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon.getEffectedPokemon()));
    }
}
