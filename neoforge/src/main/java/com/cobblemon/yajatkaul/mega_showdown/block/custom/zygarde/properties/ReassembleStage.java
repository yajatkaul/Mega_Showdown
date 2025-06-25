package com.cobblemon.yajatkaul.mega_showdown.block.custom.zygarde.properties;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum ReassembleStage implements StringRepresentable {
    IDLE, COOKING_10, COOKING_50, COOKING_100, FINISHED_10, FINISHED_50, FINISHED_100;

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}