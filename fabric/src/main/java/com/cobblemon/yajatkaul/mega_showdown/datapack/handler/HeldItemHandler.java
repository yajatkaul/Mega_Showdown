package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.FormChangeData;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class HeldItemHandler {
    public static void customEvents(HeldItemEvent.Pre event) {
        Pokemon pokemon = event.getPokemon();

        for (FormChangeData heldItem : Utils.formChangeRegistry) {
            if (heldItem.battle_mode_only()) {
                return;
            }
            if (heldItem.pokemons().contains(pokemon.getSpecies().getName())) {
                if (!pokemon.getEntity().isBattling()) {
                    if (!heldItem.required_aspects().isEmpty()) {
                        List<String> aspectList = new ArrayList<>();
                        for (String aspects : heldItem.required_aspects()) {
                            String[] aspectsDiv = aspects.split("=");
                            if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                                aspectList.add(aspects.split("=")[0]);
                            } else {
                                aspectList.add(aspects.split("=")[1]);
                            }
                        }

                        boolean allMatch = true;
                        for (String requiredAspect : aspectList) {
                            boolean matched = false;
                            for (String pokemonAspect : pokemon.getAspects()) {
                                if (pokemonAspect.startsWith(requiredAspect)) {
                                    matched = true;
                                    break;
                                }
                            }
                            if (!matched) {
                                allMatch = false;
                                break;
                            }
                        }

                        if (!allMatch) {
                            return;
                        }
                    }

                    ItemStack receivedItem = event.getReceiving();
                    String[] nameSpace = heldItem.item_id().split(":");
                    Identifier customItem = Identifier.of(nameSpace[0], nameSpace[1]);
                    Item item = Registries.ITEM.get(customItem);
                    if (receivedItem.isOf(item) && ((receivedItem.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null
                            && receivedItem.get(DataComponentTypes.CUSTOM_MODEL_DATA).value()
                            == heldItem.custom_model_data()) || heldItem.custom_model_data() == 0)) {
                        if (!heldItem.tradable_form()) {
                            setTradable(pokemon, false);
                        }
                        for (String aspects : heldItem.aspects()) {
                            String[] aspectsDiv = aspects.split("=");
                            if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                                new FlagSpeciesFeature(aspectsDiv[0], Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                            } else {
                                new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                            }
                        }
                        if (!heldItem.tradable_form()) {
                            setTradable(pokemon, false);
                        }
                        HandlerUtils.particleEffect(pokemon.getEntity(), heldItem.effects(), true);
                        return;
                    } else if (!receivedItem.isOf(item) ||
                            ((receivedItem.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                                    receivedItem.get(DataComponentTypes.CUSTOM_MODEL_DATA).value()
                                            == heldItem.custom_model_data()) || heldItem.custom_model_data() == 0)) {
                        if (!heldItem.tradable_form()) {
                            setTradable(pokemon, true);
                        }
                        for (String aspects : heldItem.default_aspects()) {
                            String[] aspectsDiv = aspects.split("=");
                            if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                                new FlagSpeciesFeature(aspectsDiv[0], Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                            } else {
                                new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                            }
                        }
                        if (!heldItem.tradable_form()) {
                            setTradable(pokemon, true);
                        }
                        HandlerUtils.particleEffect(pokemon.getEntity(), heldItem.effects(), false);
                        return;
                    }
                }
            }
        }
    }
}
