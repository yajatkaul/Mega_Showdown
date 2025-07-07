package com.cobblemon.yajatkaul.mega_showdown.datapack;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public class DatapackRegister {
    public static final ResourceKey<Registry<KeyItemData>> KEY_ITEM_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "key_items"));
    public static final ResourceKey<Registry<FusionData>> FUSION_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "fusions"));
    public static final ResourceKey<Registry<GmaxData>> GMAX_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "gmax"));
    public static final ResourceKey<Registry<HeldItemData>> HELD_ITEM_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "held_items"));
    public static final ResourceKey<Registry<MegaData>> MEGA_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega"));
    public static final ResourceKey<Registry<ShowdownItemData>> SHOWDOWN_ITEM_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "showdown_item"));
    public static final ResourceKey<Registry<BattleFormChange>> BATTLE_FORM_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "battle_form"));


    public static void register(DataPackRegistryEvent.NewRegistry event) {

        event.dataPackRegistry(
                KEY_ITEM_REGISTRY_KEY,
                KeyItemData.CODEC,
                KeyItemData.CODEC
        );


        event.dataPackRegistry(
                FUSION_REGISTRY_KEY,
                FusionData.CODEC,
                FusionData.CODEC
        );


        event.dataPackRegistry(
                GMAX_REGISTRY_KEY,
                GmaxData.CODEC,
                GmaxData.CODEC
        );


        event.dataPackRegistry(
                HELD_ITEM_REGISTRY_KEY,
                HeldItemData.CODEC,
                HeldItemData.CODEC
        );


        event.dataPackRegistry(
                MEGA_REGISTRY_KEY,
                MegaData.CODEC,
                MegaData.CODEC
        );


        event.dataPackRegistry(
                SHOWDOWN_ITEM_REGISTRY_KEY,
                ShowdownItemData.CODEC,
                ShowdownItemData.CODEC
        );

        event.dataPackRegistry(
                BATTLE_FORM_REGISTRY_KEY,
                BattleFormChange.CODEC,
                BattleFormChange.CODEC
        );
    }

}
