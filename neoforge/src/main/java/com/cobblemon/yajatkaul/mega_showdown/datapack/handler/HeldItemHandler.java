package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.api.events.battles.instruction.FormeChangeEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.heldItem.HeldItemData;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HeldItemHandler {
    public static void customEvents(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();
        ItemStack itemStack = pokemon.heldItem();

        for (HeldItemData heldItem : Utils.heldItemsRegistry) {
            if (heldItem.battle_mode_only() != null) {
                continue;
            }
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(heldItem.item_id()));
            if (heldItem.pokemons().contains(pokemon.getSpecies().getName())) {
                if (HandlerUtils.itemValidator(item, heldItem.custom_model_data(), itemStack)) {
                    if (event.getReceiving().is(item)) {
                        if (pokemon.getAspects().containsAll(heldItem.apply_if())) {
                            HandlerUtils.applyAspects(heldItem.apply_aspects(), pokemon);
                            return;
                        }
                    } else if (event.getReturning().is(item)) {
                        if (pokemon.getAspects().containsAll(heldItem.revert_if())) {
                            HandlerUtils.applyAspects(heldItem.revert_if(), pokemon);
                        }
                    }
                }
            }
        }
    }

    public static void battleModeFormChange(FormeChangeEvent formeChangeEvent) {
        Pokemon pokemon = formeChangeEvent.getPokemon().getEffectedPokemon();

        for (HeldItemData heldItem : Utils.heldItemsRegistry) {
            if (heldItem.battle_mode_only() == null) {
                continue;
            }
            if (heldItem.pokemons().contains(pokemon.getSpecies().getName())
                    && formeChangeEvent.getFormeName().equals(heldItem.battle_mode_only().showdown_form_id())) {
                HandlerUtils.applyAspects(heldItem.battle_mode_only().apply_aspects(), pokemon);
                break;
            }
        }
    }
}
