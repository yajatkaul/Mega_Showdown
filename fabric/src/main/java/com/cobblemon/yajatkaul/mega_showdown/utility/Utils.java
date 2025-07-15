package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.yajatkaul.mega_showdown.datapack.DataPackRegistriesLoader;
import com.cobblemon.yajatkaul.mega_showdown.datapack.DatapackRegister;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class Utils {
    public static Registry<KeyItemData> keyItemsRegistry;
    public static Registry<FusionData> fusionRegistry;
    public static Registry<GmaxData> gmaxRegistry;
    public static Registry<HeldItemData> heldItemsRegistry;
    public static Registry<MegaData> megaRegistry;
    public static Registry<BattleFormChange> battleFormRegistry;
    public static Registry<ShowdownItemData> showdownItemRegistry;

    public static void registryLoader(DynamicRegistryManager registryAccess) {
        keyItemsRegistry = registryAccess.get(DatapackRegister.KEY_ITEM_REGISTRY_KEY);
        fusionRegistry = registryAccess.get(DatapackRegister.FUSION_REGISTRY_KEY);
        gmaxRegistry = registryAccess.get(DatapackRegister.GMAX_REGISTRY_KEY);
        heldItemsRegistry = registryAccess.get(DatapackRegister.HELD_ITEM_REGISTRY_KEY);
        megaRegistry = registryAccess.get(DatapackRegister.MEGA_REGISTRY_KEY);
        battleFormRegistry = registryAccess.get(DatapackRegister.BATTLE_FORM_REGISTRY_KEY);
        showdownItemRegistry = registryAccess.get(DatapackRegister.SHOWDOWN_ITEM_REGISTRY_KEY);

        DataPackRegistriesLoader.registerCustomShowdown();
    }
}
