package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import net.minecraft.item.Item;

public class Memory extends Item {
    private final String type;

    public Memory(Settings settings, String type) {
        super(settings);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
