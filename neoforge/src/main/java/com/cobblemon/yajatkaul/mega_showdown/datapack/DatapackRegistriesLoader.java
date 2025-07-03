package com.cobblemon.yajatkaul.mega_showdown.datapack;

import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
import com.cobblemon.yajatkaul.mega_showdown.commands.MegaCommands;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.heldItem.HeldItemData;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class DatapackRegistriesLoader {

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
            ResourceLocation custom_stone_item_id = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
            Item customStone = BuiltInRegistries.ITEM.get(custom_stone_item_id);

            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (stack.getItem().equals(customStone) &&
                        ((stack.get(DataComponents.CUSTOM_MODEL_DATA) != null &&
                                stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == pokemon.custom_model_data())
                                || pokemon.custom_model_data() == 0)) {
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

            String[] parts = items.item_id().split(":");
            ResourceLocation custom_held_item_id = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
            Item customHeldItem = BuiltInRegistries.ITEM.get(custom_held_item_id);

            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (stack.getItem().equals(customHeldItem) &&
                        ((stack.get(DataComponents.CUSTOM_MODEL_DATA) != null &&
                                stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == items.custom_model_data())
                                || items.custom_model_data() == 0)) {
                    return items.showdown_id();
                }
                return null;
            });
        }

        //BATTLE ONLY FORME CHANGE
        for (HeldItemData items : Utils.formChangeRegistry) {
            //COMMAND UTILS
            MegaCommands.VALID_ITEMS.add(items.msd_id());
            //

            if (items.battle_mode_only() != null) {
                String[] parts = items.item_id().split(":");
                ResourceLocation custom_held_item_id = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
                Item customHeldItem = BuiltInRegistries.ITEM.get(custom_held_item_id);

                CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                    if (stack.getItem().equals(customHeldItem) &&
                            ((stack.get(DataComponents.CUSTOM_MODEL_DATA) != null &&
                                    stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == items.custom_model_data())
                                    || items.custom_model_data() == 0)) {
                        return items.showdown_id();
                    }
                    return null;
                });
            }
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
        for (KeyItemData keyItem : Utils.keyItemsRegistry) {
            MegaCommands.VALID_ITEMS.add(keyItem.msd_id());
        }
    }

}
