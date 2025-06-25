package com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove;

import com.cobblemon.mod.common.api.types.ElementalType;

public class ElementalZCrystal extends ZCrystal {
    private final String strType;

    public ElementalZCrystal(Settings settings, ElementalType type, String strType) {
        super(settings, type);
        this.strType = strType;
    }

    public String getType() {
        return strType;
    }
}
