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
        MegaCommands.VALID_ITEMS.clear();

        //MEGA
        for (MegaData pokemon : Utils.megaRegistry) {
            //COMMAND UTILS
            MegaCommands.VALID_ITEMS.add(pokemon.msd_id());
            //

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

        //HELD ITEMS
        for (HeldItemData items : Utils.heldItemsRegistry) {
            //COMMAND UTILS
            MegaCommands.VALID_ITEMS.add(items.msd_id());
            //
        }

        //SHOWDOWN ITEMS
        for (ShowdownItemData items : Utils.showdownItemRegistry) {
            //COMMAND UTILS
            MegaCommands.VALID_ITEMS.add(items.msd_id());
            //

            Identifier custom_held_item_id = Identifier.tryParse(items.item_id());
            Item customHeldItem = Registries.ITEM.get(custom_held_item_id);

            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (stack.getItem().equals(customHeldItem) &&
                        ((stack.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                                stack.get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == items.custom_model_data()) || items.custom_model_data() == 0)) {
                    return items.showdown_item_id();
                }
                return null;
            });
        }

        //GMAX
        for (GmaxData pokemon : Utils.gmaxRegistry) {
            Utils.GMAX_SPECIES.add(pokemon.pokemon());
        }

        //FUSIONS
        for (FusionData fusion : Utils.fusionRegistry) {
            MegaCommands.VALID_ITEMS.add(fusion.msd_id());
        }

        //KEY ITEMS
        for (KeyItemData keyItems : Utils.keyItemsRegistry) {
            MegaCommands.VALID_ITEMS.add(keyItems.msd_id());
        }
    }
}
