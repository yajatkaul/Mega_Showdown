package com.cobblemon.yajatkaul.mega_showdown.block.custom.zygarde.properties;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum ReassembleStage implements StringIdentifiable {
    IDLE, COOKING_10, COOKING_50, COOKING_100, FINISHED_10, FINISHED_50, FINISHED_100;

    @Override
    public String asString() {
        return name().toLowerCase(Locale.ROOT);
    }
}