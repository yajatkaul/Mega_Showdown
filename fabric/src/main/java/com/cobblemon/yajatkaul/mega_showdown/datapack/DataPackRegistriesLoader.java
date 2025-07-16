package com.cobblemon.yajatkaul.mega_showdown.datapack;

import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.MegaData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.ShowdownItemData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.handler.HandlerUtils;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class DataPackRegistriesLoader {
    public static void registerCustomShowdown() {
        //MEGA
        for (MegaData pokemon : Utils.megaRegistry) {
            Identifier custom_stone_item_id = Identifier.tryParse(pokemon.item_id());
            Item customStone = Registries.ITEM.get(custom_stone_item_id);
            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (HandlerUtils.itemValidator(customStone, pokemon.custom_model_data(), stack)) {
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
                if (HandlerUtils.itemValidator(customHeldItem, item.custom_model_data(), stack)) {
                    return item.showdown_item_id();
                }
                return null;
            });
        }
    }
}
