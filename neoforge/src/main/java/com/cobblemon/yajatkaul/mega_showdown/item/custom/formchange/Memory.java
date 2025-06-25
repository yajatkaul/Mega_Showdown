package com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange;

import net.minecraft.world.item.Item;

public class Memory extends Item {
    private final String type;

    public Memory(Properties arg, String type) {
        super(arg);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
