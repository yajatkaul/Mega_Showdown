package com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange;

import net.minecraft.item.Item;

public class ArceusType extends Item {
    private final String type;

    public ArceusType(Settings settings, String type) {
        super(settings);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
