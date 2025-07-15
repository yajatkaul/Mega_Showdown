package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.yajatkaul.mega_showdown.datapack.DatapackRegister;
import com.cobblemon.yajatkaul.mega_showdown.datapack.DatapackRegistriesLoader;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    public static Registry<KeyItemData> keyItemsRegistry;
    public static Registry<BattleFormChange> battleFormRegistry;
    public static Registry<FusionData> fusionRegistry;
    public static Registry<GmaxData> gmaxRegistry;
    public static Registry<HeldItemData> heldItemsRegistry;
    public static Registry<MegaData> megaRegistry;
    public static Registry<ShowdownItemData> showdownItemRegistry;

    public static void registryLoader(RegistryAccess registryAccess) {
        keyItemsRegistry = registryAccess.registryOrThrow(DatapackRegister.KEY_ITEM_REGISTRY_KEY);
        battleFormRegistry = registryAccess.registryOrThrow(DatapackRegister.BATTLE_FORM_REGISTRY_KEY);
        fusionRegistry = registryAccess.registryOrThrow(DatapackRegister.FUSION_REGISTRY_KEY);
        gmaxRegistry = registryAccess.registryOrThrow(DatapackRegister.GMAX_REGISTRY_KEY);
        heldItemsRegistry = registryAccess.registryOrThrow(DatapackRegister.HELD_ITEM_REGISTRY_KEY);
        megaRegistry = registryAccess.registryOrThrow(DatapackRegister.MEGA_REGISTRY_KEY);
        showdownItemRegistry = registryAccess.registryOrThrow(DatapackRegister.SHOWDOWN_ITEM_REGISTRY_KEY);

        DatapackRegistriesLoader.registerCustomShowdown();
    }
}
