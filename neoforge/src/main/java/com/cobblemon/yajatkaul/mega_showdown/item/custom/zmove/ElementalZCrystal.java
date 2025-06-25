package com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove;

import com.cobblemon.mod.common.api.types.ElementalType;

public class ElementalZCrystal extends ZCrystal {
    private final String type;

    public ElementalZCrystal(Properties properties, ElementalType type, String strType) {
        super(properties, type);
        this.type = strType;
    }

    public String getType() {
        return type;
    }
}
