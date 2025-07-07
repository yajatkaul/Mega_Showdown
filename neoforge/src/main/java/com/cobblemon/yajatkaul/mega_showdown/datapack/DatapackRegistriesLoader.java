package com.cobblemon.yajatkaul.mega_showdown.datapack;

import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
import com.cobblemon.yajatkaul.mega_showdown.commands.MegaCommands;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.GmaxData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.MegaData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.ShowdownItemData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.handler.HandlerUtils;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
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
            Utils.MEGA_POKEMONS.add(pokemon.pokemon());
            ResourceLocation custom_stone_item_id = ResourceLocation.tryParse(pokemon.item_id());
            Item customStone = BuiltInRegistries.ITEM.get(custom_stone_item_id);
            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (HandlerUtils.itemValidator(customStone, pokemon.custom_model_data(), stack)) {
                    return pokemon.showdown_id();
                }
                return null;
            });
        }

        //SHOWDOWN ITEMS
        for (ShowdownItemData item : Utils.showdownItemRegistry) {
            ResourceLocation custom_held_item_id = ResourceLocation.tryParse(item.item_id());
            Item customHeldItem = BuiltInRegistries.ITEM.get(custom_held_item_id);

            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (HandlerUtils.itemValidator(customHeldItem, item.custom_model_data(), stack)) {
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
