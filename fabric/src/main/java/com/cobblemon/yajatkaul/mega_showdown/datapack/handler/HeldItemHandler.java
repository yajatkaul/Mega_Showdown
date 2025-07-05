package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.events.battles.instruction.FormeChangeEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.BattleFormChange;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.HeldItemData;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import kotlin.Unit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;


public class HeldItemHandler {
    public static void customEvents(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();
        ItemStack itemStack = pokemon.heldItem();

        for (HeldItemData heldItem : Utils.heldItemsRegistry) {
            Item item = Registries.ITEM.get(Identifier.tryParse(heldItem.item_id()));
            if (heldItem.pokemons().contains(pokemon.getSpecies().getName())) {
                if (HandlerUtils.itemValidator(item, heldItem.custom_model_data(), itemStack)) {
                    if (event.getReceiving().isOf(item)) {
                        for (List<String> condition : heldItem.apply_if()) {
                            if (pokemon.getAspects().containsAll(condition)) {
                                HandlerUtils.applyAspects(heldItem.apply_aspects(), pokemon);
                                return;
                            }
                        }
                    } else if (event.getReturning().isOf(item)) {
                        for (List<String> condition : heldItem.revert_if()) {
                            if (pokemon.getAspects().containsAll(condition)) {
                                HandlerUtils.applyAspects(heldItem.revert_aspects(), pokemon);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void battleModeFormChange(FormeChangeEvent formeChangeEvent) {
        Pokemon pokemon = formeChangeEvent.getPokemon().getEffectedPokemon();
        PokemonBattle pokemonBattle = formeChangeEvent.getBattle();

        for (BattleFormChange formChange : Utils.battleFormRegistry) {
            if (formChange.pokemons().contains(pokemon.getSpecies().getName())
                    && formeChangeEvent.getFormeName().equals(formChange.showdown_form_id_apply())) {
                if(formChange.effects().snowStorm() != null){
                    pokemonBattle.dispatchWaitingToFront(formChange.effects().snowStorm().apply_after(), () -> {
                        HandlerUtils.applyEffects(formChange.effects(), pokemon.getEntity(), formChange.apply_aspects(), true);
                        return Unit.INSTANCE;
                    });
                }else {
                    HandlerUtils.applyAspects(formChange.apply_aspects(), pokemon);
                }
                break;
            } else if (formChange.pokemons().contains(pokemon.getSpecies().getName())
                    && formeChangeEvent.getFormeName().equals(formChange.showdown_form_id_revert())) {
                if(formChange.effects().snowStorm() != null){
                    pokemonBattle.dispatchWaitingToFront(formChange.effects().snowStorm().apply_after(), () -> {
                        HandlerUtils.applyEffects(formChange.effects(), pokemon.getEntity(), formChange.revert_aspects(), false);
                        return Unit.INSTANCE;
                    });
                }else {
                    HandlerUtils.applyAspects(formChange.revert_aspects(), pokemon);
                }
                break;
            }
        }
    }
}
