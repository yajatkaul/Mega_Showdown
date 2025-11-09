package com.github.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.battles.BattleStartedEvent;
import com.cobblemon.mod.common.api.events.battles.instruction.MegaEvolutionEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.item.custom.FormChangeItem;
import com.github.yajatkaul.mega_showdown.item.custom.mega.MegaStone;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import kotlin.Unit;
import net.minecraft.world.effect.MobEffects;

import java.util.List;

@SuppressWarnings("SameReturnValue")
public class CobbleEvents {
    public static void register() {
        CobblemonEvents.HELD_ITEM_PRE.subscribe(Priority.NORMAL, CobbleEvents::heldItemChange);
        CobblemonEvents.MEGA_EVOLUTION.subscribe(Priority.NORMAL, CobbleEvents::megaEvolution);
        CobblemonEvents.BATTLE_STARTED_POST.subscribe(Priority.NORMAL, CobbleEvents::hookBattleEnded);
    }

    private static Unit hookBattleEnded(BattleStartedEvent.Post event) {
        event.getBattle().getOnEndHandlers().add((battle -> {
            battle.getPlayers().forEach(serverPlayer -> {
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
                for (Pokemon pokemon : playerPartyStore) {
                    if (pokemon.getPersistentData().contains("battle_end_revert")) {
                        AspectUtils.applyAspects(
                                pokemon,
                                AspectUtils.decodeNbt(pokemon.getPersistentData().getList("battle_end_revert", 8))
                        );
                    }

                    if (pokemon.getPersistentData().contains("apply_aspects")) {
                        List<String> aspects =
                                AspectUtils.decodeNbt(pokemon.getPersistentData().getList("apply_aspects", 8));

                        AspectUtils.applyAspects(pokemon, aspects);
                    }

                    if (pokemon.getEntity() != null) {
                        pokemon.getEntity().removeEffect(MobEffects.GLOWING);
                    }
                }
            });

            return Unit.INSTANCE;
        }));

        return Unit.INSTANCE;
    }

    private static Unit megaEvolution(MegaEvolutionEvent event) {
        Pokemon pokemon = event.getPokemon().getEntity().getPokemon();
        if (pokemon.heldItem().getItem() instanceof MegaStone megaStone) {
            if (megaStone.getMegaProps().canMega(pokemon)) {
                MegaGimmick.megaEvolveInBattle(
                        pokemon,
                        event.getBattle(),
                        megaStone.getMegaProps().aspect_conditions().apply_aspects(),
                        megaStone.getMegaProps().aspect_conditions().revert_aspects()
                );
            }
        }

        return Unit.INSTANCE;
    }

    private static Unit heldItemChange(HeldItemEvent.Pre event) {
        if(event.getPokemon().getPersistentData().getBoolean("form_changing")) {
            event.cancel();
            return Unit.INSTANCE;
        }

        if(event.getReturning().getItem() instanceof FormChangeItem formChangeItem) {
            formChangeItem.revert(event.getPokemon());
        }

        if(event.getReceiving().getItem() instanceof FormChangeItem formChangeItem) {
            formChangeItem.apply(event.getPokemon());
        }

        return Unit.INSTANCE;
    }
}
