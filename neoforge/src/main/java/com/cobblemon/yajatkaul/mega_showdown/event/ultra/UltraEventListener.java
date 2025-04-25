package com.cobblemon.yajatkaul.mega_showdown.event.ultra;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.UltraLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.LazyLib;
import kotlin.Unit;
import net.neoforged.bus.api.SubscribeEvent;

public class UltraEventListener {
    @SubscribeEvent
    public void onUltra(UltraEvent event) {
        BattlePokemon pokemon = event.getPokemon();
        PokemonBattle battle = event.getBattle();

        new FlagSpeciesFeature("ultra", true).apply(pokemon.getEffectedPokemon());
        UltraLogic.ultraAnimation(event.getPokemon().getEntity());

        for (ActiveBattlePokemon activeBattlePokemon : event.getBattle().getActivePokemon()){
            if(activeBattlePokemon.getBattlePokemon() != null &&
                    activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pokemon.getEffectedPokemon().getOwnerPlayer()
                    && activeBattlePokemon.getBattlePokemon() == pokemon){
                event.getBattle().sendSidedUpdate(activeBattlePokemon.getActor(),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, true),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pokemon, false),
                        false);

            }
        }

        event.getBattle().dispatchWaitingToFront(3F, () -> {
            LazyLib.Companion.cryAnimation(event.getPokemon().getEffectedPokemon().getEntity());
            return Unit.INSTANCE;
        });

        battle.sendUpdate(new AbilityUpdatePacket(pokemon::getEffectedPokemon, pokemon.getEffectedPokemon().getAbility().getTemplate()));
        battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon.getEffectedPokemon()));
    }
}
