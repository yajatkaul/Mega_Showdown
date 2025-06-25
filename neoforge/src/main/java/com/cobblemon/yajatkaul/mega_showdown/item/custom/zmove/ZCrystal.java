package com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove;

import com.cobblemon.mod.common.api.types.ElementalType;
import net.minecraft.world.item.Item;

public class ZCrystal extends Item {
    private final ElementalType element;

    public ZCrystal(Properties properties, ElementalType element) {
        super(properties);
        this.element = element;
    }

    public ElementalType getElement() {
        return element;
    }
}
