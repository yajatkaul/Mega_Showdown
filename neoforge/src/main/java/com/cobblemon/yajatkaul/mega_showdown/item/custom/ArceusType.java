package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import net.minecraft.world.item.Item;

public class ArceusType extends Item {
    private final String type;

    public ArceusType(Properties arg, String type) {
        super(arg);
        this.type = type;
    }


    public String getType() {
        return type;
    }
}
