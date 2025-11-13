package com.github.yajatkaul.mega_showdown.datapack;

import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.codec.*;
import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.gimmick.MaxGimmick;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.utils.Effect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class MegaShowdownDatapackRegister {
    public static final ResourceKey<Registry<Effect>> EFFECT_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "effect"));

    public static final ResourceKey<Registry<FormChangeToggleInteractItem>> FORM_CHANGE_TOGGLE_INTERACT_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "form_change_toggle_interact"));
    public static final ResourceKey<Registry<FormChangeInteractItem>> FORM_CHANGE_INTERACT_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "form_change_interact"));
    public static final ResourceKey<Registry<SoloFusion>> SOLO_FUSION_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "solo_fusion"));
    public static final ResourceKey<Registry<DuFusion>> DU_FUSION_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "du_fusion"));
    public static final ResourceKey<Registry<MaxGimmick>> GMAX_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "gmax"));
    public static final ResourceKey<Registry<HeldItemFormChange>> HELD_ITEM_FORM_CHANGE_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "held_form_change"));
    public static final ResourceKey<Registry<MegaGimmick>> MEGA_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega"));
    public static final ResourceKey<Registry<ShowdownItem>> SHOWDOWN_ITEM_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "showdown_item"));
    public static final ResourceKey<Registry<BattleFormChange>> BATTLE_FORM_CHANGE_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "battle_form"));

    public static Registry<MegaGimmick> MEGA_REGISTRY;
    public static Registry<ShowdownItem> SHOWDOWN_ITEM_REGISTRY;
    public static Registry<MaxGimmick> GMAX_REGISTRY;
    public static Registry<SoloFusion> SOLO_FUSION_REGISTRY;
    public static Registry<DuFusion> DU_FUSION_REGISTRY;
    public static Registry<HeldItemFormChange> HELD_ITEM_FORM_CHANGE_REGISTRY;
    public static Registry<FormChangeToggleInteractItem> FORM_CHANGE_TOGGLE_INTERACT_REGISTRY;
    public static Registry<BattleFormChange> BATTLE_FORM_CHANGE_REGISTRY;
    public static Registry<Effect> EFFECT_REGISTRY;
    public static Registry<FormChangeInteractItem> FORM_CHANGE_INTERACT_REGISTRY;

    public static void registerShowdownDatapackItems() {
        EFFECT_REGISTRY = MegaShowdown.getServer().registryAccess().registryOrThrow(MegaShowdownDatapackRegister.EFFECT_REGISTRY_KEY);
        MEGA_REGISTRY = MegaShowdown.getServer().registryAccess().registryOrThrow(MegaShowdownDatapackRegister.MEGA_REGISTRY_KEY);
        SHOWDOWN_ITEM_REGISTRY = MegaShowdown.getServer().registryAccess().registryOrThrow(MegaShowdownDatapackRegister.SHOWDOWN_ITEM_REGISTRY_KEY);
        GMAX_REGISTRY = MegaShowdown.getServer().registryAccess().registryOrThrow(MegaShowdownDatapackRegister.GMAX_REGISTRY_KEY);
        SOLO_FUSION_REGISTRY = MegaShowdown.getServer().registryAccess().registryOrThrow(MegaShowdownDatapackRegister.SOLO_FUSION_REGISTRY_KEY);
        DU_FUSION_REGISTRY = MegaShowdown.getServer().registryAccess().registryOrThrow(MegaShowdownDatapackRegister.DU_FUSION_REGISTRY_KEY);
        HELD_ITEM_FORM_CHANGE_REGISTRY = MegaShowdown.getServer().registryAccess().registryOrThrow(MegaShowdownDatapackRegister.HELD_ITEM_FORM_CHANGE_REGISTRY_KEY);
        FORM_CHANGE_TOGGLE_INTERACT_REGISTRY = MegaShowdown.getServer().registryAccess().registryOrThrow(MegaShowdownDatapackRegister.FORM_CHANGE_TOGGLE_INTERACT_REGISTRY_KEY);
        BATTLE_FORM_CHANGE_REGISTRY = MegaShowdown.getServer().registryAccess().registryOrThrow(MegaShowdownDatapackRegister.BATTLE_FORM_CHANGE_REGISTRY_KEY);
        FORM_CHANGE_INTERACT_REGISTRY = MegaShowdown.getServer().registryAccess().registryOrThrow(MegaShowdownDatapackRegister.FORM_CHANGE_INTERACT_REGISTRY_KEY);

        for (MegaGimmick megaGimmick : MEGA_REGISTRY) {
            for (String aspect : megaGimmick.aspect_conditions().apply_aspects()) {
                String[] split = aspect.split("=");
                if (split[1].equals("true") || split[1].equals("false")) {
                    MegaGimmick.appendMegaAspect(split[0]);
                } else {
                    MegaGimmick.appendMegaAspect(split[1]);
                }
            }
        }

        for (ShowdownItem item : SHOWDOWN_ITEM_REGISTRY) {
            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                ShowdownItem showdownItem = stack.get(MegaShowdownDataComponents.SHOWDOWN_ITEM_COMPONENT.get());
                if (showdownItem != null && showdownItem.equals(item)) {
                    return item.showdown_item_id();
                }
                return null;
            });
        }
    }
}
