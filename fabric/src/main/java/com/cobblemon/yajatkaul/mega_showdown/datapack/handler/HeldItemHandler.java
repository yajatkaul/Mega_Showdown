package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.api.events.battles.instruction.FormeChangeEvent;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.BattleFormChange;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.HeldItemData;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;


public class HeldItemHandler {
    public static void customEvents(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();
        ItemStack itemStack = pokemon.heldItem();

        for (HeldItemData heldItem : Utils.heldItemsRegistry) {
            Item item = Registries.ITEM.get(Identifier.tryParse(heldItem.item_id()));
            if (heldItem.pokemons().contains(pokemon.getSpecies().getName())) {
                if (HandlerUtils.itemValidator(item, heldItem.custom_model_data(), itemStack)) {
                    if (event.getReceiving().isOf(item)) {
                        if (pokemon.getAspects().containsAll(heldItem.apply_if())) {
                            HandlerUtils.applyAspects(heldItem.apply_aspects(), pokemon);
                            return;
                        }
                    } else if (event.getReturning().isOf(item)) {
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

        for (BattleFormChange formChange : Utils.battleFormRegistry) {
            if (formChange.pokemons().contains(pokemon.getSpecies().getName())
                    && formeChangeEvent.getFormeName().equals(formChange.showdown_form_id())) {
                if(formChange.effects().snowStorm() != null){
                    HandlerUtils.snowStromParticleEffect(pokemon.getEntity(), formChange.effects(), true, formChange.apply_aspects());
                }else {
                    HandlerUtils.applyAspects(formChange.apply_aspects(), pokemon);
                }
                break;
            }
        }
    }
}
