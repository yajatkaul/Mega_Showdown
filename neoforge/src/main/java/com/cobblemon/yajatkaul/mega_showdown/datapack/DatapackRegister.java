package com.cobblemon.yajatkaul.mega_showdown.datapack;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public class DatapackRegister {
    public static void register(DataPackRegistryEvent.NewRegistry event) {
        final ResourceKey<Registry<KeyItemData>> KEY_ITEM_REGISTRY_KEY =
                ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "key_items"));

        event.dataPackRegistry(
                KEY_ITEM_REGISTRY_KEY,
                KeyItemData.CODEC,
                KeyItemData.CODEC
        );

        final ResourceKey<Registry<FormChangeData>> FORM_CHANGE_REGISTRY_KEY =
                ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "form_change"));

        event.dataPackRegistry(
                FORM_CHANGE_REGISTRY_KEY,
                FormChangeData.CODEC,
                FormChangeData.CODEC
        );

        final ResourceKey<Registry<FusionData>> FUSION_REGISTRY_KEY =
                ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "fusions"));

        event.dataPackRegistry(
                FUSION_REGISTRY_KEY,
                FusionData.CODEC,
                FusionData.CODEC
        );

        final ResourceKey<Registry<GmaxData>> GMAX_REGISTRY_KEY =
                ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "gmax"));

        event.dataPackRegistry(
                GMAX_REGISTRY_KEY,
                GmaxData.CODEC,
                GmaxData.CODEC
        );

        final ResourceKey<Registry<HeldItemData>> HELD_ITEM_REGISTRY_KEY =
                ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "held_items"));

        event.dataPackRegistry(
                HELD_ITEM_REGISTRY_KEY,
                HeldItemData.CODEC,
                HeldItemData.CODEC
        );

        final ResourceKey<Registry<MegaData>> MEGA_REGISTRY_KEY =
                ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega"));

        event.dataPackRegistry(
                MEGA_REGISTRY_KEY,
                MegaData.CODEC,
                MegaData.CODEC
        );
    }

}
