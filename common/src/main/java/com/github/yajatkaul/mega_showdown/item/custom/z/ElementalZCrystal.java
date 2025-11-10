package com.github.yajatkaul.mega_showdown.item.custom.z;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;

public class ElementalZCrystal extends ToolTipItem {
    private final ElementalType element;

    public ElementalZCrystal(Properties properties, ElementalType element) {
        super(properties);
        this.element = element;
    }

    public ElementalType getElement() {
        return element;
    }
}
