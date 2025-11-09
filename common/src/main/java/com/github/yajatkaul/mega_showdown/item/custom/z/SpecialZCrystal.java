package com.github.yajatkaul.mega_showdown.item.custom.z;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import net.minecraft.world.item.Item;

public class SpecialZCrystal extends ToolTipItem {
    private final ElementalType element;

    public SpecialZCrystal(Item.Properties properties, ElementalType element) {
        super(properties);
        this.element = element;
    }

    public ElementalType getElement() {
        return element;
    }

    public String getElementAsString() {
        return element.toString();
    }
}
