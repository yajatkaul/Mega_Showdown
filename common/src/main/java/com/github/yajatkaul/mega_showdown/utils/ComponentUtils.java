package com.github.yajatkaul.mega_showdown.utils;

import com.github.yajatkaul.mega_showdown.codec.*;
import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.datapack.MegaShowdownDatapackRegister;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ComponentUtils {
    public static <T> T getComponent(Class<T> type, ItemStack stack) {
        ResourceLocation resourceLocation = stack.get(MegaShowdownDataComponents.RESOURCE_LOCATION_COMPONENT.get());
        String regType = stack.getOrDefault(MegaShowdownDataComponents.REGISTRY_TYPE_COMPONENT.get(), "null");

        return switch (regType) {
            case "du_fusion" -> type == DuFusion.class ?
                    type.cast(MegaShowdownDatapackRegister.DU_FUSION_REGISTRY.get(resourceLocation)) : null;
            case "solo_fusion" -> type == SoloFusion.class ?
                    type.cast(MegaShowdownDatapackRegister.SOLO_FUSION_REGISTRY.get(resourceLocation)) : null;
            case "form_change_toggle_interact" -> type == FormChangeToggleInteractItem.class ?
                    type.cast(MegaShowdownDatapackRegister.FORM_CHANGE_TOGGLE_INTERACT_REGISTRY.get(resourceLocation)) : null;
            case "form_change_interact" -> type == FormChangeInteractItem.class ?
                    type.cast(MegaShowdownDatapackRegister.FORM_CHANGE_INTERACT_REGISTRY.get(resourceLocation)) : null;
            case "held_form_change" -> type == HeldItemFormChange.class ?
                    type.cast(MegaShowdownDatapackRegister.HELD_ITEM_FORM_CHANGE_REGISTRY.get(resourceLocation)) : null;
            case "mega" -> type == MegaGimmick.class ?
                    type.cast(MegaShowdownDatapackRegister.MEGA_REGISTRY.get(resourceLocation)) : null;
            case "showdown_item" -> type == ShowdownItem.class ?
                    type.cast(MegaShowdownDatapackRegister.SHOWDOWN_ITEM_REGISTRY.get(resourceLocation)) : null;
            case "battle_form" -> type == ShowdownItem.class ?
                    type.cast(MegaShowdownDatapackRegister.BATTLE_FORM_CHANGE_REGISTRY.get(resourceLocation)) : null;
            default -> null;
        };
    }
}
