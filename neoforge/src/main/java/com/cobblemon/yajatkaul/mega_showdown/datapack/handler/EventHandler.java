package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.events.battles.instruction.FormeChangeEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.BattleFormChange;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.HeldItemData;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import kotlin.Unit;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class EventHandler {
    public static void customEvents(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        for (HeldItemData heldItem : Utils.heldItemsRegistry) {
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(heldItem.item_id()));
            if (heldItem.pokemons().contains(pokemon.getSpecies().getName())  && !HandlerUtils.listCheck(heldItem.blacklist_aspects(), pokemon.getAspects(), true)) {
                ItemStack itemReceiving = event.getReceiving();
                ItemStack itemReturning = event.getReturning();
                if (HandlerUtils.itemValidator(item, heldItem.custom_model_data(), itemReceiving)) {
                    if (heldItem.apply_if().isEmpty()) {
                        HandlerUtils.applyEffects(heldItem.effects(), pokemon.getEntity(), heldItem.apply_aspects(), true);
                    }
                    for (List<String> condition : heldItem.apply_if()) {
                        if (pokemon.getAspects().containsAll(condition)) {
                            HandlerUtils.applyEffects(heldItem.effects(), pokemon.getEntity(), heldItem.apply_aspects(), true);
                            return;
                        }
                    }
                } else if (HandlerUtils.itemValidator(item, heldItem.custom_model_data(), itemReturning)) {
                    for (List<String> condition : heldItem.revert_if()) {
                        if (pokemon.getAspects().containsAll(condition)) {
                            HandlerUtils.applyEffects(heldItem.effects(), pokemon.getEntity(), heldItem.revert_aspects(), false);
                        }
                    }
                }
            }
        }
    }

    public static void battleModeFormChange(FormeChangeEvent formeChangeEvent) {
        Pokemon pokemon = formeChangeEvent.getPokemon().getEffectedPokemon();
        PokemonBattle pokemonBattle = formeChangeEvent.getBattle();
        Optional<PokemonEntity> other = StreamSupport.stream(formeChangeEvent.getBattle().getActivePokemon().spliterator(), false)
                .map(ActiveBattlePokemon::getBattlePokemon)
                .filter(active -> formeChangeEvent.getPokemon().getFacedOpponents().contains(active))
                .filter(Objects::nonNull)
                .map(BattlePokemon::getEntity)
                .filter(Objects::nonNull)
                .findAny();

        for (BattleFormChange formChange : Utils.battleFormRegistry) {
            if (formChange.pokemons().contains(pokemon.getSpecies().getName())
                    && formeChangeEvent.getFormeName().equals(formChange.showdown_form_id_apply())) {
                if (formChange.effects().snowStorm() != null) {
                    pokemonBattle.dispatchWaitingToFront(formChange.effects().snowStorm().apply_after(), () -> {
                        HandlerUtils.applyEffects(formChange.effects(), pokemon.getEntity(), formChange.apply_aspects(), true, other.get());
                        return Unit.INSTANCE;
                    });
                } else {
                    HandlerUtils.applyEffects(formChange.effects(), pokemon.getEntity(), formChange.apply_aspects(), true);
                }
                break;
            } else if (formChange.pokemons().contains(pokemon.getSpecies().getName())
                    && formeChangeEvent.getFormeName().equals(formChange.showdown_form_id_revert())) {
                if (formChange.effects().snowStorm() != null) {
                    pokemonBattle.dispatchWaitingToFront(formChange.effects().snowStorm().apply_after(), () -> {
                        HandlerUtils.applyEffects(formChange.effects(), pokemon.getEntity(), formChange.revert_aspects(), false, other.get());
                        return Unit.INSTANCE;
                    });
                } else {
                    HandlerUtils.applyEffects(formChange.effects(), pokemon.getEntity(), formChange.revert_aspects(), false);
                }
                break;
            }
        }
    }
}
