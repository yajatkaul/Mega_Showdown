package com.cobblemon.yajatkaul.mega_showdown.datapack;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class DatapackRegister {

    public static final RegistryKey<Registry<KeyItemData>> KEY_ITEM_REGISTRY_KEY =
            RegistryKey.ofRegistry(Identifier.of(MegaShowdown.MOD_ID, "key_items"));

    public static final RegistryKey<Registry<FusionData>> FUSION_REGISTRY_KEY =
            RegistryKey.ofRegistry(Identifier.of(MegaShowdown.MOD_ID, "fusions"));

    public static final RegistryKey<Registry<GmaxData>> GMAX_REGISTRY_KEY =
            RegistryKey.ofRegistry(Identifier.of(MegaShowdown.MOD_ID, "gmax"));

    public static final RegistryKey<Registry<HeldItemData>> HELD_ITEM_REGISTRY_KEY =
            RegistryKey.ofRegistry(Identifier.of(MegaShowdown.MOD_ID, "held_items"));

    public static final RegistryKey<Registry<MegaData>> MEGA_REGISTRY_KEY =
            RegistryKey.ofRegistry(Identifier.of(MegaShowdown.MOD_ID, "mega"));

    public static final RegistryKey<Registry<BattleFormChange>> BATTLE_FORM_REGISTRY_KEY =
            RegistryKey.ofRegistry(Identifier.of(MegaShowdown.MOD_ID, "battle_form"));

    public static final RegistryKey<Registry<ShowdownItemData>> SHOWDOWN_ITEM_REGISTRY_KEY =
            RegistryKey.ofRegistry(Identifier.of(MegaShowdown.MOD_ID, "showdown_item"));

    public static void register() {
        DynamicRegistries.registerSynced(KEY_ITEM_REGISTRY_KEY, KeyItemData.CODEC);
        DynamicRegistries.registerSynced(FUSION_REGISTRY_KEY, FusionData.CODEC);
        DynamicRegistries.registerSynced(GMAX_REGISTRY_KEY, GmaxData.CODEC);
        DynamicRegistries.registerSynced(HELD_ITEM_REGISTRY_KEY, HeldItemData.CODEC);
        DynamicRegistries.registerSynced(MEGA_REGISTRY_KEY, MegaData.CODEC);
        DynamicRegistries.registerSynced(BATTLE_FORM_REGISTRY_KEY, BattleFormChange.CODEC);
        DynamicRegistries.registerSynced(SHOWDOWN_ITEM_REGISTRY_KEY, ShowdownItemData.CODEC);
    }
}
