package com.cobblemon.yajatkaul.mega_showdown.datapack;

import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
import com.cobblemon.yajatkaul.mega_showdown.commands.MegaCommands;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class DataPackRegistriesLoader {
    public static void registerCustomShowdown() {
        Utils.GMAX_SPECIES.clear();
        Utils.addGmaxToMap();
        Utils.MEGA_POKEMONS.clear();
        Utils.addMegaList();

        //MEGA
        for (MegaData pokemon : Utils.megaRegistry) {
            Utils.MEGA_POKEMONS.add(pokemon.pokemon());
            String[] parts = pokemon.item_id().split(":");
            Identifier custom_stone_item_id = Identifier.of(parts[0], parts[1]);
            Item customStone = Registries.ITEM.get(custom_stone_item_id);
            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (stack.getItem().equals(customStone) &&
                        ((stack.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                                stack.get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == pokemon.custom_model_data()) ||
                                pokemon.custom_model_data() == 0)) {
                    return pokemon.showdown_id();
                }
                return null;
            });
        }

        //SHOWDOWN ITEMS
        for (ShowdownItemData item : Utils.showdownItemRegistry) {
            Identifier custom_held_item_id = Identifier.tryParse(item.item_id());
            Item customHeldItem = Registries.ITEM.get(custom_held_item_id);

            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (stack.getItem().equals(customHeldItem) &&
                        ((stack.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                                stack.get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == item.custom_model_data()) || item.custom_model_data() == 0)) {
                    return item.showdown_item_id();
                }
                return null;
            });
        }

        //GMAX
        for (GmaxData pokemon : Utils.gmaxRegistry) {
            Utils.GMAX_SPECIES.add(pokemon.pokemon());
        }
    }
}
